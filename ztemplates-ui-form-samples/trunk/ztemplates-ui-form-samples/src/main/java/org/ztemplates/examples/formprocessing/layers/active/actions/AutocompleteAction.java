/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * @author www.gerdziegler.de
 */
package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ztemplates.actions.ZIAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZIntProperty;
import org.ztemplates.web.ZTemplates;

@ZMatch(value = "/autocomplete", parameters = {"q", "limit"})
public class AutocompleteAction implements ZIAction
{
  static final Logger log = Logger.getLogger(AutocompleteAction.class);

  private String q;

  private final ZIntProperty limit = new ZIntProperty();


  public ZIntProperty getLimit()
  {
    return limit;
  }


  public static String createUrl() throws Exception
  {
    AutocompleteAction act = new AutocompleteAction();
    return ZTemplates.getActionService().createUrl(act);
  }


//  public static JSONObject getSchema()
//  {
//    JSONArray fields = new JSONArray();
//    fields.put("name");
//
//    JSONObject ret = new JSONObject();
//    try
//    {
//      JSONObject metaFields = new JSONObject();
//      metaFields.put("totalRecords", "totalRecords");
//      ret.put("resultsList", "results");
//      ret.put("fields", fields);
//      ret.put("metaFields", metaFields);
//    }
//    catch (JSONException e)
//    {
//      log.error("", e);
//    }
//    return ret;
//  }


  public void after() throws Exception
  {
    StringBuffer sb = new StringBuffer();
    int lim = limit.isEmpty() ? 10 : limit.getValue().intValue();
    for (int i = 0; i < lim; i++)
    {
      sb.append(q + i);
      sb.append('\n');
    }

    //    JSONArray arr = new JSONArray();
    //    JSONObject crt = new JSONObject();
    //    crt.put("name", q);
    //    arr.put(crt);
    //    for (int i = 0; i < 10; i++)
    //    {
    //      crt = new JSONObject();
    //      crt.put("name", q + i);
    //      arr.put(crt);
    //    }
    //
    //    
    //    
    //    JSONObject ret = new JSONObject();
    //    ret.put("results", arr);
    //    ret.put("totalRecords", "1000");
    String s = sb.toString();
    log.info(s);
    ZTemplates.getServletService().render(s);
  }


  public String getQ()
  {
    return q;
  }


  public void setQ(String query)
  {
    this.q = query;
  }

}
