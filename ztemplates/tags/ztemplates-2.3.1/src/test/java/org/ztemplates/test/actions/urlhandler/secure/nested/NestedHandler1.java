package org.ztemplates.test.actions.urlhandler.secure.nested;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;

/**
 */
@ZMatch(value = "nested1")
@ZSecure("katze")
public class NestedHandler1 implements INested
{
}
