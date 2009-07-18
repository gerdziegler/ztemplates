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
