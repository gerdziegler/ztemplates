package org.ztemplates.actions.urlhandler.tree.term;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.ztemplates.actions.ZSecure;
import org.ztemplates.actions.security.ZRoles;
import org.ztemplates.actions.security.ZSecurityException;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZTreeTermList
{
  private final List<ZTreeTerm> terms = new ArrayList<ZTreeTerm>();

  private ZRoles roles;


  public ZTreeTermList()
  {
  }


  public ZTreeTermList(List<ZTreeTerm> terms)
  {
    this.terms.addAll(terms);
  }


  public List<ZTreeTerm> getTerms()
  {
    return terms;
  }


  public static ZTreeTermList append(ZTreeTermList s1, ZTreeTermList s2)
  {

    ZTreeTermList ret = new ZTreeTermList();

    ret.terms.addAll(s1.terms);
    ret.terms.addAll(s2.terms);

    return ret;
  }


  @Override
  public String toString()
  {
    // StringBuffer sb = new StringBuffer();
    // toXml(sb, 0);
    // return sb.toString();
    return printToConsole();
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<parsed-term-list>");
    for (ZTreeTerm term : terms)
    {
      term.toXml(sb, depth + 1);
    }
    ZFormatUtil.indent(sb, depth);
    sb.append("</parsed-term-list>");
  }


  public String printToConsole()
  {
    StringBuffer sb = new StringBuffer();

    Set<String> handlerSet = new TreeSet<String>();
    for (ZTreeTerm t : terms)
    {
      // sb.append(t.toDefinition());
      handlerSet.add(t.getClazz().getName());
      // sb.append('[');
      // sb.append(t.getClazz().getSimpleName());
      // sb.append(']');
      // sb.append(' ');
    }

    for (String s : handlerSet)
    {
      sb.append(' ');
      sb.append(s);
    }
    return sb.toString();
  }


  public ZRoles getRoles() throws ZSecurityException
  {
    if (roles == null)
    {
      roles = new ZRoles();
      for (ZTreeTerm t : terms)
      {
        ZSecure sec = (ZSecure) t.getClazz().getAnnotation(ZSecure.class);
        if (sec != null)
        {
          String[] r = sec.value();
          roles.add(r);
          if (roles.isImpossible())
          {
            throw new ZSecurityException("role conditions can never be satisfied: "
                + printToConsole());
          }
        }
      }
    }
    return roles;
  }

}
