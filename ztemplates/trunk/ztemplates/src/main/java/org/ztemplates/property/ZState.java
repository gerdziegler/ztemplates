/*
 * Copyright 2006 Gerd Ziegler (www.gerdziegler.de)
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
 *
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.io.Serializable;

import org.ztemplates.json.ZExposeJson;

public class ZState implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String text;

  private String type;


  protected ZState()
  {
  }


  public ZState(String type, String text)
  {
    this.type = type;
    this.text = text;
  }


  @ZExposeJson
  public final String getText()
  {
    return text;
  }


  public void setText(String text)
  {
    this.text = text;
  }


  @ZExposeJson
  public final String getType()
  {
    return type;
  }


  public void setType(String type)
  {
    this.type = type;
  }


  public String toString()
  {
    return text;
  }
}
