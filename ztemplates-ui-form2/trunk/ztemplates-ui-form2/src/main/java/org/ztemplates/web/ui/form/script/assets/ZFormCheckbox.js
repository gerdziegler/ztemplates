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