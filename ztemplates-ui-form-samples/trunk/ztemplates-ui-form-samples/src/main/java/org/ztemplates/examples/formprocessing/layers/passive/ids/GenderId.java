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
 * 29.01.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.examples.formprocessing.layers.passive.ids;

public class GenderId extends Id<String>
{
  public static final GenderId MALE = new GenderId("m");

  public static final GenderId FEMALE = new GenderId("w");


  private GenderId(String id)
  {
    super(id);
  }


  public static GenderId create(String id)
  {
    return id == null ? null : new GenderId(id);
  }
}
