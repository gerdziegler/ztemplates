package org.ztemplates.actions.urlhandler.tree.match;

import java.util.List;

import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;

public class ZMatchedUrl
{
  private final ZTreeTermList termList;

  private final List<String> values;


  public ZMatchedUrl(ZTreeTermList terms, List<String> values)
  {
    super();
    this.termList = terms;
    this.values = values;
  }


  public ZTreeTermList getTermList()
  {
    return termList;
  }


  public List<String> getValues()
  {
    return values;
  }
}
