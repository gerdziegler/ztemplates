package org.ztemplates.render;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ztemplates.render.script.ZCssExposed;
import org.ztemplates.render.script.ZJavaScriptExposed;

public class ZRenderedObject implements Serializable {
    private final String text;

    private final SortedSet<ZJavaScriptExposed> javaScriptExposed;

    private final SortedSet<ZCssExposed> cssExposed;

    public ZRenderedObject(String text) {
	this.text = text;
	this.javaScriptExposed = new TreeSet<ZJavaScriptExposed>();
	this.cssExposed = new TreeSet<ZCssExposed>();
    }

    public ZRenderedObject(String text,
	    SortedSet<ZJavaScriptExposed> javaScriptExposed,
	    SortedSet<ZCssExposed> cssExposed) {
	super();
	this.text = text;
	this.javaScriptExposed = javaScriptExposed;
	this.cssExposed = cssExposed;
    }

    public String getText() {
	return text;
    }

    public SortedSet<ZJavaScriptExposed> getJavaScriptExposed() {
	return javaScriptExposed;
    }

    public SortedSet<ZCssExposed> getCssExposed() {
	return cssExposed;
    }
}
