package org.ztemplates.form.mirr;

import java.util.Stack;

import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.visitor.ZFormWalker;
import org.ztemplates.form.visitor.ZIFormVisitor;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormMirrorFactory
{
  private ZFormWalker walker = new ZFormWalker();


  public ZFormMirror createFormMirror(ZIForm form)
  {
    final Stack<ZIFormMirror> mirrorStack = new Stack<ZIFormMirror>();
    ZFormMirror formMirror = new ZFormMirror(form);
    mirrorStack.push(formMirror);

    ZIFormVisitor visitor = new ZIFormVisitor()
      {
        public void before(String fieldName, ZProperty prop)
        {
        }


        public void visit(ZProperty prop)
        {
          ZIPropertyContainer cont = (ZIPropertyContainer) mirrorStack.peek();
          cont.getProperties().add(prop);
        }


        public void after(String fieldName, ZProperty prop)
        {
        }


        public void before(String fieldName, ZOperation op)
        {
        }


        public void visit(ZOperation op)
        {
          ZIOperationContainer cont = (ZIOperationContainer) mirrorStack.peek();
          cont.getOperations().add(op);
        }


        public void after(String fieldName, ZOperation op)
        {
        }


        public void before(String fieldName, ZIForm form)
        {
          ZIFormContainer cont = (ZIFormContainer) mirrorStack.peek();
          ZFormMirror formMirror = new ZFormMirror(form);
          cont.getForms().add(formMirror);
          mirrorStack.push(formMirror);
        }


        public void before(String fieldName, ZIForm form, int idx, int cnt)
        {
          ZIFormContainer cont = (ZIFormContainer) mirrorStack.peek();
          ZFormMirror formMirror = new ZFormMirror(form);
          cont.getForms().add(formMirror);
          mirrorStack.push(formMirror);
        }


        public void visit(ZIForm form)
        {
        }


        public void after(String fieldName, ZIForm form, int idx, int cnt)
        {
          mirrorStack.pop();
        }


        public void after(String fieldName, ZIForm form)
        {
          mirrorStack.pop();
        }


        public void before(String fieldName, ZFormList<ZIForm> list)
        {
          ZIFormListContainer cont = (ZIFormListContainer) mirrorStack.peek();
          ZFormListMirror formListMirror = new ZFormListMirror(list);
          cont.getFormLists().add(formListMirror);
          mirrorStack.push(formListMirror);
        }


        public void visit(ZFormList<ZIForm> list)
        {
        }


        public void after(String fieldName, ZFormList<ZIForm> list)
        {
          mirrorStack.pop();
        }


        public void before(String fieldName, ZForm form)
        {
          ZIFormHolderContainer cont = (ZIFormHolderContainer) mirrorStack.peek();
          ZFormHolderMirror formHolderMirror = new ZFormHolderMirror(form);
          cont.getFormHolders().add(formHolderMirror);
          mirrorStack.push(formHolderMirror);
        }


        public void visit(ZForm form)
        {
        }


        public void after(String fieldName, ZForm form)
        {
          mirrorStack.pop();
        }
      };
    walker.visitDepthFirst(form, visitor);
    return formMirror;
  }

}
