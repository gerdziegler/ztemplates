/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.examples.formprocessing.layers.active.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.ztemplates.examples.formprocessing.layers.active.services.FormDataRespDTO;
import org.ztemplates.examples.formprocessing.layers.active.services.IFormTutorialService;
import org.ztemplates.examples.formprocessing.layers.passive.ids.ContinentId;
import org.ztemplates.examples.formprocessing.layers.passive.ids.CountryId;

/**
 * dummy implementation of the service, this could be a statless session bean 
 * or some other transactional service
 * @author www.gerdziegler.de
 */
public class TutorialServiceImpl implements IFormTutorialService
{
  public List<CountryId> getCountries(ContinentId con) throws Exception
  {
    List<CountryId> ret = new ArrayList<CountryId>();
    if (ContinentId.EUROPE.equals(con))
    {
      ret.add(CountryId.create(Locale.GERMANY));
      ret.add(CountryId.create(Locale.FRANCE));
      ret.add(CountryId.create(Locale.ITALY));
      ret.add(CountryId.create(Locale.UK));
      ret.add(CountryId.create("RU"));
    }
    else if (ContinentId.AMERICA.equals(con))
    {
      ret.add(CountryId.create(Locale.US));
      ret.add(CountryId.create("CA"));
      ret.add(CountryId.create("BR"));
    }
    else if (ContinentId.ASIA.equals(con))
    {
      ret.add(CountryId.create(Locale.CHINA));
      ret.add(CountryId.create(Locale.JAPAN));
      ret.add(CountryId.create("IN"));
      ret.add(CountryId.create(Locale.KOREA));
      ret.add(CountryId.create(Locale.TAIWAN));
      ret.add(CountryId.create("RU"));
    }
    else if (ContinentId.AFRICA.equals(con))
    {
      ret.add(CountryId.create("ZA"));
      ret.add(CountryId.create("EG"));
      ret.add(CountryId.create("CD"));
      ret.add(CountryId.create("CG"));
    }
    else if (ContinentId.AUSTRALIA.equals(con))
    {
      ret.add(CountryId.create("AU"));
      ret.add(CountryId.create("NZ"));
    }
    else
    {
      throw new Exception("unknown continent: " + con);
    }
    return ret;
  }

  @Override
  public FormDataRespDTO getInitialFormData() throws Exception
  {
    return new FormDataRespDTO();
  }

  @Override
  public List<ContinentId> getContinents() throws Exception
  {
    List<ContinentId> ret = new ArrayList<ContinentId>();
    ret.add(ContinentId.NONE);
    ret.add(ContinentId.EUROPE);
    ret.add(ContinentId.AMERICA);
    ret.add(ContinentId.ASIA);
    ret.add(ContinentId.AUSTRALIA);
    ret.add(ContinentId.AFRICA);
    return ret;
  }
}
