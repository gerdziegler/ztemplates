if(ZTEMPLATES==null) {
	var ZTEMPLATES = new Object();
}
if(ZTEMPLATES.validators==null) {
	ZTEMPLATES.validators = new Object();
}
ZTEMPLATES.validators.ZOperationValidator = function(formId, operationName, validator) {
	for(var i=0; i<validator.propertyNames.length; i++) {
		var propName = validator.propertyNames[i];		
		var prop = ZTEMPLATES.getProperty(formId, propName);
		if(prop.error) {
			return validator.message;
		} 
	}
	return null;
};