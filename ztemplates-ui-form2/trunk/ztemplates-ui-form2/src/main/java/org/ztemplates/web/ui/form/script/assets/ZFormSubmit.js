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