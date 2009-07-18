if (typeof ZTEMPLATES == 'undefined') {
	var ZTEMPLATES = new Object();
}
if (typeof ZTEMPLATES.forms == 'undefined') {
	ZTEMPLATES.forms = new Object();
}
if (typeof ZTEMPLATES.form == 'undefined') {
	ZTEMPLATES.form = new Object();
}
if(typeof ZTEMPLATES.util == 'undefined') {
	ZTEMPLATES.util= new Object();
}
ZTEMPLATES.util.runScripts = function(e) {
	if (e.nodeType != 1) return; //if it's not an element node, return 
	if (e.tagName.toLowerCase() == 'script') {
		eval(e.text); //run the script
	}
	else {
		var n = e.firstChild;
		while ( n ) {
			if ( n.nodeType == 1 ) ZTEMPLATES.util.runScripts( n ); //if it's an element node, recurse
			n = n.nextSibling;
		}
	}
};	
ZTEMPLATES.validateOperations = function(formId) {
	var form = ZTEMPLATES.forms[formId];
	for ( var i = 0; i < form.state.ztemplates.operations.length; i++) {
		var operationName = form.state.ztemplates.operations[i];
		var operation = ZTEMPLATES.getProperty(formId, operationName);
		if (operation.stringValidators.length > 0) {
			operation.error = false;
			operation.state = null;
			for ( var j = 0; j < operation.stringValidators.length; j++) {
				if(operation.stringValidators[j].javaScriptMethodName!=null) {
					var validator = eval(operation.stringValidators[j].javaScriptMethodName);
					var error = validator(formId, operationName, operation.stringValidators[j]);
					if (error != null) {
						operation.error = true;
						operation.state = {
							"text" :error,
							"type" :"error"
						};
						return false;
					}
				}
			}
		}
	}
};
ZTEMPLATES.revalidateProperties = function(formId) {
	var form = ZTEMPLATES.forms[formId];
	for ( var i = 0; i < form.state.ztemplates.properties.length; i++) {
		var propertyName = form.state.ztemplates.properties[i];
		var prop = ZTEMPLATES.getProperty(formId, propertyName);
		ZTEMPLATES.validateProperty(formId, propertyName, prop.stringValue);
	}
};
ZTEMPLATES.validateProperty = function(formId, propertyName, value) {
	var prop = ZTEMPLATES.getProperty(formId, propertyName);
	if (value == null || value.length == 0) {
		if (prop.required) {
			prop.error = true;
			prop.state = {
				"text" :prop.requiredMessage,
				"type" :"error"
			};
			return true;
		} else {
			prop.error = false;
			prop.state = null;
			return false;
		}
	}
	prop.error = false;
	prop.state = null;
	for ( var i = 0; i < prop.stringValidators.length; i++) {
		if(prop.stringValidators[i].javaScriptMethodName!=null) {
			var validator = eval(prop.stringValidators[i].javaScriptMethodName);
			var error = validator(formId, propertyName,
					prop.stringValidators[i], value);
			if (error != null) {
				prop.error = true;
				prop.state = {
					"text":error,
					"type":"error"
				};
				return true;
			}
		}
	}
	return false;
};

ZTEMPLATES.reloadState = function(formId, propertyName, value){
	var form = ZTEMPLATES.forms[formId];
	var prop = ZTEMPLATES.getProperty(formId, propertyName);
	prop.stringValue = value;
	form.modified = true;

	prop.empty = value == null || value.length == 0;
	
	var formProxy = $('#'+formId); 
	
	var ajaxReloadState = false;
	for(var i=0; i<form.ajaxPropertyNames.length && !ajaxReloadState; i++)
	{
		if(form.ajaxPropertyNames[i]==propertyName)
		{
			ajaxReloadState = true;
			break;
		}
	}	
	if (!ajaxReloadState) {
		ZTEMPLATES.revalidateProperties(formId);
		ZTEMPLATES.validateOperations(formId);
		formProxy.trigger('ztemplates-form-changed', propertyName);
		return;
	}
	
	formProxy.trigger('ztemplates-reload-state-begin',propertyName); 
	
	var parameters = $("#"+formId).serialize() /*+ "&" + encodeURI("zformscript.changed=" + propertyName)*/;
	$.ajax({
		type:"POST",
		url:form.ajaxUrl,
		data:parameters,
		dataType:"json",
		success:function(o) {
			formProxy.trigger('ztemplates-reload-state-end', propertyName);
			form.state = o;
			formProxy.find("input[name='zformscriptHidden']").val(form.state.ztemplates.zformscriptHidden);
			formProxy.trigger('ztemplates-form-changed', propertyName);
		},
		error: function(o) {
			formProxy.trigger('ztemplates-reload-state-failure', propertyName);
		}
	});	
};
ZTEMPLATES.getProperty = function(formId, propertyName){
  var json = eval('ZTEMPLATES.forms.' + formId + '.state.' + propertyName);
  return json;
};
ZTEMPLATES.updateStyle = function(formId, propertyName, contentId,
		defaultClassName){
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	var content = document.getElementById(contentId);
    var contentProxy = $(content); 
    contentProxy.removeClass(defaultClassName + "_readonly");
    contentProxy.removeClass(defaultClassName + "_required");
    contentProxy.removeClass(defaultClassName + "_error");
	if (!json.writeable) {
		contentProxy.addClass(defaultClassName + "_readonly");
	} else if (json.required
			&& (json.stringValue == null || json.stringValue.length == 0)) {
		contentProxy.addClass(defaultClassName + "_required");
	} else if (json.error) {
		contentProxy.addClass(defaultClassName + "_error");
	}
	return json;
};
ZTEMPLATES.form.initBeforeUnload = function(formId, message) {
	window.onbeforeunload = function() {
		if (ZTEMPLATES.forms[formId].modified) {
			return message;
		}
	};
	$("#" + formId).submit(function(e) {
		ZTEMPLATES.forms[formId].modified = false;
	});
};
$(function(){
	//init form items	
	$("form[class='ztemplates-form']").each(function(){
		var form = this;
		var formId = form.id; 

		ZTEMPLATES.forms[formId].id = formId;
	
		$(this).find("input[type='text']").each(function(){
			initInputText(form, this);
		});
		$(this).find("input[type='password']").each(function(){
			initInputText(form, this);
		});
		$(this).find("textarea").each(function(){
			initInputTextArea(form, this);
		});
		$(this).find("input[type='hidden']").each(function(){
			if("zformscriptHidden"!=this.name) {
				initInputHidden(form, this);
			}
		});
		$(this).find("input[type='radio']").each(function(){
			initFormRadio(form, this);
		});
		$(this).find("input[type='checkbox']").each(function(){
			initFormCheckbox(form, this);
		});
		$(this).find("select").each(function(){
			initFormSelect(form, this);
		});
		$(this).find("input[type='submit']").each(function(){
			initFormSubmit(form, this);
		});		
	});
});