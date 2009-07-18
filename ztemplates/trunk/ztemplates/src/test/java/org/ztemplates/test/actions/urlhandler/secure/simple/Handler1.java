package org.ztemplates.test.actions.urlhandler.secure.simple;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;

/**
 */
@ZMatch(value = "/test")
@ZSecure("katze")
public class Handler1
{
}
