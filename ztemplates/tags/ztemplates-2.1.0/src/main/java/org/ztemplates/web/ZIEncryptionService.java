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
package org.ztemplates.web;

public interface ZIEncryptionService extends ZIService
{
  /**
   * encrypts the string and its md5hash
   * 
   * @param s
   * @return
   */
  public String encrypt(String s) throws Exception;


  /**
   * encrypts the string and its md5hash
   * 
   * @param s
   * @return
   */
  public String decrypt(String s) throws Exception;
}
