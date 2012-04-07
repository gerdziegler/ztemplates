package org.ztemplates.actions;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.actions.expression.ZExpression;
import org.ztemplates.actions.expression.ZLiteral;
import org.ztemplates.actions.expression.ZNestedExpression;
import org.ztemplates.actions.expression.ZOptionalExpression;
import org.ztemplates.actions.expression.ZSlash;
import org.ztemplates.actions.expression.ZTail;
import org.ztemplates.actions.expression.ZTerm;
import org.ztemplates.actions.expression.ZVariable;
import org.ztemplates.actions.security.ZRoles;
import org.ztemplates.actions.urlhandler.ZUrl;
import org.ztemplates.actions.util.impl.ZReflectionUtil;

public class ZUrlFactory implements ZIUrlFactory
{
  static Logger log = Logger.getLogger(ZUrlFactory.class);

  private final String encoding;


  public ZUrlFactory(String encoding)
  {
    this.encoding = encoding != null ? encoding : "ISO-8859-1";
  }


  public String createUrl(Object action) throws Exception
  {
    if (action instanceof String)
    {
      return (String) action;
    }

    ZUrl url = createZUrl(action);

    String surl = url.getUrl();

    StringBuilder sb = new StringBuilder(surl);
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
        val = URLEncoder.encode(val, encoding/* , ENCODING */);

        sb.append(val);
      }
    }

    return sb.toString();
  }


  private ZUrl createZUrl(Object obj) throws Exception
  {
    Map<String, String[]> parameterMap = new HashMap<String, String[]>();
    ZRoles roles = new ZRoles();
    StringBuilder sb = new StringBuilder();
    boolean empty = compute(obj, sb, parameterMap, roles);
    if (empty)
    {
      throw new Exception("error while creating url: missing required value for " + obj.getClass().getName() + " --- " + sb.toString());
    }
    String url = sb.toString();
    ZUrl ret = new ZUrl(url, parameterMap, roles);
    return ret;
  }


  protected boolean compute(Object obj, StringBuilder sb, Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    Class clazz = obj.getClass();
    ZMatch match = (ZMatch) clazz.getAnnotation(ZMatch.class);
    if (match == null)
    {
      sb.append("[no @ZMatch annotation on " + obj.getClass().getName() + "]");
      return true;
    }
    else
    {
      ZSecure sec = (ZSecure) clazz.getAnnotation(ZSecure.class);
      if (sec != null)
      {
        roles.add(sec.value());
      }

      String[] parameters = match.parameters();
      for (String param : parameters)
      {
        String[] val = ZReflectionUtil.callParameterGetter(obj, param);
        if (val != null)
        {
          parameterMap.put(param, val);
        }
      }
      ZExpression exp = new ZExpression(match.value());
      return compute(exp, obj, sb, parameterMap, roles);
    }
  }


  private boolean compute(ZExpression expression, Object obj, StringBuilder sb, Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    boolean empty = false;

    StringBuilder crtsb = new StringBuilder();
    for (ZTerm t : expression.getContent())
    {
      if (t instanceof ZLiteral)
      {
        compute((ZLiteral) t, obj, crtsb);
      }
      else if (t instanceof ZSlash)
      {
        compute((ZSlash) t, obj, crtsb);
      }
      else if (t instanceof ZVariable)
      {
        empty |= compute((ZVariable) t, obj, crtsb);
      }
      else if (t instanceof ZOptionalExpression)
      {
        compute((ZOptionalExpression) t, obj, crtsb, parameterMap, roles);
      }
      else if (t instanceof ZNestedExpression)
      {
        empty |= compute((ZNestedExpression) t, obj, crtsb, parameterMap, roles);
      }
      else if (t instanceof ZTail)
      {
        empty |= compute((ZTail) t, obj, crtsb);
      }
      else
      {
        throw new Exception("unsupported term-type: " + t.getClass().getName());
      }
    }
    sb.append(crtsb);
    return empty;
  }


  private void compute(ZLiteral l, Object handler, StringBuilder sb) throws Exception
  {
    sb.append(l.getText());
  }


  private void compute(ZSlash l, Object handler, StringBuilder sb) throws Exception
  {
    sb.append(l.getText());
  }


  private boolean compute(ZVariable v, Object obj, StringBuilder sb) throws Exception
  {
    String val = ZReflectionUtil.callVariableGetter(obj, v.getName());
    if (val == null)
    {
      sb.append("${");
      sb.append(v.getName());
      sb.append("}");
      return true;
    }
    else
    {
      val = URLEncoder.encode(val/* , encoding */);
      sb.append(val);
      return false;
    }
  }


  private boolean compute(ZTail v, Object obj, StringBuilder sb) throws Exception
  {
    String val = ZReflectionUtil.callVariableGetter(obj, v.getName());
    if (val == null)
    {
      sb.append("*{");
      sb.append(v.getName());
      sb.append("}");
      return true;
    }
    else
    {
      // no encoding here, tail can contain /
      // val = URLEncoder.encode(val/*, encoding*/);
      sb.append(val);
      return false;
    }
  }


  private void compute(ZOptionalExpression oe, Object obj, StringBuilder sb, Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    StringBuilder crtsb = new StringBuilder();
    boolean empty = compute(oe.getOptionalExpression(), obj, crtsb, parameterMap, roles);
    if (!empty)
    {
      sb.append(crtsb);
    }
  }


  private boolean compute(ZNestedExpression ne, Object obj, StringBuilder sb, Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    Object nested = ZReflectionUtil.callReferenceGetter(obj, ne.getName());
    if (nested != null)
    {
      return compute(nested, sb, parameterMap, roles);
    }
    else
    {
      sb.append("#{");
      sb.append(ne.getName());
      sb.append("}");
      return true;
    }
  }
}
