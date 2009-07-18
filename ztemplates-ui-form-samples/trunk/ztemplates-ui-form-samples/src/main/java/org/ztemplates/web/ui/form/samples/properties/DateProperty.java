/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.web.ui.form.samples.properties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.ztemplates.property.ZDateProperty;

public class DateProperty extends ZDateProperty
{
  public DateProperty(String label, DateFormat df)
  {
    super(label, df);
  }


  public DateProperty(String label, String format)
  {
    super(label, new SimpleDateFormat(format));
  }


  public DateProperty(String label)
  {
    this(label, new SimpleDateFormat("dd.MM.yyyy"));
  }
}
