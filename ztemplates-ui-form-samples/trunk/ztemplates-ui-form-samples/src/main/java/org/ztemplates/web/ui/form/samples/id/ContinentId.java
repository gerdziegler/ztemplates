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
package org.ztemplates.web.ui.form.samples.id;

public class ContinentId extends Id<String>
{
  public static final ContinentId NONE = new ContinentId("");
  public static final ContinentId EUROPE = new ContinentId("EUROPE");
  public static final ContinentId AMERICA = new ContinentId("AMERICA");
  public static final ContinentId ASIA = new ContinentId("ASIA");
  public static final ContinentId AUSTRALIA = new ContinentId("AUSTRALIA");
  public static final ContinentId AFRICA = new ContinentId("AFRICA");


  private ContinentId(String id)
  {
    super(id);
  }


  public static ContinentId create(String id)
  {
    return id == null ? null : new ContinentId(id);
  }
}
