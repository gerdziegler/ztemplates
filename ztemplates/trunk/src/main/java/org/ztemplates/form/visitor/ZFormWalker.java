package org.ztemplates.form.visitor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormWalker
{
  public void visitBreadthFirst(ZIForm obj, ZIFormVisitor vis)
  {
    try
    {
      for (Class clazz = obj.getClass(); ZIForm.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass())
      {
        for (Field f : clazz.getDeclaredFields())
        {
          if (!Modifier.isFinal(f.getModifiers()))
          {
            continue;
          }
          Class type = f.getType();
          if (!f.isAccessible())
          {
            f.setAccessible(true);
          }
          String fieldName = f.getName();
          // ORDER given by extends relation
          // first
          if (ZOperation.class.isAssignableFrom(type))
          {
            ZOperation op = (ZOperation) f.get(obj);
            vis.before(fieldName, op);
            vis.visit(op);
            vis.after(fieldName, op);
          }
          // second
          else if (ZProperty.class.isAssignableFrom(type))
          {
            ZProperty prop = (ZProperty) f.get(obj);
            vis.before(fieldName, prop);
            vis.visit(prop);
            vis.after(fieldName, prop);
          }
          else if (ZIForm.class.isAssignableFrom(type))
          {
            ZIForm fe = (ZIForm) f.get(obj);
            vis.before(fieldName, fe);
            vis.visit(fe);
            visitBreadthFirst(fe, vis);
            vis.after(fieldName, fe);
          }
          else if (ZForm.class.isAssignableFrom(type))
          {
            ZForm fe = (ZForm) f.get(obj);
            vis.before(fieldName, fe);
            vis.visit(fe);
            ZIForm form = fe.getForm();
            if (form != null)
            {
              vis.before(fieldName, form);
              vis.visit(form);
              visitBreadthFirst(form, vis);
              vis.after(fieldName, form);
            }
            vis.after(fieldName, fe);
          }
          else if (ZFormList.class.isAssignableFrom(type))
          {
            ZFormList<ZIForm> list = (ZFormList<ZIForm>) f.get(obj);
            vis.before(fieldName, list);
            vis.visit(list);
            int cnt = list.size();
            for (int i = 0; i < cnt; i++)
            {
              ZIForm form = list.get(i);
              vis.before(fieldName, form, i, cnt);
              visitBreadthFirst(form, vis);
              vis.after(fieldName, form, i, cnt);
            }
            vis.after(fieldName, list);
          }
        }
      }
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException("" + obj, e);
    }
  }


  public void visitDepthFirst(ZIForm obj, ZIFormVisitor vis)
  {
    visitBreadthFirst(obj, vis);
  }
}
