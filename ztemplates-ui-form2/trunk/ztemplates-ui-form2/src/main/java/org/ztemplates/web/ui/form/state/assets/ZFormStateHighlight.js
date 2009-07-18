ZTEMPLATES.highlightPropertyInputBackground = function(name, backgroundColor, callback) {
	var highlightPropertyProxy = $(":input[name='" + name + "']"); 
	if(highlightPropertyProxy.length!=1) {
		callback();		
	} else {
		highlightPropertyProxy.each(function(){
			var color = $(this).css("background-color");		
			$(this).css("background-color", backgroundColor);
			$(this).fadeTo(600, 0.4, function() {
				$(this).css({
					"background-color": color,
					"opacity": 1.0
				});
				callback();
			});	
		});
	}
};