package org.ztemplates.form.visitor;

import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.ZIdForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZAbstractFormVisitor implements ZIFormVisitor
{

  @Override
  public void before(String fieldName, ZProperty prop)
  {

  }


  @Override
  public void after(String fieldName, ZProperty prop)
  {

  }


  @Override
  public void before(String fieldName, ZOperation op)
  {

  }


  @Override
  public void after(String fieldName, ZOperation op)
  {

  }


  @Override
  public void before(String fieldName, ZIForm form)
  {

  }


  @Override
  public void before(String fieldName, ZIForm form, int idx, int cnt)
  {

  }


  @Override
  public void before(String fieldName, ZForm form)
  {

  }


  @Override
  public <K> void before(String fieldName, ZFormList<ZIdForm<K>, K> map)
  {

  }


  @Override
  public void after(String fieldName, ZIForm form, int idx, int cnt)
  {

  }


  @Override
  public void after(String fieldName, ZIForm form)
  {

  }


  @Override
  public void after(String fieldName, ZForm form)
  {

  }


  @Override
  public <K> void after(String fieldName, ZFormList<ZIdForm<K>, K> map)
  {

  }
}