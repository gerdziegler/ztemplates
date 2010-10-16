package org.ztemplates.test.scriptrepo.component;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.ztemplates.web.script.experimental.repo.impl.ZDefaultVersionComparator;

public class DefaultVersionComparatorTest extends TestCase
{
  public void testNum1()
  {
    ZDefaultVersionComparator comp = new ZDefaultVersionComparator();
    Assert.assertEquals(-1, comp.compare("0.0.0", "0.0.1"));
    Assert.assertEquals(-1, comp.compare("0.0.0", "0.1.0"));
    Assert.assertEquals(-1, comp.compare("0.0.0", "1.0.0"));
    Assert.assertEquals(1, comp.compare("0.0.1", "0.0.0"));
    Assert.assertEquals(1, comp.compare("0.1.0", "0.0.0"));
    Assert.assertEquals(1, comp.compare("1.0.0", "0.0.0"));
    Assert.assertEquals(-1, comp.compare("0.0.1", "0.0.2"));
    Assert.assertEquals(1, comp.compare("0.1.0", "0.0.1"));
    Assert.assertEquals(1, comp.compare("1.0.0", "0.1.1"));
    Assert.assertEquals(0, comp.compare("1.0.0", "1.0.0"));
    Assert.assertEquals(-1, comp.compare("1.0", "1.0.2"));
    Assert.assertEquals(1, comp.compare("1.0.1", "1.0"));
    Assert.assertEquals(1, comp.compare("1.0.0", "1.0"));
    Assert.assertEquals(-1, comp.compare("1", "1.0"));
    Assert.assertEquals(-1, comp.compare("1", "1.0.1"));
    Assert.assertEquals(-1, comp.compare("1.0.3.8", "1.0.3.8.1.4.5"));
    Assert.assertEquals(1, comp.compare("1.1", "1.0.3.8.1.4.5"));
    Assert.assertEquals(0, comp.compare("1.0.3.8.1.4.5", "1.0.3.8.1.4.5"));
  }
}
