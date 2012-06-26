package org.ztemplates.form.impl;

import org.ztemplates.form.visitor.ZIFormVisitor;



public interface ZIFormVisitable
{
  public void visitDepthFirst(ZIFormVisitor vis);


  public void visitBreadthFirst(ZIFormVisitor vis);

}