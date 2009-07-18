package org.ztemplates.actions.urlhandler.tree;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.security.ZRoles;
import org.ztemplates.actions.security.ZSecurityException;
import org.ztemplates.actions.urlhandler.ZIUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.ZUrl;
import org.ztemplates.actions.urlhandler.ZUrlFactory;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchedUrl;
import org.ztemplates.actions.urlhandler.tree.process.ZAfterInstruction;
import org.ztemplates.actions.urlhandler.tree.process.ZBeginInstruction;
import org.ztemplates.actions.urlhandler.tree.process.ZBeginNested;
import org.ztemplates.actions.urlhandler.tree.process.ZEndNested;
import org.ztemplates.actions.urlhandler.tree.process.ZIProcessingInstruction;
import org.ztemplates.actions.urlhandler.tree.process.ZSetVariable;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeLiteral;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeNestedBegin;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeNestedEnd;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeSlash;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTail;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTerm;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeVariable;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZTreeUrlHandler implements ZIUrlHandler
{
  static Logger log = Logger.getLogger(ZTreeUrlHandler.class);

  private final ZIUrlFactory urlFactory;

  private final ZISecurityProvider security;

  private final ZMatchTree tree;


  public ZTreeUrlHandler(ZMatchTree tree, ZISecurityProvider security)
  {
    super();
    this.security = security;
    urlFactory = new ZUrlFactory();
    this.tree = tree;
  }


  public void printInfo(StringBuffer sb)
  {
    sb.append(tree.toConsoleString());
  }


  public Object process(String url) throws Exception
  {
    ZUrl zurl = parse(url);
    return process(zurl);
  }


  public Object process(String url, Map<String, String[]> paramMap) throws Exception
  {
    ZUrl zurl = parse(url);
    zurl.getParameterMap().putAll(paramMap);
//    for (Map.Entry<String, String[]> en : paramMap.entrySet())
//    {
//      String key = en.getKey();
//      String[] val = en.getValue();
////      String[] val = new String[oldVal.length];
////      for (int i = 0; i < oldVal.length; i++)
////      {
////        val[i] = oldVal[i];//URLDecoder.decode(oldVal[i]/* , ENCODING */);
////      }
//      zurl.getParameterMap().put(key, val);
//    }
    return process(zurl);
  }


  private Object process(ZUrl zurl) throws Exception
  {
    Map<String, String[]> paramMap = zurl.getParameterMap();
    String url = zurl.getUrl();

    if (log.isDebugEnabled())
    {
      log.debug("processing " + url);
    }

    ZMatchedUrl matched = tree.match(url);
    if (matched == null)
    {
      return null;
    }
    checkSecurity(matched);
    Object ret = process(matched, paramMap);
    return ret;
  }


  private void checkSecurity(ZMatchedUrl matched) throws Exception
  {
    if (security == null)
    {
      return;
    }
    ZRoles roles = matched.getTermList().getRoles();
    if (roles.isSecure())
    {
      for (String role : roles.getRoles())
      {
        if (security.isUserInRole(role))
        {
          return;
        }
      }
      throw new ZSecurityException("access forbidden. user not in one of roles: " + roles);
    }
  }


  private List<ZIProcessingInstruction> computeInstructions(ZMatchedUrl matched) throws Exception
  {
    List<String> values = matched.getValues();
    List<ZTreeTerm> terms = matched.getTermList().getTerms();
    List<ZIProcessingInstruction> instr = new ArrayList<ZIProcessingInstruction>();
    instr.add(new ZBeginInstruction(terms.get(0).getClazz()));
    for (ZTreeTerm et : terms)
    {
      if (et instanceof ZTreeVariable)
      {
        ZTreeVariable v = (ZTreeVariable) et;
        instr.add(new ZSetVariable(v.getClazz(), v.getName(), values.remove(0)));
      }
      else if (et instanceof ZTreeNestedBegin)
      {
        ZTreeNestedBegin v = (ZTreeNestedBegin) et;
        instr.add(new ZBeginNested(v));
      }
      else if (et instanceof ZTreeNestedEnd)
      {
        ZTreeNestedEnd v = (ZTreeNestedEnd) et;
        instr.add(new ZEndNested(v));
      }
      else if (et instanceof ZTreeTail)
      {
        ZTreeTail v = (ZTreeTail) et;
        instr.add(new ZSetVariable(v.getClazz(), v.getName(), values.remove(0)));
      }
      else if (et instanceof ZTreeSlash)
      {
        // do nothing
      }
      else if (et instanceof ZTreeLiteral)
      {
        // do nothing
      }
      else
      {
        throw new Exception("invalid class: " + et.getClass().getName());
      }
    }
    instr.add(new ZAfterInstruction(terms.get(0).getClazz()));

    return instr;
  }


  private Object process(ZMatchedUrl matched, Map<String, String[]> parameters) throws Exception
  {
    List<ZIProcessingInstruction> instr = computeInstructions(matched);
    Object rootPojo = null;
    final Stack<Object> pojos = new Stack<Object>();
    for (ZIProcessingInstruction et : instr)
    {
      if (et instanceof ZSetVariable)
      {
        ZSetVariable v = (ZSetVariable) et;
        Object pojo = pojos.peek();
        ZReflectionUtil.callBeforeVariable(pojo, v.getName());
        ZReflectionUtil.callVariableSetter(pojo, v.getName(), v.getValue());
        ZReflectionUtil.callAfterVariable(pojo, v.getName());
      }
      else if (et instanceof ZBeginNested)
      {
        ZBeginNested v = (ZBeginNested) et;
        Object pojo = pojos.peek();
        Object nestedVal = ZReflectionUtil.callReferenceGetter(pojo, v.getName());
        if (nestedVal == null)
        {
          Class nestedClass = v.getNestedClass();
          nestedVal = ZReflectionUtil.newInstance(nestedClass);
          ZReflectionUtil.callInitReference(pojo, v.getName(), nestedVal);
          ZReflectionUtil.callReferenceSetter(pojo, v.getName(), nestedVal);
        }
        ZReflectionUtil.callBeforeReference(pojo, v.getName());
        pojo = ZReflectionUtil.callReferenceGetter(pojo, v.getName());
        pojos.push(pojo);
        ZReflectionUtil.callBefore(pojo);
      }
      else if (et instanceof ZEndNested)
      {
        Object pojo = pojos.peek();
        ZMatch zmatch = (ZMatch) pojo.getClass().getAnnotation(ZMatch.class);
        ZEndNested v = (ZEndNested) et;
        update(pojo, parameters);
        ZReflectionUtil.callAfter(pojo);
        pojos.pop();
        pojo = pojos.peek();
        ZReflectionUtil.callAfterReference(pojo, v.getName());
        if (zmatch.consume())
        {
          ZReflectionUtil.callReferenceSetter(pojo, v.getName(), null);
        }
      }
      else if (et instanceof ZBeginInstruction)
      {
        ZBeginInstruction v = (ZBeginInstruction) et;
        rootPojo = ZReflectionUtil.newInstance(v.getClazz());
        ZReflectionUtil.callBefore(rootPojo);
        pojos.push(rootPojo);
      }
      else if (et instanceof ZAfterInstruction)
      {
        // ZAfterInstruction v = (ZAfterInstruction)et;
        pojos.pop();
        assert pojos.isEmpty() : "should be empty: " + pojos.toString();
        update(rootPojo, parameters);
        ZReflectionUtil.callAfter(rootPojo);
      }
      else
      {
        throw new Exception("invalid class: " + et.getClass().getName());
      }
    }

    return rootPojo;
  }


  public String createUrl(Object action) throws Exception
  {
    if (action instanceof String)
    {
      return (String) action;
    }

    ZUrl url = urlFactory.createUrl(action);

    String surl = url.getUrl();

    if (security != null && url.getRoles().isSecure())
    {
      surl = security.addSecurityToUrl(surl, url.getRoles().getRoles());
    }

    StringBuffer sb = new StringBuffer(surl);
    boolean first = true;
    for (Map.Entry<String, String[]> en : url.getParameterMap().entrySet())
    {
      String name = en.getKey();
      for (String val : en.getValue())
      {
        if (first)
        {
          sb.append('?');
          first = false;
        }
        else
        {
          sb.append('&');
        }
        sb.append(name);
        sb.append('=');
        val = URLEncoder.encode(val/* , ENCODING */);

        sb.append(val);
      }
    }

    return sb.toString();
  }


  public ZUrl parse(String s) throws Exception
  {
    int idx = s.indexOf('?');
    Map<String, String[]> parameterMap = new HashMap<String, String[]>();
    String url;
    if (idx > 0)
    {
      url = s.substring(0, idx);
      String queryString = s.substring(idx + 1);
      String[] items = queryString.split("&");
      for (String item : items)
      {
        int eq = item.indexOf('=');
        if (eq >= 0)
        {
          String key = item.substring(0, eq);
          if (eq == item.length() - 1)
          {
            parameterMap.put(key, new String[] {});
          }
          else
          {
            String val = item.substring(eq + 1);
            val = URLDecoder.decode(val/* , ENCODING */);
            parameterMap.put(key, new String[]
            {
              val
            });
          }
        }
        else
        {
          parameterMap.put(item, new String[] {});
        }
      }
    }
    else
    {
      url = s;
    }

    url = URLDecoder.decode(url/* , ENCODING */);

    // remove security
    if (security != null)
    {
      url = security.removeSecurityFromUrl(url);
    }

    //add last cut trailing slash
    if (url.length() > 1 && url.charAt(url.length() - 1) == '/')
    {
      url = url.substring(0, url.length() - 1);
    }

    return new ZUrl(url, parameterMap, null);
  }

  // ***************************************************************************************************
  // ***************************************************************************************************
  // ***************************************************************************************************
  // ***************************************************************************************************
  // ***************************************************************************************************
  // ***************************************************************************************************


  private static void update(Object pojo, Map<String, String[]> parameters) throws Exception
  {
    ZOperation operation = null;
    ZMatch zmatch = (ZMatch) pojo.getClass().getAnnotation(ZMatch.class);
    String[] parameterDefs = zmatch.parameters();
    for (String name : parameterDefs)
    {
      String[] value = parameters.get(name);
      ZProperty assignedProp = ZReflectionUtil.callParameterSetter(pojo, name, value);
      if (assignedProp instanceof ZOperation)
      {
        if (operation != null)
        {
          throw new Exception("Only one operation call per request allowed: " + operation + " "
              + assignedProp);
        }
        operation = (ZOperation) assignedProp;
      }
      // eat the parameters
      parameters.remove(name);
    }

//    String form = zmatch.form();
//    if (form.length() > 0)
//    {
//      ZReflectionUtil.callBeforeForm(pojo, form);
//      ZIFormElement formElement = ZReflectionUtil.callFormGetter(pojo, form);
//      ZFormElementMirror mirr = new ZFormElementMirror(formElement);
//      mirr.process();      
//      ZReflectionUtil.callAfterForm(pojo, form);
//    }

//    ZActionPojoMirror pojoMirr = new ZActionPojoMirror(pojo);
//    pojoMirr.update();
  }



  //  private static void updateOnAJAXCall(ZIFormElement form, String propertyChanged,
  //      Set<ZProperty> properties, Set<ZOperation> operations) throws Exception
  //  {
  //    for (ZProperty p : properties)
  //    {
  //      p.setAjaxCall(true);
  //    }
  //    for (ZOperation op : operations)
  //    {
  //      op.setAjaxCall(true);
  //    }
  //
  //    ZProperty prop = (ZProperty) ZReflectionUtil.getObjectByBeanPath(form, propertyChanged);
  //    prop.fireAjaxChangeListeners();
  //
  //    for (ZProperty p : properties)
  //    {
  //      p.revalidate();
  //    }
  //    for (ZOperation op : operations)
  //    {
  //      op.revalidate();
  //    }

  //    Set<ZProperty> notValidatedProperties = new HashSet<ZProperty>(properties);
  //    DefaultDirectedGraph<ZProperty, ZDependencyEdge> succesorGraph = new DefaultDirectedGraph<ZProperty, ZDependencyEdge>(ZDependencyEdge.class);

  //    succesorGraph.addVertex(prop);
  //    computeSuccessorDependencyGraph(prop, succesorGraph);

  //    Set<ZProperty> predecessorProperties = new HashSet<ZProperty>(properties);
  //    predecessorProperties.removeAll(succesorGraph.vertexSet());
  //    for (ZProperty p : predecessorProperties)
  //    {
  //      notValidatedProperties.remove(p);
  //      p.revalidate();
  //    }
  //    notValidatedProperties.remove(prop);
  //    prop.revalidate();
  //
  //    TopologicalOrderIterator<ZProperty, ZDependencyEdge> topologicalOrderIterator = new TopologicalOrderIterator<ZProperty, ZDependencyEdge>(succesorGraph);
  //    // eat first one
  //    List<ZProperty> processed = new ArrayList<ZProperty>();
  //    processed.add(topologicalOrderIterator.next());
  //    while (topologicalOrderIterator.hasNext())
  //    {
  //      ZProperty crtProp = topologicalOrderIterator.next();
  //      ZDependencyEdge updater = null;
  //      for (ZProperty crtProcessed : processed)
  //      {
  //        if (succesorGraph.containsEdge(crtProcessed, crtProp))
  //        {
  //          ZDependencyEdge crtEdge = succesorGraph.getEdge(crtProcessed, crtProp);
  //          if (updater == null)
  //          {
  //            updater = crtEdge;
  //          }
  //          else if (updater.getValueUpdater() != crtEdge.getValueUpdater())
  //          {
  //            throw new Exception("different valueupdaters on edges " + updater + "  and  " + crtEdge);
  //          }
  //        }
  //      }
  //      if (log.isDebugEnabled())
  //      {
  //        log.debug("calling updater " + updater.getTarget().getName());
  //      }
  //      updater.getValueUpdater().updateValue();
  //      notValidatedProperties.remove(crtProp);
  //      crtProp.revalidate();
  //      processed.add(crtProp);
  //    }
  //    for (ZProperty p : notValidatedProperties)
  //    {
  //      p.revalidate();
  //    }
  //    for (ZOperation op : operations)
  //    {
  //      op.revalidate();
  //    }
  //  }

  //  private static void computeSuccessorDependencyGraph(ZProperty prop,
  //      DefaultDirectedGraph<ZProperty, ZDependencyEdge> graph) throws Exception
  //  {
  //    Set<ZDependencyEdge> depList = prop.getDependenciesOut();
  //    for (ZDependencyEdge edge : depList)
  //    {
  //      if (!graph.containsVertex(edge.getTarget()))
  //      {
  //        graph.addVertex(edge.getTarget());
  //        computeSuccessorDependencyGraph(edge.getTarget(), graph);
  //      }
  //      graph.addEdge(edge.getSource(), edge.getTarget(), edge);
  //    }
  //  }

}
