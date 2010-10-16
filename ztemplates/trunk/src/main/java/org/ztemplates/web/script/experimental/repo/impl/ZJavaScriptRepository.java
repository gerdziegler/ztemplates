package org.ztemplates.web.script.experimental.repo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.ztemplates.render.script.ZScriptRepository;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptId;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptInfo;

public class ZJavaScriptRepository implements Serializable
{
  private static final Logger log = Logger.getLogger(ZScriptRepository.class);

  private final List<String> javaScriptSorted = new ArrayList<String>();

  private Map<String, ZJavaScriptInfo> javaScriptUrl2InfoMap = new HashMap<String, ZJavaScriptInfo>();

  private Map<ZJavaScriptId, ZJavaScriptInfo> javaScriptId2InfoMap = new HashMap<ZJavaScriptId, ZJavaScriptInfo>();

  private Map<String, List<ZJavaScriptInfo>> javaScriptLib2InfoMap = new HashMap<String, List<ZJavaScriptInfo>>();

  private final List<List<String>> javaScripts = new ArrayList<List<String>>();

  private final List<ZJavaScriptInfo> javaScriptInfo = new ArrayList<ZJavaScriptInfo>();

  private final List<List<String>> cssScripts = new ArrayList<List<String>>();

  private List<String> cssSorted = new ArrayList<String>();


  public ZJavaScriptRepository() throws Exception
  {
  }


  public void addJavaScript(List<String> js)
  {
    javaScripts.add(js);
  }


  public void addJavaScript(String[] jsArr)
  {
    List<String> l = new ArrayList<String>();
    for (String s : jsArr)
    {
      l.add(s);
    }
    addJavaScript(l);
  }


  public void addJavaScriptInfo(ZJavaScriptInfo javaScriptInfo)
  {
    this.javaScriptInfo.add(javaScriptInfo);
  }


  public void init() throws Exception
  {
    javaScriptId2InfoMap.clear();
    javaScriptUrl2InfoMap.clear();
    javaScriptLib2InfoMap.clear();
    javaScriptSorted.clear();
    cssSorted.clear();
    initJavaScriptInfoMap(javaScriptInfo);
    initJavaScriptSorted(javaScripts);
    initCssSorted(cssScripts);
  }


  private void initJavaScriptInfoMap(List<ZJavaScriptInfo> javaScriptInfo)
  {
    for (ZJavaScriptInfo jsi : javaScriptInfo)
    {
      initJavaScriptInfo(jsi);
    }
    for (List<ZJavaScriptInfo> jsiList : javaScriptLib2InfoMap.values())
    {
      sortJavaScriptInfoList(jsiList);
    }
  }

  final Comparator<ZJavaScriptInfo> comp = new Comparator<ZJavaScriptInfo>()
  {
    public int compare(ZJavaScriptInfo o1, ZJavaScriptInfo o2)
    {
      String v1 = o1.getId().getVersion();
      String v2 = o2.getId().getVersion();
      int ret = o1.getVersionComparator().compare(v1, v2);
      // reverse order!
      return -1 * ret;
    }
  };


  public void sortJavaScriptInfoList(List<ZJavaScriptInfo> jsiList)
  {
    System.out.println(jsiList);
    Collections.sort(jsiList, comp);
    System.out.println(jsiList);
  }


  private void initJavaScriptInfo(ZJavaScriptInfo jsi)
  {
    String renamed = renameVariables(jsi.getUrl());
    javaScriptUrl2InfoMap.put(renamed, jsi);
    javaScriptId2InfoMap.put(jsi.getId(), jsi);
    String lib = jsi.getId().getName();
    List<ZJavaScriptInfo> infos = javaScriptLib2InfoMap.get(lib);
    if (infos == null)
    {
      infos = new ArrayList<ZJavaScriptInfo>();
      javaScriptLib2InfoMap.put(lib, infos);
    }
    infos.add(jsi);
  }


  private ZJavaScriptInfo getJavaScriptInfo(String url)
  {
    String renamed = renameVariables(url);
    ZJavaScriptInfo ret = javaScriptUrl2InfoMap.get(renamed);
    if (ret == null)
    {
      ret = new ZJavaScriptInfo(renamed, new ZJavaScriptId(url, "1.0.0"));
      javaScriptUrl2InfoMap.put(renamed, ret);
    }
    return ret;
  }


  private List<ZJavaScriptInfo> getLibInfos(String url)
  {
    ZJavaScriptInfo jsi = getJavaScriptInfo(url);
    List<ZJavaScriptInfo> ret = javaScriptLib2InfoMap.get(jsi.getId().getName());
    if (ret == null)
    {
      ret = new ArrayList<ZJavaScriptInfo>();
      ret.add(jsi);
      javaScriptLib2InfoMap.put(url, ret);
    }
    return ret;
  }


