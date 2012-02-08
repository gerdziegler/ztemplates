package org.ztemplates.message;

public class ZErrorMessage extends ZMessage
{

  public ZErrorMessage(String text)
  {
    super(ZMessage.ERROR, text);
  }
}