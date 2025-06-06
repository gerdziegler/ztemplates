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

package org.ztemplates.web.jsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class ZServletOutputStreamWrapper extends ServletOutputStream
{
  private ByteArrayOutputStream buffer = new ByteArrayOutputStream();


  @Override
  public void write(int b) throws IOException
  {
    buffer.write(b);
  }


  @Override
  public void write(byte[] b) throws IOException
  {
    buffer.write(b);
  }


  @Override
  public void write(byte[] b, int off, int len) throws IOException
  {
    buffer.write(b, off, len);
  }


  public ByteArrayOutputStream getBuffer()
  {
    return buffer;
  }
}
