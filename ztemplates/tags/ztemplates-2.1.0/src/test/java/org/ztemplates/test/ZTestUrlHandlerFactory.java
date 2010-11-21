/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */
package org.ztemplates.test;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassPathItem;
import org.zclasspath.ZIClassRepository;
import org.zclasspath.ZJavaClassPath;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.tree.ZTreeUrlHandler;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;

/**
 * 
 * 
 * 
 */
public class ZTestUrlHandlerFactory
{
  private static Logger log = Logger.getLogger(ZTestUrlHandlerFactory.class);

  public static final ZISecurityProvider defaultSecurityService = new ZISecurityProvider()
  {
    public boolean isUserInRole(String role)
    {
      return true;
    }


    public String getUserName()
    {
      return "defaultUser";
    }

  };

  public static final ZISecureUrlDecorator defaultSecureUrlDecorator = new ZISecureUrlDecorator()
  {
    public String addSecurityToUrl(String url, Set<String> roles)
    {
      return url;
    }


    public String removeSecurityFromUrl(String url)
    {
      return url;
    }
  };


  public static ZIUrlHandler create(String pojoPackage, ZISecurityProvider security) throws Exception
  {
    return ZTestUrlHandlerFactory.create(pojoPackage, security, "utf-8");
  }


  public static ZIUrlHandler create(String pojoPackage, ZISecurityProvider security, String encoding) throws Exception
  {
    List<ZIClassPathItem> items = ZJavaClassPath.getItems();
    ZIClassRepository classRepository = ZClassRepository.create(items, pojoPackage);
    ZTestApplicationContext applicationContext = new ZTestApplicationContext(classRepository);
    ZMatchTree matchTree = new ZMatchTree(classRepository);
    ZIUrlHandler ret = new ZTreeUrlHandler(matchTree, security, defaultSecureUrlDecorator, applicationContext, encoding);
    return ret;
  }
}
