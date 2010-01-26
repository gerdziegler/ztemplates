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
 * 07.01.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.web.request.impl;

import org.ztemplates.actions.util.ZEncrypter;
import org.ztemplates.web.ZIEncryptionService;

public class ZEncryptionServiceImpl implements ZIEncryptionService
{
  private final ZEncrypter encrypter;


  public ZEncryptionServiceImpl(String password, byte[] salt)
  {
    encrypter = new ZEncrypter(salt, password);
  }


  public ZEncryptionServiceImpl(String password, String saltHex)
  {
    if (password == null)
    {
      encrypter = null;
    }
    else
    {
      byte[] salt = null;
      if (saltHex != null)
      {
        saltHex = saltHex.trim().toLowerCase();
        if (saltHex.length() != 16)
        {
          throw new RuntimeException("'encryptSalt' in must have length 16 and contain only the hex characters 0-1 and a-f");
        }

        salt = new byte[saltHex.length() / 2];
        for (int i = 0; i < salt.length; i++)
        {
          salt[i] = (byte) Integer.parseInt(saltHex.substring(2 * i, 2 * i + 2), 16);
        }
      }

      encrypter = new ZEncrypter(salt, password);
    }
  }


  public String decrypt(String s) throws Exception
  {
    if (encrypter == null)
    {
      return s;
    }

    String ret = encrypter.decrypt(s);
    return ret;
  }


  public String encrypt(String s) throws Exception
  {
    if (encrypter == null)
    {
      return s;
    }

    String ret = encrypter.encrypt(s);
    return ret;
  }

}
