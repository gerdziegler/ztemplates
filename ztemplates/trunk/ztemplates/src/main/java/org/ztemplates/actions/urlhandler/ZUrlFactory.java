package org.ztemplates.actions.urlhandler;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;
import org.ztemplates.actions.expression.ZExpression;
import org.ztemplates.actions.expression.ZLiteral;
import org.ztemplates.actions.expression.ZNestedExpression;
import org.ztemplates.actions.expression.ZOptionalExpression;
import org.ztemplates.actions.expression.ZSlash;
import org.ztemplates.actions.expression.ZTail;
import org.ztemplates.actions.expression.ZTerm;
import org.ztemplates.actions.expression.ZVariable;
import org.ztemplates.actions.security.ZRoles;
import org.ztemplates.actions.util.ZReflectionUtil;

public class ZUrlFactory implements ZIUrlFactory
{

  static Logger log = Logger.getLogger(ZUrlFactory.class);


  public ZUrlFactory()
  {
  }


  public ZUrl createUrl(Object obj)
  {
    Map<String, String[]> parameterMap = new HashMap<String, String[]>();
    ZRoles roles = new ZRoles();
    try
    {
      StringBuffer sb = new StringBuffer();
      compute(obj, sb, parameterMap, roles);
      String url = sb.toString();
      ZUrl ret = new ZUrl(url, parameterMap, roles);
      return ret;
    }
    catch (Exception e)
    {
      log.error(obj, e);
      return new ZUrl(e.getMessage(), parameterMap, roles);
    }
  }


  protected boolean compute(Object obj, StringBuffer sb, Map<String, String[]> parameterMap,
      ZRoles roles) throws Exception
  {
    Class clazz = obj.getClass();
    ZMatch match = (ZMatch) clazz.getAnnotation(ZMatch.class);
    if (match == null)
    {
      sb.append(obj.toString());
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


  private boolean compute(ZExpression expression, Object obj, StringBuffer sb,
      Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    boolean empty = false;

    StringBuffer crtsb = new StringBuffer();
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
        empty = compute((ZVariable) t, obj, crtsb);
      }
      else if (t instanceof ZOptionalExpression)
      {
        compute((ZOptionalExpression) t, obj, crtsb, parameterMap, roles);
      }
      else if (t instanceof ZNestedExpression)
      {
        empty = compute((ZNestedExpression) t, obj, crtsb, parameterMap, roles);
      }
      else if (t instanceof ZTail)
      {
        empty = compute((ZTail) t, obj, crtsb);
      }
      else
      {
        throw new Exception("unsupported term-type: " + t.getClass().getName());
      }
      if (empty)
      {
        return true;
      }
    }
    sb.append(crtsb);
    return false;
  }


  private void compute(ZLiteral l, Object handler, StringBuffer sb) throws Exception
  {
    sb.append(l.getText());
  }


  private void compute(ZSlash l, Object handler, StringBuffer sb) throws Exception
  {
    sb.append(l.getText());
  }


  private boolean compute(ZVariable v, Object obj, StringBuffer sb) throws Exception
  {
    String val = ZReflectionUtil.callVariableGetter(obj, v.getName());
    if (val == null)
    {
      return true;
    }
    else
    {
      val = URLEncoder.encode(val/* , encoding */);
      sb.append(val);
      return false;
    }
  }


  private boolean compute(ZTail v, Object obj, StringBuffer sb) throws Exception
  {
    String val = ZReflectionUtil.callVariableGetter(obj, v.getName());
    if (val == null)
    {
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


  private void compute(ZOptionalExpression oe, Object obj, StringBuffer sb,
      Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    StringBuffer crtsb = new StringBuffer();
    boolean empty = compute(oe.getOptionalExpression(), obj, crtsb, parameterMap, roles);
    if (!empty)
    {
      sb.append(crtsb);
    }
  }


  private boolean compute(ZNestedExpression ne, Object obj, StringBuffer sb,
      Map<String, String[]> parameterMap, ZRoles roles) throws Exception
  {
    Object nested = ZReflectionUtil.callReferenceGetter(obj, ne.getName());
    if (nested != null)
    {
      return compute(nested, sb, parameterMap, roles);
    }
    else
    {
      return true;
    }
  }
}
