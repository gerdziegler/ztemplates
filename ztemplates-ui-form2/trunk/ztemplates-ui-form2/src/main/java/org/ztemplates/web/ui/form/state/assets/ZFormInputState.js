$(function(){
	//var netFailureImg = new Image();
	//netFailureImg.src = "ztemplates-ui-form/state/assets/reloadStateFailure.png";

	//init form items	
	$("form[class='ztemplates-form']").each(function(){
		var form = this;
		var formId = form.id;
		$(form).find(".ZFormInputState-state").each(function(){
			updateInputState(form, this);
			$(this).hover(function() {
				if($(this).hasClass("ZFormInputState-state_error")) {
					var text = $(this).next(".ZFormInputState-text"); 
					text.animate({opacity: "show", left: "25"}, "slow");
				}
			}, function() {
				if($(this).hasClass("ZFormInputState-state_error")) {
					var text = $(this).next(".ZFormInputState-text");
					text.animate({opacity: "hide", left: "50"}, "fast");
				}
			});
		});
		$(form).bind('ztemplates-form-changed', function(e, triggerName) {
			$('#' + formId).find(".ZFormInputState-state").each(function(){
				updateInputState(form, this);
			});
		});
		$(form).bind('ztemplates-reload-state-begin', function(e, triggerName) {
			var stateDivProxy = $("div[propertynames*='" + triggerName + "']"); 
			stateDivProxy.removeClass("reloadStateFailure");
			stateDivProxy.addClass("reloadState");
		});
		$(form).bind('ztemplates-reload-state-end', function(e, triggerName) {
			var stateDivProxy = $("div[propertynames*='" + triggerName + "']"); 
			stateDivProxy.removeClass("reloadState");
		});
		$(form).bind('ztemplates-reload-state-failure', function(e, triggerName) {
			var stateDivProxy = $("div[propertynames*='" + triggerName + "']"); 
			stateDivProxy.removeClass("reloadState");
			stateDivProxy.addClass("reloadStateFailure");
		});		
	});	
});
function updateInputState(form, stateDiv){
	var stateDivProxy = $(stateDiv);
	var textDiv = document.getElementById(stateDiv.id + "Text");
	var textDivProxy = $(textDiv);
	var propertyNames = stateDivProxy.attr('propertynames').split(",");	
	var text = "";
	var error = false;
	for(var i=0; i<propertyNames.length;i++)
	{
		var propertyName = propertyNames[i];
		var json = ZTEMPLATES.getProperty(form.id, propertyName);
		if(json.error)
		{
			error = true;
			text+=json.state.text + "\n";
		}
	}
	textDivProxy.html(text);
	if(error) {
		stateDivProxy.removeClass("ZFormInputState-state_ok");
		stateDivProxy.addClass("ZFormInputState-state_error");
	} else {
		stateDivProxy.removeClass("ZFormInputState-state_error");
		stateDivProxy.addClass("ZFormInputState-state_ok");
	}
}

