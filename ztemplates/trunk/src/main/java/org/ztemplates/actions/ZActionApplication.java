package org.ztemplates.actions;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.web.request.impl.ZSecureUrlDecoratorImpl;

public class ZActionApplication
{
  private static final Logger log = Logger.getLogger(ZActionApplication.class);

  private final ZIActionApplicationContext applicationContext;

  private final ZMatchTree matchTree;

  private final ZISecureUrlDecorator secureUrlDecorator;

  private Set<String> passThroughRead = new HashSet<String>();


  public ZActionApplication(ZIActionApplicationContext applicationContext,
      ZIClassRepository classRepository) throws Exception
  {
    this.applicationContext = applicationContext;
    ZMatchTreeFactory matchTreeFactory = new ZMatchTreeFactory();
    this.matchTree = matchTreeFactory.createMatchTree(classRepository);
    this.secureUrlDecorator = new ZSecureUrlDecoratorImpl();

    String matchTreeInfo = matchTree.toConsoleString();
    log.warn(matchTreeInfo);
  }


  public ZIActionApplicationContext getApplicationContext()
  {
    return applicationContext;
  }


  public ZMatchTree getMatchTree()
  {
    return matchTree;
  }


  public void setPassThroughRead(Set<String> passThroughRead)
  {
    this.passThroughRead = passThroughRead;
  }


  public Set<String> getPassThroughRead()
  {
    return passThroughRead;
  }


  public ZISecureUrlDecorator getSecureUrlDecorator()
  {
    return secureUrlDecorator;
  }
}
