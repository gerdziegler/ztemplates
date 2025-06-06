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
 * 03.10.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.ztemplates.form.ZFormElementMirrorTest;
import org.ztemplates.form.ZFormValuesTest;
import org.ztemplates.form.ZSerializeUtilTest;
import org.ztemplates.json.ZJsonTest;
import org.ztemplates.test.actions.classes.test1.ClassesTest;
import org.ztemplates.test.actions.expression.test1.ExpressionTest;
import org.ztemplates.test.actions.service.ActionServiceTest;
import org.ztemplates.test.actions.urlhandler.callbacks.test1.CallbacksTest;
import org.ztemplates.test.actions.urlhandler.callbacks.test2.ProcessorTest;
import org.ztemplates.test.actions.urlhandler.constructor.ConstructorTest;
import org.ztemplates.test.actions.urlhandler.form.FormTest;
import org.ztemplates.test.actions.urlhandler.form.FormTestOpCallback;
import org.ztemplates.test.actions.urlhandler.i18n.I18nTest;
import org.ztemplates.test.actions.urlhandler.match.collision.TreeCollisionTest;
import org.ztemplates.test.actions.urlhandler.match.collision2.TreeCollision2Test;
import org.ztemplates.test.actions.urlhandler.match.collision3.Collision3Test;
import org.ztemplates.test.actions.urlhandler.match.handler.ParseTreeTest;
import org.ztemplates.test.actions.urlhandler.match.handler.tail.TailParseTreeTest;
import org.ztemplates.test.actions.urlhandler.match.matchtree.MatchTreeTest;
import org.ztemplates.test.actions.urlhandler.match.test1.TreeTest;
import org.ztemplates.test.actions.urlhandler.match.test2.TreeTest2;
import org.ztemplates.test.actions.urlhandler.namedform.NamedFormTest;
import org.ztemplates.test.actions.urlhandler.prop.plain.PropTest;
import org.ztemplates.test.actions.urlhandler.repository.nested.NestedHandlerTest;
import org.ztemplates.test.actions.urlhandler.repository.simple.UrlHandlerRepositoryTest;
import org.ztemplates.test.actions.urlhandler.repository.simple.ZUrlFactoryTest;
import org.ztemplates.test.actions.urlhandler.repository.trailingslash.TrailingSlashTest;
import org.ztemplates.test.actions.urlhandler.secure.nested.NestedSecureTest;
import org.ztemplates.test.actions.urlhandler.secure.simple.SimpleSecureTest;
import org.ztemplates.test.actions.urlhandler.tree.collision.test1.UrlHandlerRepositoryCollisionTest;
import org.ztemplates.test.actions.urlhandler.tree.parameters.ParameterTest;
import org.ztemplates.test.property.PropertiesTest;
import org.ztemplates.test.property.PropertyTest;
import org.ztemplates.test.reflection.ReflectionUtilTest;
import org.ztemplates.test.render.css.CssTest;
import org.ztemplates.test.render.impl.ReplaceUtilTest;
import org.ztemplates.test.render.impl.opt.ZRenderClassRepositoryTest;
import org.ztemplates.test.render.maps.MapTestPojoTest;
import org.ztemplates.test.render.methodrepo.ZExposedMethodRepositoryTest;
import org.ztemplates.test.render.script.basic.ScriptTest;
import org.ztemplates.test.render.script.basic2.BasicScript2Test;
import org.ztemplates.test.render.script.cycle.ScriptCycleTest;
import org.ztemplates.test.render.script.duplicates.VariableNamesScriptTest;
import org.ztemplates.test.render.script.js.CachingJavaScriptProcessorTest;
import org.ztemplates.test.render.script.var.ScriptVarTest;

