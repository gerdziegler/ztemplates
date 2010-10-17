package org.ztemplates.web.request.impl;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.render.ZIRenderedObject;

class ZRenderedObject implements ZIRenderedObject {
	private static final long serialVersionUID = 1L;

	private final String text;

	private final List<List<String>> javaScriptExposed;

	private final List<List<String>> cssExposed;

	public ZRenderedObject(String text) {
		this.text = text;
		this.javaScriptExposed = new ArrayList<List<String>>();
		this.cssExposed = new ArrayList<List<String>>();
	}

	public ZRenderedObject(String text, List<List<String>> javaScriptExposed,
			List<List<String>> cssExposed) {
		super();
		this.text = text;
		this.javaScriptExposed = javaScriptExposed;
		this.cssExposed = cssExposed;
	}

	public String getText() {
		return text;
	}

	public List<List<String>> getJavaScriptExposed() {
		return javaScriptExposed;
	}

	public List<List<String>> getCssExposed() {
		return cssExposed;
	}
}
