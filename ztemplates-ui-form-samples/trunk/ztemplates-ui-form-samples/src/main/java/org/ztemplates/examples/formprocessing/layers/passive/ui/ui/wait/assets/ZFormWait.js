function initFormWait(formId, text, imgUrl) {
	var waitItem = 
		new YAHOO.widget.Panel("wait",  
			{ width:"240px", 
			  fixedcenter:true, 
			  close:false, 
			  draggable:false, 
			  zindex:4,
			  modal:true,
			  visible:false
			} 
		);

	waitItem.setHeader(text);
	waitItem.setBody('<img src="' + imgUrl + '" />');
	waitItem.render(document.body);
	$(function(e) {
		var form = document.getElementById(formId);
		var formProxy = $(form);
		formProxy.bind("ztemplates-reload-state-begin", function() {
			waitItem.show();
		});
		formProxy.bind("ztemplates-reload-state-end", function() {
			waitItem.hide();
		});
		formProxy.bind("ztemplates-reload-state-failure", function() {
			waitItem.hide();
		});
	});
}
