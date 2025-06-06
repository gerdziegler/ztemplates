/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. 11.03.2008 @author
 * www.gerdziegler.de
 */
package org.ztemplates.render.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZCopyUtil
{
  final static int CACHESIZE = 2048;


  public static void copy(InputStream in, OutputStream out) throws IOException
  {
    InputStream myin = new BufferedInputStream(in, CACHESIZE);
    OutputStream myout = new BufferedOutputStream(out, CACHESIZE);
    try
    {
      byte[] buff = new byte[CACHESIZE];
      int c = myin.read(buff);
      while (c > 0)
      {
        myout.write(buff, 0, c);
        c = myin.read(buff);
      }
    }
    finally
    {
      myout.flush();
    }
  }
}
