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
 * www.gerdziegler.de
 */
package org.ztemplates.property;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.validation.ZIValidator;

public abstract class ZProperty<T>
{
  protected static final long serialVersionUID = 1L;

  public static final Logger log = Logger.getLogger(ZProperty.class);

  private static final String[] EMPTY = new String[0];

  private String[] stringValues = EMPTY;

  private final List<ZIValidator> validators = new ArrayList<ZIValidator>();

  private String name;

  private boolean required = false;

  private boolean readable = true;

  private boolean writeable = true;


  public abstract String format(T obj);


  public abstract T parse(String stringValue) throws Exception;


  public ZProperty()
  {
  }


  /**
   * parses only not null objects
   * 
   * @return true if the value has changed
   */
  public final T getValue() throws Exception
  {
    return isEmpty() ? null : parse(stringValues[0]);
  }


  /**
   * using List here because generic arrays cannot be created without passing
   * parameters.
   * 
   * @return
   * @throws Exception
   */
  public final List<T> getValues() throws Exception
  {
    List<T> ret = new ArrayList<T>(stringValues.length);
    for (int i = 0; i < stringValues.length; i++)
    {
      ret.add(parse(stringValues[i]));
    }
    return ret;
  }


  /**
   * formats only not null objects
   * 
   * @return true if the value has changed
   */
  public void setValue(T val)
  {
    setStringValues(val == null ? null : new String[]
    {
        format(val)
    });
  }


  @ZExposeJson
  public boolean isEmpty()
  {
    return stringValues.length == 0 || stringValues.length == 1 && stringValues[0].length() == 0;
  }


  @ZExposeJson
  public boolean isWriteable()
  {
    return writeable;
  }


  public void setWriteable(boolean writeable)
  {
    this.writeable = writeable;
  }


  public String toString()
  {
    return "[" + getClass().getSimpleName() + " name='" + getName() + "' stringValues='" + stringValues + "']";
  }


  @ZExposeJson
  public String getStringValue()
  {
    return isEmpty() ? null : stringValues[0];
  }


  @ZExposeJson
  public String[] getStringValues()
  {
    return stringValues;
  }


  @ZExposeJson
  public boolean isReadable()
  {
    return readable;
  }


  public void setReadable(boolean readable)
  {
    this.readable = readable;
  }


  @ZExposeJson
  public boolean isRequired()
  {
    return required;
  }


  public void setRequired(boolean required)
  {
    this.required = required;
  }


  /**
   * 
   * @param newStringValue
   * @return true if the value has changed
   */
  public void setStringValues(String[] newStringValues)
  {
    String[] oldStringValues = this.stringValues;
    if (newStringValues == null)
    {
      newStringValues = EMPTY;
    }
    this.stringValues = newStringValues;
  }


  /**
   * Override this to validate <b>this property only</b>, without any access to
   * some persistency layer, this is <b>called every time the value or the
   * required status changes to false</b> if you need to validate <b>more than
   * one property</b>, like for example two dates that must be one before
   * another, place that validation into the validate() method of the containing
   * ZIFormElement. Obey the subsidiarity principle: place the validation into
   * the first ZIFormElement that knows <b>all</b> properties involved. Called
   * only for <b>non-empty</b> Properties.
   * 
   * @return the Error Object or null if validation successful
   * 
   *         <p>
   *         Typical usage pattern:
   *         <p>
   * 
   *         <pre>
   * &#064;Override
   * public ZError validate() throws Exception
   * {
   *   ZError ret = super.validate();
   *   if (ret != null)
   *   {
   *     return ret;
   *   }
   *   val = getValue(); // this is always not null, as validate is never called for
   *                     // empty Properties.
   *   // do the validation here
   *   // validate ONLY this property here, no access to other properties or
   *   // persistency layer
   * 
   *   return null;
   * }
   * </pre>
   */
  // public ZError validate() throws Exception
  // {
  // if (isEmpty())
  // {
  // throw new Exception("validate should never be called with empty value: " +
  // this);
  // }
  //
  // String stringValue = stringValues[0];
  // for (ZIValidator val : stringValidators)
  // {
  // ZError err = val.validate(stringValue);
  // if (err != null)
  // {
  // return err;
  // }
  // }
  // return null;
  // }

  /**
   * internal String identifier
   * 
   * @return
   */
  @ZExposeJson
  public String getName()
  {
    return name;
  }


  /**
   * internal String identifier
   * 
   * @return
   */
  public void setName(String name)
  {
    this.name = name;
  }


  @ZExposeJson
  public List<ZIValidator> getValidators()
  {
    return validators;
  }
}
