package org.ztemplates.form.visitor;

import java.util.Stack;

import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.ZINamedFormElement;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZStackingFormVisitor implements ZIFormVisitor
{
  protected Stack<ZINamedFormElement> stack = new Stack<ZINamedFormElement>();


  public void before(String fieldName, ZProperty prop)
  {
    stack.push(prop);
  }


  public void visit(ZProperty prop)
  {
  }


  public void after(String fieldName, ZProperty prop)
  {
    stack.pop();
  }


  public void before(String fieldName, ZOperation op)
  {
    stack.push(op);
  }


  public void visit(ZOperation op)
  {
  }


  public void after(String fieldName, ZOperation op)
  {
    stack.pop();
  }


  public void before(String fieldName, ZIForm form)
  {
  }


  public void visit(ZIForm form)
  {
  }


  public void after(String fieldName, ZIForm form)
  {
  }


  public void before(String fieldName, ZFormList<ZIForm> list)
  {
    stack.push(list);
  }


  public void visit(ZFormList<ZIForm> list)
  {
  }


  public void after(String fieldName, ZFormList<ZIForm> list)
  {
    stack.pop();
  }


  public void before(String fieldName, ZForm form)
  {
    stack.push(form);
  }


  public void visit(ZForm form)
  {
  }


  public void after(String fieldName, ZForm form)
  {
    stack.pop();
  }


  public void before(String fieldName, ZIForm form, int idx, int cnt)
  {
  }


  public void after(String fieldName, ZIForm form, int idx, int cnt)
  {
  }
}