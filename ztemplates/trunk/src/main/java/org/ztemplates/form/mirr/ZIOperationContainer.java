package org.ztemplates.form.mirr;

import java.util.List;

import org.ztemplates.property.ZOperation;

public interface ZIOperationContainer extends ZIFormMirror
{
  public List<ZOperation> getOperations();

}
