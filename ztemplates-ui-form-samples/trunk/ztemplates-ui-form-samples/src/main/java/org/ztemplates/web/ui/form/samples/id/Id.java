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

import java.io.Serializable;

import org.ztemplates.json.ZExposeJson;

public abstract class Id<T> implements Serializable
{
  private final T id;


  public Id(T id)
  {
    this.id = id;
  }


  @Override
  public boolean equals(Object other)
  {
    if (other instanceof Id)
    {
      Id o = (Id) other;
      return o.getRaw().equals(getRaw());
    }
    else
    {
      return false;
    }
  }


  @Override
  public int hashCode()
  {
    return getRaw().hashCode();
  }


  @ZExposeJson
  public T getRaw()
  {
    return id;
  }


  public String toString()
  {
    return getRaw().toString();
  }
}