  private void initJavaScriptSorted(List<List<String>> javaScripts) throws Exception
  {
    if (javaScripts.isEmpty())
    {
      return;
    }

    DefaultDirectedGraph<String, DefaultEdge> jsGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    for (List<String> zs : javaScripts)
    {
      for (String js : zs)
      {
        String renamed = renameVariables(js);
        ZJavaScriptInfo jsi = getJavaScriptInfo(js);
        boolean standalone = jsi != null && jsi.isStandalone();
        if (standalone && !javaScriptSorted.contains(renamed))
        {
          javaScriptSorted.add(renamed);
        }
        else
        {
          jsGraph.addVertex(renamed);
        }
      }
    }
    for (List<String> zs : javaScripts)
    {
      String v1 = null;
      for (String js : zs)
      {
        String renamed = renameVariables(js);
        if (v1 == null)
        {
          v1 = renamed;
        }
        else
        {
          String v2 = renamed;
          if (jsGraph.containsVertex(v1) && jsGraph.containsVertex(v2) && !jsGraph.containsEdge(v1, v2))
          {
            jsGraph.addEdge(v1, v2);
          }
          v1 = v2;
        }
      }
    }
    ZJavaScriptRepository.removeCycles(jsGraph);

    TopologicalOrderIterator<String, DefaultEdge> sortedIterator = new TopologicalOrderIterator<String, DefaultEdge>(jsGraph);
    while (sortedIterator.hasNext())
    {
      String js = sortedIterator.next();
      if (!javaScriptSorted.contains(js))
      {
        javaScriptSorted.add(js);
      }
    }
  }


  private static <V, E extends DefaultEdge> void removeCycles(DefaultDirectedGraph<V, E> g)
  {
    CycleDetector<V, E> cycleDetector = new CycleDetector<V, E>(g);
    boolean header = false;
    Set<V> cycleVertices = cycleDetector.findCycles();
    while (!cycleVertices.isEmpty())
    {
      if (!header)
      {
        log.warn(ZJavaScriptRepository.print(cycleVertices));
        log.info("assuming this is OK and will remove edges till no more cycles.");
        header = true;
      }

      Iterator<V> iter = cycleVertices.iterator();
      V e1 = iter.next();
      Set<E> edges = g.outgoingEdgesOf(e1);
      for (E de : edges)
      {
        V e2 = g.getEdgeTarget(de);
        if (cycleVertices.contains(e2))
        {
          log.info("removing '" + e1 + "' --> '" + e2 + "'");
          g.removeEdge(de);
          break;
        }
      }
      cycleVertices = cycleDetector.findCycles();
    }
  }


  private static <V> String print(Set<V> set)
  {
    StringBuffer sb = new StringBuffer("dependency graph has cycle (unordered): ");
    for (V js : set)
    {
      sb.append(js);
      sb.append(" --- ");
    }
    return sb.toString();
  }


  private void initCssSorted(List<List<String>> cssScripts) throws Exception
  {
    if (cssScripts.isEmpty())
    {
      return;
    }
    DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    for (List<String> zs : cssScripts)
    {
      for (String css : zs)
      {
        g.addVertex(css);
      }
    }

    if (g.vertexSet().isEmpty())
    {
      return;
    }

    for (List<String> zs : cssScripts)
    {
      String v1 = null;
      for (String css : zs)
      {
        if (v1 == null)
        {
          v1 = css;
        }
        else
        {
          String v2 = css;
          g.addEdge(v1, v2);
          v1 = v2;
        }
      }
    }

    ZJavaScriptRepository.removeCycles(g);

    TopologicalOrderIterator<String, DefaultEdge> sortedIterator = new TopologicalOrderIterator<String, DefaultEdge>(g);
    while (sortedIterator.hasNext())
    {
      String js = sortedIterator.next();
      if (!cssSorted.contains(js))
      {
        cssSorted.add(js);
      }
    }
  }


  /**
   * need this to recognize the same script, if written with different variable
   * names
   * 
   * @param s
   * @return
   */
  protected String renameVariables(String s)
  {
    return s.replaceAll("\\$\\{([^\\}])*\\}", "\\$\\{x\\}");
  }


  public int getJavaScriptIndex(String s)
  {
    String name = renameVariables(s);
    return javaScriptSorted.indexOf(name);
  }


  public int getCssIndex(String s)
  {
    return cssSorted.indexOf(s);
  }


  public List<String> computeJavaScript(List<String> unsorted)
  {
    Comparator<String> comp = new Comparator<String>()
    {
      public int compare(String o1, String o2)
      {
        if (o1.equals(o2))
        {
          return 0;
        }
        int idx1 = getJavaScriptIndex(o1);
        int idx2 = getJavaScriptIndex(o2);
        if (idx1 == idx2)
        {
          return 0;
        }
        return idx1 < getJavaScriptIndex(o2) ? -1 : 1;
      }
    };
    Set<String> updated = replaceOldVersions(unsorted);
    List<String> ret = new ArrayList<String>();
    ret.addAll(updated);
    Collections.sort(ret, comp);
    return ret;
  }


  public Set<String> replaceOldVersions(List<String> unsorted)
  {
    // Map<String, ZJavaScriptInfo> map = new
    Set<String> ret = new HashSet<String>();
    for (String url : unsorted)
    {
      List<ZJavaScriptInfo> libInfos = getLibInfos(url);
      ret.add(libInfos.get(0).getUrl());
    }
    return ret;
  }
}
