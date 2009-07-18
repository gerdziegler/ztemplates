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

