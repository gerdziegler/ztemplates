package org.ztemplates.actions.urlhandler.tree;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.ztemplates.actions.ZIFormAction;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.security.ZRoles;
import org.ztemplates.actions.security.ZSecurityException;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.ZUrl;
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
import org.ztemplates.actions.util.impl.ZReflectionUtil;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZTemplates;

public class ZTreeUrlHandler implements ZIUrlHandler
{
  static Logger log = Logger.getLogger(ZTreeUrlHandler.class);

  private final ZISecurityProvider security;

  private final ZISecureUrlDecorator secureUrlDecorator;

  // private final ZIActionApplicationContext applicationContext;

  private final ZMatchTree tree;

  private final String encoding;

  private Stack<Object> pojos;

  private Stack<ZBeginNested> nested;


  public ZTreeUrlHandler(ZMatchTree tree, ZISecurityProvider security, ZISecureUrlDecorator secureUrlDecorator,
      String encoding)
  {
    super();
    this.encoding = encoding != null ? encoding : "ISO-8859-1";
    this.security = security;
    this.tree = tree;
    this.secureUrlDecorator = secureUrlDecorator;
    // this.applicationContext = applicationContext;
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
    // for (Map.Entry<String, String[]> en : paramMap.entrySet())
    // {
    // String key = en.getKey();
    // String[] val = en.getValue();
    // // String[] val = new String[oldVal.length];
    // // for (int i = 0; i < oldVal.length; i++)
    // // {
    // // val[i] = oldVal[i];//URLDecoder.decode(oldVal[i]/* , ENCODING */);
    // // }
    // zurl.getParameterMap().put(key, val);
    // }
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
    this.pojos = new Stack<Object>();
    this.nested = new Stack<ZBeginNested>();
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
        nested.push(v);
        Object pojo = pojos.peek();
        Object nestedVal = ZReflectionUtil.callReferenceGetter(pojo, v.getName());
        if (nestedVal == null)
        {
          Class nestedClass = v.getNestedClass();
          nestedVal = createActionPojo(nestedClass);
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
        ZMatch zmatch = pojo.getClass().getAnnotation(ZMatch.class);
        ZEndNested v = (ZEndNested) et;
        UpdateResult updateResult = update(pojo, parameters);
        callAfterOrAfterOperation(pojo, updateResult.operationToCall);

        pojos.pop();
        nested.pop();
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
        rootPojo = createActionPojo(v.getClazz());
        ZReflectionUtil.callBefore(rootPojo);
        pojos.push(rootPojo);
      }
      else if (et instanceof ZAfterInstruction)
      {
        // ZAfterInstruction v = (ZAfterInstruction)et;
        pojos.pop();
        assert pojos.isEmpty() : "should be empty: " + pojos.toString();
        UpdateResult updateResult = update(rootPojo, parameters);
        callAfterOrAfterOperation(rootPojo, updateResult.operationToCall);
        // if (updateResult.operationToCall != null &&
        // updateResult.operationToCall.getOperationListener() != null)
        // {
        // updateResult.operationToCall.getOperationListener().exec();
        // }
        // else
        // {
        // ZReflectionUtil.callAfter(rootPojo);
        // }
      }
      else
      {
        throw new Exception("invalid class: " + et.getClass().getName());
      }
    }