public class AllTests extends TestSuite
{
  // public AllTests()
  // {
  // addTestSuite(ExpressionTest.class);
  // addTestSuite(ClassesTest.class);
  // addTestSuite(RegexTest.class);
  // addTestSuite(RegexNestedTest.class);
  // addTestSuite(SamplerNestedTest.class);
  // addTestSuite(ProcessorTest.class);
  //
  // addTestSuite(ZUrlTest.class);
  // addTestSuite(UrlHandlerRepositoryTest.class);
  // addTestSuite(UrlHandlerRepositoryCollisionTest.class);
  // addTestSuite(NestedHandlerTest.class);
  // addTestSuite(ParameterTest.class);
  //
  // addTestSuite(TrailingSlashTest.class);
  // }

  public static Test suite()
  {
    TestSuite suite = new TestSuite("Tests for org.ztemplates.actions");

    // $JUnit-BEGIN$

    suite.addTestSuite(ExpressionTest.class);
    suite.addTestSuite(ClassesTest.class);
    // suite.addTestSuite(RegexSimpleTest.class);
    // suite.addTestSuite(RegexNestedTest.class);
    // suite.addTestSuite(RegexSamplerTest.class);
    suite.addTestSuite(ProcessorTest.class);
    suite.addTestSuite(ZUrlFactoryTest.class);
    suite.addTestSuite(UrlHandlerRepositoryTest.class);
    suite.addTestSuite(UrlHandlerRepositoryCollisionTest.class);
    suite.addTestSuite(NestedHandlerTest.class);
    suite.addTestSuite(ParameterTest.class);
    suite.addTestSuite(TrailingSlashTest.class);
    suite.addTestSuite(CallbacksTest.class);
    suite.addTestSuite(PropTest.class);
    suite.addTestSuite(PropertyTest.class);
    suite.addTestSuite(CssTest.class);
    suite.addTestSuite(TreeTest.class);
    suite.addTestSuite(TreeTest2.class);
    suite.addTestSuite(TreeCollisionTest.class);
    suite.addTestSuite(TreeCollision2Test.class);
    suite.addTestSuite(Collision3Test.class);
    suite.addTestSuite(ParseTreeTest.class);
    suite.addTestSuite(SimpleSecureTest.class);
    suite.addTestSuite(NestedSecureTest.class);
    suite.addTestSuite(MatchTreeTest.class);
    suite.addTestSuite(I18nTest.class);
    suite.addTestSuite(ScriptTest.class);
    suite.addTestSuite(ScriptCycleTest.class);
    suite.addTestSuite(TailParseTreeTest.class);
    suite.addTestSuite(ReplaceUtilTest.class);
    suite.addTestSuite(PropertiesTest.class);
    suite.addTestSuite(ScriptVarTest.class);
    suite.addTestSuite(BasicScript2Test.class);
    suite.addTestSuite(CachingJavaScriptProcessorTest.class);
    suite.addTestSuite(ConstructorTest.class);
    suite.addTestSuite(ActionServiceTest.class);
    suite.addTestSuite(FormTest.class);
    suite.addTestSuite(ReflectionUtilTest.class);
    suite.addTestSuite(ZFormElementMirrorTest.class);
    suite.addTestSuite(ZSerializeUtilTest.class);
    suite.addTestSuite(VariableNamesScriptTest.class);
    suite.addTestSuite(ZJsonTest.class);
    suite.addTestSuite(FormTestOpCallback.class);
    // suite.addTestSuite(SessionFormTest.class);
    suite.addTestSuite(ZFormValuesTest.class);
    suite.addTestSuite(ZRenderClassRepositoryTest.class);
    suite.addTestSuite(ZExposedMethodRepositoryTest.class);
    suite.addTestSuite(FormTest.class);
    suite.addTestSuite(NamedFormTest.class);
    suite.addTestSuite(MapTestPojoTest.class);

    // suite.addTestSuite(ZTemplatesInternalArchitectureTest.class);

    // $JUnit-END$

    return suite;
  }


  public static void main(String[] args)
  {
    TestRunner.run(AllTests.suite());
  }

}
