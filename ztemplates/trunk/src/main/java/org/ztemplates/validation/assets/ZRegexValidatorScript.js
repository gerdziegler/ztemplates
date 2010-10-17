zregexValidatorScript = function(formId, propertyName, validator, value) {
	if(value.match(validator.regex)) {
		return null;
	} 
	return validator.message;
};