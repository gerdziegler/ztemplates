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
