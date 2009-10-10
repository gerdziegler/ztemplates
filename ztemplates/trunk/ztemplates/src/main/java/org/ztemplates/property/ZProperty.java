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
import org.ztemplates.property.validator.ZIStringValidator;
import org.ztemplates.web.ZTemplates;

public abstract class ZProperty<T>
{
  protected static final long serialVersionUID = 1L;

  public static final String MESSAGE_ID_required = "required";

  protected static final Logger log = Logger.getLogger(ZProperty.class);

  private String stringValue;

  private ZState state;

  private String description;

  private String label;

  private String name;

  private boolean required = false;

  private boolean readable = true;

  private boolean writeable = true;

  private final List<ZIStringValidator> stringValidators = new ArrayList<ZIStringValidator>();


  public abstract String format(T obj);


  public abstract T parse(String stringValue) throws Exception;


  public ZProperty()
  {
  }


  public ZProperty(String label)
  {
    this.label = label;
  }


  /**
   * always calls parse, to allow for Null-Objects
   */
  public final T getValue() throws Exception
  {
    return parse(stringValue);
  }


  /**
   * formats only not null objects
   * @return true if the value has changed
   */
  public boolean setValue(T val)
  {
    return setStringValue(val == null ? null : format(val));
  }


  @ZExposeJson
  public boolean isEmpty()
  {
    return stringValue == null;
  }


  @ZExposeJson
  public boolean isRequired()
  {
    return required;
  }


  public void setRequired(boolean required)
  {
    boolean changed = this.required != required;
    this.required = required;
    if (changed)
    {
      updateRequiredState();
    }
  }


  private void updateRequiredState()
  {
    if (required)
    {
      if(stringValue == null)
      {
        setState(new ZErrorRequired(getRequiredMessage()));
      }
    }
    else if (!required)
    {
      if(stringValue != null && state instanceof ZErrorRequired)
      {
        setState(null);
      }
      else if(stringValue == null)
      {
        setState(null);
      }
    }
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
    return "[" + getClass().getSimpleName() + " name='" + getName() + "' label='" + getLabel()
        + "' stringValue='" + stringValue + "']";
  }


  @ZExposeJson
  public String getStringValue()
  {
    return stringValue;
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


  /**
   * 
   * @param newStringValue
   * @return true if the value has changed
   */
  public boolean setStringValue(String newStringValue)
  {
    boolean changed = this.stringValue == null && newStringValue != null
        || this.stringValue != null && !this.stringValue.equals(newStringValue);

    if (changed)
    {
      if (newStringValue == null || "".equals(newStringValue))
      {
        this.stringValue = null;
      }
      else
      {
        this.stringValue = newStringValue;
      }

      revalidate();
    }

    return changed;
  }


  /**
   * Override this to validate <b>this property only</b>, without any access to some persistency layer,
   * this is <b>called every time the value or the required status changes to false</b>
   * if you need to validate <b>more than one property</b>, like for example
   * two dates that must be one before another, place that validation
   * into the validate() method of the containing ZIFormElement. Obey the
   * subsidiarity principle: place the validation into the first ZIFormElement
   * that knows <b>all</b> properties involved.
   * Called only for <b>non-empty</b> Properties. 

   * @return the Error Object or null if validation successful
   * 
   *  <p>Typical usage pattern:<p>
   <pre>
     @Override
     public ZError validate() throws Exception {
        ZError ret = super.validate();
        if (ret!=null) {
          return ret;
        }
        val = getValue(); //this is always not null, as validate is never called for empty Properties.        
        //do the validation here
        //validate ONLY this property here, no access to other properties or persistency layer
        
        return null;
     }
   </pre>
   */
  public ZError validate() throws Exception
  {
    if (stringValue == null)
    {
      throw new Exception("validate should never be called with empty value: " + this);
    }

    for (ZIStringValidator val : stringValidators)
    {
      ZError err = val.validate(stringValue);
      if (err != null)
      {
        return err;
      }
    }
    return null;
  }


  /**
   * 
   */
  public void revalidate()
  {
    if (stringValue == null)
    {
      if (required)
      {
        setState(new ZErrorRequired(getRequiredMessage()));
      }
      else
      {
        setState(null);
      }
    }
    else
    {
      try
      {
        setState(validate());
      }
      catch (Exception e)
      {
        setState(new ZError(e));
      }
    }
  }


  @ZExposeJson
  public String getRequiredMessage()
  {
    try
    {
      return ZTemplates.getMessageService().getMessage(ZProperty.class.getName(),
          MESSAGE_ID_required);
    }
    catch (Exception e)
    {
      log.error("getRequiredMessage", e);
      return e.getMessage();
    }
  }


  @ZExposeJson
  public final ZState getState()
  {
    return state;
  }


  public void setState(ZState state)
  {
    this.state = state;
  }


  @ZExposeJson
  public boolean isError()
  {
    return state instanceof ZError;
  }


  @ZExposeJson
  public String getDescription()
  {
    return description;
  }


  public void setDescription(String description)
  {
    this.description = description;
  }


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


  //  @ZExposeJson
  //  public boolean isAjaxReloadState()
  //  {
  //    return ajaxReloadState;
  //  }
  //
  //
  //  /**
  //   * To enable ajax calls, set this to true when ajax call should
  //   * be made if this property changes, defaults to <b>false</b>
  //   */
  //  public void setAjaxReloadState(boolean ajaxReloadStateOnChange)
  //  {
  //    this.ajaxReloadState = ajaxReloadStateOnChange;
  //  }

  /**
   * human readable String identifier
   * 
   * @return
   */
  @ZExposeJson
  public String getLabel()
  {
    return label;
  }


  /**
   * human readable String identifier
   * 
   * @return
   */
  public void setLabel(String label)
  {
    this.label = label;
  }


  @ZExposeJson
  public List<ZIStringValidator> getStringValidators()
  {
    return stringValidators;
  }
}
