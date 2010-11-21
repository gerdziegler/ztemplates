package org.ztemplates.actions.urlhandler.tree.match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZMatchTreeNode implements Serializable, Comparable<ZMatchTreeNode>
{
  static Logger log = Logger.getLogger(ZMatchTreeNode.class);

  private List<ZMatchTreeNode> children = new ArrayList<ZMatchTreeNode>();

  private List<ZSegment> segments = new ArrayList<ZSegment>();

  private ZTreeTermList handler;


  public ZMatchTreeNode()
  {
  }


  public List<ZMatchTreeNode> getChildren()
  {
    return children;
  }


  public List<ZSegment> getSegments()
  {
    return segments;
  }


  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    toXml(sb, 0);
    return sb.toString();
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<match-tree-node>");
    ZFormatUtil.indent(sb, depth);
    sb.append("<segments>");
    for (ZSegment term : segments)
    {
      term.toXml(sb, depth + 1);
    }
    ZFormatUtil.indent(sb, depth);
    sb.append("</segments>");
    if (!children.isEmpty())
    {
      ZFormatUtil.indent(sb, depth);
      sb.append("<children>");
      for (ZMatchTreeNode child : children)
      {
        child.toXml(sb, depth + 1);
      }
      ZFormatUtil.indent(sb, depth);
      sb.append("</children>");
    }
    ZFormatUtil.indent(sb, depth);
    sb.append("</match-tree-node>");
  }


  public ZTreeTermList getHandler()
  {
    return handler;
  }


  public void setHandler(ZTreeTermList handler)
  {
    this.handler = handler;
  }


  public ZTreeTermList match(String url, List<String> values)
  {
    assert (url.charAt(0) == '/');
    String myUrl = url.substring(1);

    if (myUrl.length() == 0)
    {
      if (segments.size() == 0)
      {
        return getHandler();
      }
      else
      {
        return null;
      }
    }

    String restUrl = matchSegments(myUrl, values);
    if (restUrl == null)
    {
      // no match
      return null;
    }
    else if (restUrl.length() == 0)
    {
      // match
      return getHandler();
    }
    else if (restUrl.charAt(0) != '/')
    {
      // no match
      return null;
    }
    else
    {
      // match children
      return matchChildren(restUrl, values);
    }
  }


  private ZTreeTermList matchChildren(String url, List<String> values)
  {
    int valuesLength = values.size();
    for (ZMatchTreeNode node : children)
    {
      ZTreeTermList ret = node.match(url, values);
      if (ret != null)
      {
        return ret;
      }
      else
      {
        // if no match, remove values added by nested nodes
        int removeCnt = values.size() - valuesLength;
        for (int i = 0; i < removeCnt; i++)
        {
          values.remove(valuesLength);
        }
      }
    }
    return null;
  }


  private String matchSegments(String myUrl, List<String> values)
  {
    String rest = myUrl;
    boolean variable = false;
    for (int i = 0; i < segments.size(); i++)
    {
      if (rest.length() == 0)
      {
        return null;
      }

      ZSegment t = segments.get(i);
      if (t instanceof ZSegmentLiteral)
      {
        ZSegmentLiteral l = (ZSegmentLiteral) t;
        if (variable)
        {
          int varEnd = rest.indexOf(l.getText());
          if (varEnd <= 0)
          {
            return null;
          }
          String value = rest.substring(0, varEnd);
          if (value.indexOf('/') >= 0)
          {
            return null;
          }
          values.add(value);
          rest = rest.substring(varEnd + l.getText().length());
          variable = false;
        }
        else
        {
          if (!rest.startsWith(l.getText()))
          {
            return null;
          }
          rest = rest.substring(l.getText().length());
        }
      }
      else if (t instanceof ZSegmentVariable)
      {
        variable = true;
      }
      else if (t instanceof ZSegmentTail)
      {
        values.add(rest);
        return "";
      }
    }
    if (variable)
    {
      int idx = rest.indexOf('/');
      if (idx > 0)
      {
        String value = rest.substring(0, idx);
        values.add(value);
        rest = rest.substring(idx);
      }
      else
      {
        values.add(rest);
        rest = "";
      }
    }
    return rest;
  }


  public boolean isMatchingTheSame(ZMatchTreeNode other)
  {
    if (segments.size() != other.segments.size())
    {
      return false;
    }
    for (int i = 0; i < segments.size(); i++)
    {
      ZSegment t1 = segments.get(i);
      ZSegment t2 = other.segments.get(i);
      if (!t1.isMatchingTheSame(t2))
      {
        return false;
      }
    }
    return true;
  }


  public void sortChildren()
  {
    Collections.sort(children);
    for (ZMatchTreeNode node : children)
    {
      node.sortChildren();
    }
  }


  public int compareTo(ZMatchTreeNode n)
  {
    StringBuffer sb1 = new StringBuffer();
    int literalCnt1 = 0;
    for (ZSegment s : segments)
    {
      if (s instanceof ZSegmentLiteral)
      {
        ZSegmentLiteral sl = (ZSegmentLiteral) s;
        sb1.append(sl.getText());
        literalCnt1++;
      }
    }

    StringBuffer sb2 = new StringBuffer();
    int literalCnt2 = 0;
    for (ZSegment s : n.segments)
    {
      if (s instanceof ZSegmentLiteral)
      {
        ZSegmentLiteral sl = (ZSegmentLiteral) s;
        sb2.append(sl.getText());
        literalCnt2++;
      }
    }

    if (sb1.length() < sb2.length())
    {
      return 1;
    }
    else if (sb1.length() > sb2.length())
    {
      return -1;
    }

    if (literalCnt1 < literalCnt2)
    {
      return 1;
    }
    else if (literalCnt1 > literalCnt2)
    {
      return -1;
    }

    return sb1.toString().compareTo(sb2.toString());
  }
}