    return rootPojo;
  }


  private Object createActionPojo(Class clazz) throws Exception
  {
    // is this class spring enabled?
    Component comp = (Component) clazz.getAnnotation(Component.class);
    if (comp != null)
    {
      WebApplicationContext ctx = WebApplicationContextUtils
          .getRequiredWebApplicationContext(ZTemplates.getServletService().getRequest().getSession().getServletContext());
      String name = comp.value();
      if (name.length() > 0)
      {
        return ctx.getBean(name, clazz);
      }
      else
      {
        return ctx.getBean(clazz);
      }
    }
    else
    {
      return ZReflectionUtil.newInstance(clazz);
    }
  }


  private void callAfterOrAfterOperation(Object pojo, ZOperation operationToCall) throws Exception
  {
    // no operation --> call after
    if (operationToCall == null)
    {
      ZReflectionUtil.callAfter(pojo);
      return;
    }

    // operation with listener --> call it
    if (operationToCall.getOperationListener() != null)
    {
      operationToCall.getOperationListener().exec();
      return;
    }

    // try callback
    boolean callBackCalled = ZReflectionUtil.callAfterOperation(pojo, operationToCall.getName());
    if (callBackCalled)
    {
      return;
    }

    ZReflectionUtil.callAfter(pojo);
  }


  // private void storeSessionForm(ZISessionFormModel sessionForm,
  // ZFormModelWrapper mirr) throws Exception
  // {
  // String key = sessionForm.getSessionKey();
  // if (key == null)
  // {
  // return;
  // }
  // ZFormValues values = (ZFormValues) applicationContext.getAttribute(key);
  // if (values == null)
  // {
  // values = new ZFormValues();
  // applicationContext.setAttribute(key, values);
  // }
  // mirr.writeToValues(values);
  // }

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
            parameterMap.put(key, new String[]
            {});
          }
          else
          {
            String val = item.substring(eq + 1);
            val = URLDecoder.decode(val, encoding/* , ENCODING */);
            parameterMap.put(key, new String[]
            {
                val
            });
          }
        }
        else
        {
          parameterMap.put(item, new String[]
          {});
        }
      }
    }
    else
    {
      url = s;
    }

    url = URLDecoder.decode(url/* , ENCODING */);

    // remove security
    if (secureUrlDecorator != null)
    {
      url = secureUrlDecorator.removeSecurityFromUrl(url);
    }

    // add last cut trailing slash
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

  private static class UpdateResult
  {
    public ZOperation operationToCall;

    // public ZISessionFormModel sessionForm;

    public ZFormWrapper formWrapper;
  }


  private UpdateResult update(Object pojo, Map<String, String[]> parameters) throws Exception
  {
    UpdateResult ret = new UpdateResult();
    ZMatch zmatch = pojo.getClass().getAnnotation(ZMatch.class);
    String[] parameterDefs = zmatch.parameters();
    for (String name : parameterDefs)
    {
      String[] value = parameters.get(name);
      ZProperty assignedProp = ZReflectionUtil.callParameterSetter(pojo, name, value);
      if (assignedProp instanceof ZOperation)
      {
        if (ret.operationToCall != null)
        {
          throw new Exception("Only one operation call per request allowed: " + ret.operationToCall + " " + assignedProp);
        }
        ret.operationToCall = (ZOperation) assignedProp;
      }
      // eat the parameters
      // parameters.remove(name);
    }

    String formName = zmatch.form();
    boolean isActionForm = pojo instanceof ZIFormAction;
    if (formName.length() > 0 || isActionForm)
    {
      if (isActionForm)
      {
        if (formName.length() > 0)
        {
          if (!formName.equals("form"))
          {
            throw new Exception("action pojo class [" + pojo.getClass().getName() + "] implements " + ZIFormAction.class.getName()
                + " and has wrong value in annotation " + ZMatch.class.getName() + ": form='" + formName
                + "'. Change to form='form' or do not specify at all (leave empty).");
          }
        }
        else
        {
          formName = "form";
        }
      }

      ZFormValues formValues = new ZFormValues();
      formValues.getValues().putAll(parameters);

      ZReflectionUtil.callBeforeForm(pojo, formName);
      ZIForm form = (ZIForm) ZReflectionUtil.callFormGetter(pojo, formName);
      ret.formWrapper = new ZFormWrapper(form);

      // if (form instanceof ZISessionFormModel)
      // {
      // ret.sessionForm = (ZISessionFormModel) form;
      // String key = ret.sessionForm.getSessionKey();
      // if (key != null)
      // {
      // ZFormValues values = (ZFormValues)
      // applicationContext.getAttribute(key);
      // if (values != null)
      // {
      // ret.formWrapper.readFromValues(values);
      // }
      // }
      // }

      ZFormMembers assigned = ret.formWrapper.readFromValues(formValues);
      ZOperation op;
      int opCnt = assigned.getOperations().size();
      if (opCnt == 0)
      {
        op = null;
      }
      else if (opCnt == 1)
      {
        op = assigned.getOperations().get(0);
      }
      else
      // if (opCnt > 1)
      {
        throw new Exception("Only one operation call per request allowed: " + assigned.getOperations());
      }

      ZReflectionUtil.callAfterForm(pojo, "form");
      ret.operationToCall = op;
    }
    return ret;
  }


  public Object getNestedActionParent()
  {
    return pojos.get(pojos.size() - 2);
  }


  public String getNestedActionName()
  {
    return nested.peek().getName();
  }
}
