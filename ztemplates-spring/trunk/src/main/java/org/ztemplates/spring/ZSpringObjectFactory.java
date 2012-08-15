package org.ztemplates.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.web.ZTemplates;

public class ZSpringObjectFactory implements ZIObjectFactory
{
  private ZIObjectFactory defaultPojoFactory;


  public ZSpringObjectFactory(ZIObjectFactory defaultPojoFactory)
  {
    this.defaultPojoFactory = defaultPojoFactory;
  }


  @Override
  public <T> T newInstance(Class<T> clazz) throws Exception
  {
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
      return defaultPojoFactory.newInstance(clazz);
    }
  }
}