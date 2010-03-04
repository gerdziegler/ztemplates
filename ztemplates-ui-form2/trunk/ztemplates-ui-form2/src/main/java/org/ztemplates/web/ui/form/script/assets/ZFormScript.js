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
}
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
}
ZTEMPLATES.revalidateProperties = function(formId) {
	var form = ZTEMPLATES.forms[formId];
	for ( var i = 0; i < form.state.ztemplates.properties.length; i++) {
		var propertyName = form.state.ztemplates.properties[i];
		var prop = ZTEMPLATES.getProperty(formId, propertyName);
		ZTEMPLATES.validateProperty(formId, propertyName, prop.stringValue);
	}
}
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
}
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
}
ZTEMPLATES.getProperty = function(formId, propertyName){
	var propName = 'ZTEMPLATES.forms.' + formId + '.state.' + propertyName;
	try {
	  var json = eval(propName);
	  return json;
	} catch(e) {
		alert('object not found: ' + propName);
	}
}
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
}
function initFormRadio(form, input) {
	var formId = form.id;
	var propertyName = input.name;

	updateFormRadio(form, input, "");
	$(input).change(function(e) {
		if(input.checked) {
			ZTEMPLATES.reloadState(formId, propertyName, input.value);
		}		
	});
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateFormRadio(form, input, triggerId);
	});
}
function updateFormRadio(form, input, triggerName) {
	var formId = form.id;
	var propertyName = input.name;
	var inputId = input.id;
	var cssId = $(input).attr("cssid");
	
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	if(input.value==json.stringValue) {
		input.checked = "checked";
	} else {
		input.checked = "";
	}
	if(json.writeable) {
		input.disabled="";
	} else {
		input.disabled="disabled";
	}

	ZTEMPLATES.updateStyle(formId, propertyName, inputId, cssId + "-input");
}
function initFormCheckbox(form, input) {	
	updateFormCheckbox(form, input, "");
	var formId = form.id;
	var propertyName = input.name;
	$(input).change(function(e) {
		ZTEMPLATES.reloadState(formId, propertyName, input.checked.toString());
	});
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateFormCheckbox(form, input, triggerId);
	});
}
function updateFormCheckbox(form, input, triggerName) {
	var formId = form.id;
	var propertyName = input.name;
	var inputId = input.id;
	var cssId = $(input).attr("cssid");
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	input.checked = (json.stringValue=='true');
    //$(input).val([json.stringValue]);
	if(!json.writeable) {
		input.disabled = true;
	} else {
		input.disabled = false;
	}	
	ZTEMPLATES.updateStyle(formId, propertyName, inputId, cssId + "-input");
}
function initFormSelect(form, input) {
	var formId = form.id;
	var propertyName = input.name;
	var selectId = input.id;
	updateFormSelect(formId, propertyName, selectId, "");
	$(input).change(function(e) {
		var content = document.getElementById(selectId);
		var value = content.options[content.selectedIndex].value;
		ZTEMPLATES.reloadState(formId, propertyName, value);
	});
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateFormSelect(formId, propertyName, selectId, triggerId);
	});
}
function updateFormSelect(formId, propertyName, selectId, triggerName) {
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	var content = document.getElementById(selectId);
	var cssId = $(content).attr("cssid");	
	content.innerHTML = '';
	for(i=0; i<json.allowedStringValues.length; i++) {
		var crt = json.allowedStringValues[i];
		var crtValue = json.allowedDisplayValues[i];
		var elem = document.createElement("option");
		elem.value = crt;
		elem.appendChild(document.createTextNode(crtValue));
		content.appendChild(elem);
	}
	if(json.stringValue==null) {
		content.value = "";
	} else {
		content.value = json.stringValue;
	}
	if(json.writeable) {
		content.disabled="";
	} else {
		content.disabled="disabled";
	}
	ZTEMPLATES.updateStyle(formId, propertyName, selectId, cssId + "-input");
}
function initFormSubmit(form, input) {
	var formId = form.id;
	var propertyName = input.name;
	updateInputSubmit(form, input, "");
	$(input).change( function(e) {
		ZTEMPLATES.reloadState(formId, propertyName, input.value);
	});
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateInputSubmit(form, input, triggerId);
	});
	$(form).bind('ztemplates-reload-state-begin', function(e, triggerId) {
		input.disabled = "disabled";
	});
}
function updateInputSubmit(form, input, triggerName) {
	var formId = form.id;
	var propertyName = input.name;
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	input.value = json.allowedValue;
	if(json.writeable) {
		input.disabled="";
	} else {
		input.disabled="disabled";
	}
	var inputProxy = $(input);
	inputProxy.removeClass("readonly");
	inputProxy.removeClass("required");
	if(!json.writeable) {
		inputProxy.addClass("readonly");
	} else if(json.required && json.empty) {
		inputProxy.addClass("required");
	}
}
function initInputTextArea(form, input) {
	var inputId = input.id;
	var autoresize = $(input).attr("autoresize");
	initInputText(form, input);
	if(autoresize) {
		input.style.overflow='hidden';
		$(input).keyup(function(e) {
			$("#"+inputId).each(function(){
				this.style.height = this.scrollHeight + 'px';
			});
		});
	}
}
function initInputText(form, input) {	
	var formId = form.id;
	var propertyName = input.name;
	var inputId = input.id;
	updateInputText(formId, propertyName, inputId, "");
	$(input).change(function(e) {	
		ZTEMPLATES.reloadState(formId, propertyName, $("#"+inputId).val()); 
	});
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateInputText(formId, propertyName, inputId, triggerId);
	});
}
function updateInputText(formId, propertyName, inputId, triggerName) {
	var content = document.getElementById(inputId);
	var cssId = $(content).attr("cssid");
	var json = ZTEMPLATES.getProperty(formId, propertyName);
	if(triggerName!=propertyName) {
		if(json.stringValue==null) {
			content.value = "";
		} else {
			content.value = json.stringValue;
		}
	}
	if(json.writeable) {
		content.readOnly = "";
	} else {
		content.readOnly="readonly";
	}
	ZTEMPLATES.updateStyle(formId, propertyName, inputId, cssId + "-input");
}
function initInputHidden(form, input) {
	var formId = form.id;
	var propertyName = input.name;
	var inputId = input.id;
	updateInputHidden(formId, propertyName, inputId, "");

	$(input).change(function(e) {	
		ZTEMPLATES.forms[formId].reloadState(propertyName, $("#"+inputId).val()); 
	});
	
	$(form).bind('ztemplates-form-changed', function(e, triggerId) {
		updateInputHidden(formId, propertyName, inputId, triggerId);
	});
}
function updateInputHidden(formId, propertyName, inputId, triggerName) {
	var content = document.getElementById(inputId);
	var json = ZTEMPLATES.getProperty(formId, propertyName);

	if(triggerName!=propertyName) {
		if(json.stringValue==null) {
			content.value = "";
		} else {
			content.value = json.stringValue;
		}
	}
}
$(function(){
	//init form items	
	$("form[class='ztemplates-form']").each(function(){
		var form = this;
		var formId = form.id; 

		ZTEMPLATES.forms[formId].id = formId;
	
		$(this).find("input[type='text'],input[type='file']").each(function(){
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