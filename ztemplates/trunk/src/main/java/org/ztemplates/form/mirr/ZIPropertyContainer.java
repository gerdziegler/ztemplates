package org.ztemplates.form.mirr;

import java.util.List;

import org.ztemplates.property.ZProperty;

public interface ZIPropertyContainer extends ZIFormMirror
{
  public List<ZProperty> getProperties();
}
