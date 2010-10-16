package org.ztemplates.render.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.ztemplates.render.ZCss;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.script.deprecated.ZIJavaScriptMeta;

public class ZScriptRepository implements Serializable, ZIScriptRepository
{
  private static final Logger log = Logger.getLogger(ZScriptRepository.class);

  private final ZIJavaScriptMeta meta;

  private final List<String> javaScriptSorted;

  private List<String> cssSorted = new ArrayList<String>();


  public ZScriptRepository(ZIJavaScriptMeta meta, List<ZScript> scripts) throws Exception
  {
    this.meta = meta;
    javaScriptSorted = initJavaScript(scripts);
    initCss(scripts);
  }


  private List<String> initJavaScript(List<ZScript> scripts) throws Exception
  {
    List<String> javaScriptSorted = new ArrayList<String>();
    if (scripts.isEmpty())
    {
      return javaScriptSorted;
    }

    DefaultDirectedGraph<String, DefaultEdge> jsGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    for (ZScript zs : scripts)
    {
      for (ZJavaScript js : zs.javaScript())
      {
        String renamed = ZScriptRepository.renameVariables(js.value());
        if (meta.isStandalone(renamed))
        {
          javaScriptSorted.add(renamed);
        }
        else
        {
          jsGraph.addVertex(renamed);
        }
      }
    }
    for (ZScript zs : scripts)
    {
      String v1 = null;
      for (ZJavaScript js : zs.javaScript())
      {
        String renamed = ZScriptRepository.renameVariables(js.value());
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
    ZScriptRepository.removeCycles(jsGraph);

    TopologicalOrderIterator<String, DefaultEdge> sortedIterator = new TopologicalOrderIterator<String, DefaultEdge>(jsGraph);
    while (sortedIterator.hasNext())
    {
      String js = sortedIterator.next();
      javaScriptSorted.add(js);
    }
    return javaScriptSorted;
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
        log.warn(ZScriptRepository.print(cycleVertices));
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


  private void initCss(List<ZScript> scripts) throws Exception
  {
    if (scripts.isEmpty())
    {
      return;
    }
    DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    for (ZScript zs : scripts)
    {
      for (ZCss css : zs.css())
      {
        g.addVertex(css.value());
      }
    }

    if (g.vertexSet().isEmpty())
    {
      return;
    }

    for (ZScript zs : scripts)
    {
      String v1 = null;
      for (ZCss js : zs.css())
      {
        if (v1 == null)
        {
          v1 = js.value();
        }
        else
        {
          String v2 = js.value();
          g.addEdge(v1, v2);
          v1 = v2;
        }
      }
    }

    ZScriptRepository.removeCycles(g);

    TopologicalOrderIterator<String, DefaultEdge> sortedIterator = new TopologicalOrderIterator<String, DefaultEdge>(g);
    while (sortedIterator.hasNext())
    {
      String js = sortedIterator.next();
      cssSorted.add(js);
    }
  }


  /**
   * need this to recognize the same script, if written with different variable
   * names
   * 
   * @param s
   * @return
   */
  protected static String renameVariables(String s)
  {
    return s.replaceAll("\\$\\{([^\\}])*\\}", "\\$\\{x\\}");
  }


  public int getJavaScriptIndex(String s)
  {
    String name = ZScriptRepository.renameVariables(s);
    return javaScriptSorted.indexOf(name);
  }


  public int getCssIndex(String s)
  {
    return cssSorted.indexOf(s);
  }
}
