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
 * 09.02.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.actions.util;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

public class ZEncrypter
{
  private static final Logger log = Logger.getLogger(ZEncrypter.class);

  Cipher ecipher;

  Cipher dcipher;

  MessageDigest md;

  byte[] salt;

  static byte[] defaultSalt =
  {
      (byte) 0xAF,
      (byte) 0x1F,
      (byte) 0x38,
      (byte) 0x76,
      (byte) 0x12,
      (byte) 0x36,
      (byte) 0xD3,
      (byte) 0x11
  };

  int iterationCount = 19;


  public ZEncrypter(byte[] salt, String passPhrase)
  {
    if (salt == null)
    {
      salt = defaultSalt;
    }
    this.salt = salt;
    try
    {
      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
      SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
      ecipher = Cipher.getInstance(key.getAlgorithm());
      dcipher = Cipher.getInstance(key.getAlgorithm());
      md = MessageDigest.getInstance("MD5");

      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

      ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
      dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    }
    catch (Exception e)
    {
      log.error("", e);
      throw new RuntimeException(e);
    }
  }


  public ZEncrypter(String passPhrase)
  {
    this(defaultSalt, passPhrase);
  }


  public String encrypt(String str) throws Exception
  {
    try
    {
      byte[] utf8 = str.getBytes("UTF8");

      byte[] digest = md.digest(utf8);

      assert digest.length == 16 : "" + digest.length;

      byte[] all = new byte[utf8.length + digest.length];

      System.arraycopy(utf8, 0, all, 0, utf8.length);
      System.arraycopy(digest, 0, all, utf8.length, digest.length);

      byte[] enc = ecipher.doFinal(all);

      // return new sun.misc.BASE64Encoder().encode(enc);
      String ret = ZBase64Util.encodeBytes(enc, ZBase64Util.DONT_BREAK_LINES);
      return ret;
    }
    catch (Exception e)
    {
      throw new Exception(e.getMessage(), e);
    }
  }


  public String decrypt(String str) throws Exception
  {
    try
    {
      byte[] dec = ZBase64Util.decode(str);

      byte[] all = dcipher.doFinal(dec);

      byte[] utf8 = new byte[all.length - 16];
      byte[] digest = new byte[16];

      System.arraycopy(all, 0, utf8, 0, utf8.length);
      System.arraycopy(all, utf8.length, digest, 0, digest.length);

      byte[] digest1 = md.digest(utf8);

      if (!Arrays.equals(digest, digest1))
      {
        throw new Exception("MD5 hash does not match");
      }

      String ret = new String(utf8, "UTF8");
      return ret;
    }
    catch (Exception e)
    {
      throw new Exception(e.getMessage(), e);
    }
  }
}
