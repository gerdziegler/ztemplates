package org.ztemplates.actions;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.commons.ZIObjectFactory;

public class ZActionApplication
{
  private static final Logger log = Logger.getLogger(ZActionApplication.class);

  private final ZIActionApplicationContext applicationContext;

  private final ZMatchTree matchTree;

  private Set<String> passThroughRead = new HashSet<String>();

  private ZIObjectFactory objectFactory;


  public ZActionApplication(ZIActionApplicationContext applicationContext,
      ZIClassRepository classRepository,
      ZIObjectFactory objectFactory) throws Exception
  {
    this.applicationContext = applicationContext;
    this.objectFactory = objectFactory;
    ZMatchTreeFactory matchTreeFactory = new ZMatchTreeFactory();
    this.matchTree = matchTreeFactory.createMatchTree(classRepository);

    String matchTreeInfo = matchTree.toConsoleString();
    log.info(matchTreeInfo);
  }


  public ZIObjectFactory getObjectFactory()
  {
    return objectFactory;
  }


  public void setObjectFactory(ZIObjectFactory objectFactory)
  {
    this.objectFactory = objectFactory;
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
}