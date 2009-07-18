/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.form;

import java.io.Serializable;

import org.ztemplates.actions.util.ZBase64Util;

public class ZSerializeUtil
{
  public static String serialize(Serializable obj) throws Exception
  {
    String base64 = ZBase64Util.encodeObject(obj, ZBase64Util.GZIP | ZBase64Util.DONT_BREAK_LINES);
    //    String ret = URLEncoder.encode(base64, "ISO-8859-1");
    return base64;
  }


  public static Object deserialize(String s) throws Exception
  {
    //    String decoded = URLDecoder.decode(s, "ISO-8859-1");
    Object ret = ZBase64Util.decodeToObject(s);
    return ret;
  }
}
