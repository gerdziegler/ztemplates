package org.ztemplates.actions.urlhandler.tree.match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZUrlCollisionException;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeLiteral;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeSlash;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTail;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTerm;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeVariable;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZMatchTree implements Serializable
{
  static Logger log = Logger.getLogger(ZMatchTree.class);

  private final List<ZMatchTreeNode> roots = new ArrayList<ZMatchTreeNode>();


  public void sort()
  {
    Collections.sort(roots);
    for (ZMatchTreeNode node : roots)
    {
      node.sortChildren();
    }
  }


  public void add(ZTreeTermList crt) throws Exception
  {
    List<ZTreeTerm> terms = crt.getTerms();
    List<ZMatchTreeNode> nodes = createNodes(terms);
    merge(roots, nodes, 0);
  }


  /**
   * create new nodes when found slash, terms must start with slash
   * 
   * @param terms
   * @return
   */
  private List<ZMatchTreeNode> createNodes(List<ZTreeTerm> terms)
  {
    List<ZMatchTreeNode> ret = new ArrayList<ZMatchTreeNode>();
    ZMatchTreeNode crt = null;
    for (ZTreeTerm term : terms)
    {
      if (term instanceof ZTreeSlash)
      {
        ZMatchTreeNode newNode = new ZMatchTreeNode();
        ret.add(newNode);
        crt = newNode;
      }
      else if (term instanceof ZTreeLiteral)
      {
        ZTreeLiteral tl = (ZTreeLiteral) term;
        crt.getSegments().add(new ZSegmentLiteral(tl.getText()));
      }
      else if (term instanceof ZTreeVariable)
      {
        ZTreeVariable tl = (ZTreeVariable) term;
        crt.getSegments().add(new ZSegmentVariable(tl.getName()));
      }
      else if (term instanceof ZTreeTail)
      {
        ZTreeTail tl = (ZTreeTail) term;
        crt.getSegments().add(new ZSegmentTail(tl.getName()));
      }
    }
    crt.setHandler(new ZTreeTermList(terms));
    return ret;
  }


  /**
   * merge nodes into r
   * 
   * @param root2
   * @param nodes
   */
  private void merge(List<ZMatchTreeNode> parentNodes, List<ZMatchTreeNode> nodes, int start)
      throws Exception
  {
    if (start >= nodes.size())
    {
      return;
    }
    ZMatchTreeNode crtNode = nodes.get(start);
    boolean added = false;
    for (ZMatchTreeNode parentNode : parentNodes)
    {
      if (crtNode.isMatchingTheSame(parentNode))
      {
        if (crtNode.getHandler() != null)
        {
          if (parentNode.getHandler() == null)
          {
            parentNode.setHandler(crtNode.getHandler());
          }
          else
          {
            throw new ZUrlCollisionException("url collision: \n"
                + parentNode.getHandler().printToConsole() + "\n"
                + crtNode.getHandler().printToConsole());
          }
        }
        crtNode = parentNode;
        added = true;
        break;
      }
    }
    if (!added)
    {
      parentNodes.add(crtNode);
    }
    merge(crtNode.getChildren(), nodes, start + 1);
  }


  @Override
  public String toString()
  {
    return toConsoleString();
    // StringBuffer sb = new StringBuffer();
    // toXml(sb, 0);
    // return sb.toString();
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indentTree(sb, depth);
    sb.append("<parse-tree>");
    for (ZMatchTreeNode node : roots)
    {
      node.toXml(sb, depth + 1);
    }
    ZFormatUtil.indentTree(sb, depth);
    sb.append("</parse-tree>");
  }


  public String toConsoleString()
  {
    // first, compute max width of tree
    int fill = Integer.MIN_VALUE;
    StringBuffer sb = new StringBuffer();
    for (ZMatchTreeNode node : roots)
    {
      fill = Math.max(fill, printToConsole(sb, node, "", 0));
    }
    fill += 4;
    // then print tree
    sb = new StringBuffer();
    for (ZMatchTreeNode node : roots)
    {
      printToConsole(sb, node, "", fill);
    }
    return sb.toString();
  }


  private int printToConsole(StringBuffer ret, ZMatchTreeNode node, String prefix, int fill)
  {
    StringBuffer sb = new StringBuffer();
    sb.append('\n');
    if (prefix.length() > 0)
    {
      if (prefix.charAt(prefix.length() - 1) == '|')
      {
        sb.append(prefix);
        sb.append("--  /");
      }
      else
      {
        sb.append(prefix.substring(0, prefix.length() - 1));
        sb.append("|--  /");
      }
    }
    else
    {
      sb.append("/");
    }
    for (ZSegment t : node.getSegments())
    {
      String s = t.toDefinition();
      sb.append(s);
    }

    int len = sb.length();

    if (node.getHandler() != null)
    {
      sb.append(" ");
      ZFormatUtil.fillRight(sb, fill, '.');
      String s = node.getHandler().printToConsole();
      sb.append(s);
    }

    ret.append(sb);

    for (int i = 0; i < node.getChildren().size(); i++)
    {
      ZMatchTreeNode child = node.getChildren().get(i);
      String crtPrefix;
      if (i == node.getChildren().size() - 1)
      {
        crtPrefix = prefix + "    ";
      }
      else
      {
        crtPrefix = prefix + "   |";
      }
      len = Math.max(len, printToConsole(ret, child, crtPrefix, fill));
    }

    return len;
  }


  public List<ZMatchTreeNode> getRoots()
  {
    return roots;
  }


  public ZMatchedUrl match(String url)
  {
    for (ZMatchTreeNode node : roots)
    {
      List<String> values = new ArrayList<String>();
      ZTreeTermList terms = node.match(url, values);
      if (terms != null)
      {
        return new ZMatchedUrl(terms, values);
      }
    }
    return null;
  }
}
