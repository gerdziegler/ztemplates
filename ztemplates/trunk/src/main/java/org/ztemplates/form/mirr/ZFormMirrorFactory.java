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
        @Override
        public void before(String fieldName, ZProperty prop)
        {
          ZIPropertyContainer cont = (ZIPropertyContainer) mirrorStack.peek();
          cont.getProperties().add(prop);
        }


        @Override
        public void after(String fieldName, ZProperty prop)
        {
        }


        @Override
        public void before(String fieldName, ZOperation op)
        {
          ZIOperationContainer cont = (ZIOperationContainer) mirrorStack.peek();
          cont.getOperations().add(op);
        }


        @Override
        public void after(String fieldName, ZOperation op)
        {
        }


        @Override
        public void before(String fieldName, ZIForm form)
        {
          ZIFormContainer cont = (ZIFormContainer) mirrorStack.peek();
          ZFormMirror formMirror = new ZFormMirror(form);
          cont.getForms().add(formMirror);
          mirrorStack.push(formMirror);
        }


        @Override
        public void before(String fieldName, ZIForm form, int idx, int cnt)
        {
          ZIFormContainer cont = (ZIFormContainer) mirrorStack.peek();
          ZFormMirror formMirror = new ZFormMirror(form);
          cont.getForms().add(formMirror);
          mirrorStack.push(formMirror);
        }


        @Override
        public void after(String fieldName, ZIForm form, int idx, int cnt)
        {
          mirrorStack.pop();
        }


        @Override
        public void after(String fieldName, ZIForm form)
        {
          mirrorStack.pop();
        }


        //        @Override
        //        public void before(String fieldName, ZFormList<ZIForm> list)
        //        {
        //          ZIFormListContainer cont = (ZIFormListContainer) mirrorStack.peek();
        //          ZFormListMirror formListMirror = new ZFormListMirror(list);
        //          cont.getFormLists().add(formListMirror);
        //          mirrorStack.push(formListMirror);
        //        }

        //        @Override
        //        public void after(String fieldName, ZFormList<ZIForm> list)
        //        {
        //          mirrorStack.pop();
        //        }

        @Override
        public void before(String fieldName, ZFormList map)
        {
          ZIFormMapContainer cont = (ZIFormMapContainer) mirrorStack.peek();
          ZFormMapMirror mirror = new ZFormMapMirror(map);
          cont.getFormMaps().add(mirror);
          mirrorStack.push(mirror);
        }


        @Override
        public void after(String fieldName, ZFormList map)
        {
          mirrorStack.pop();
        }


        @Override
        public void before(String fieldName, ZForm form)
        {
          ZIFormHolderContainer cont = (ZIFormHolderContainer) mirrorStack.peek();
          ZFormHolderMirror formHolderMirror = new ZFormHolderMirror(form);
          cont.getFormHolders().add(formHolderMirror);
          mirrorStack.push(formHolderMirror);
        }


        @Override
        public void after(String fieldName, ZForm form)
        {
          mirrorStack.pop();
        }

      };
    walker.visit(form, visitor);
    return formMirror;
  }

}
