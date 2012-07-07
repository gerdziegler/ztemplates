package org.ztemplates.test.actions.urlhandler.namedform;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.form.mirr.ZFormMirrorFactory;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;

public class NamedFormTest extends TestCase
{
  static Logger log = Logger.getLogger(NamedFormTest.class);

  ZIUrlHandler up;

  ZFormMirrorFactory formMirrorFactory = new ZFormMirrorFactory();


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(NamedFormTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testProcess() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("myForm7_prop", new String[]
    {
        "katze",
    });
    param.put("id", new String[]
    {
        "7",
    });
    NamedFormAction obj = (NamedFormAction) up.process("/namedFormAction", param);
    assertEquals(new Long(7), obj.id);
    assertEquals("katze", obj.form.prop.getStringValue());
  }
}
