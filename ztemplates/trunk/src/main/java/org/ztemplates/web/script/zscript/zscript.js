/**
 * zscript 1.0.3 
 * (c) 2011 www.gerdziegler.de, www.ztemplates.org
 * Apache Licence 2.0
 * requires jquery http://www.jquery.org
 */
if(typeof zscript === 'undefined') {
    var zscript = function() {	
        var hasConsole = typeof console!=='undefined';
        
        var log = hasConsole && (typeof console.log!=='undefined')? function(text){console.log(text)} : function(text){};
        var info = hasConsole && (typeof console.info!=='undefined')? function(text){console.info(text)} : function(text){log(text)};
        var warn = hasConsole && (typeof console.warn!=='undefined')? function(text){console.warn(text)} : function(text){log(text)};
        var error = hasConsole && (typeof console.error!=='undefined')? function(text){console.error(text)} : function(text){log(text)};
    	
    	var definitions = new Array();
		var callbackQueue = new Array();
	    var loading = false;
		
		function defineImpl(script, url) {
			log('define: ' + script + ' = ' + url);
			if(typeof definitions[script]==='undefined') {
				definitions[script] = {'url': url, 'loadRequested':false, 'loaded':false};
			} else if(definitions[script].url!=url) {
				error('script already defined with another url: ' + script + " --- old: " + definitions[script].url + " --- new: " + url);
			} else {
				log('script already defined: ' + script);
			}
		}
			
		function loadImpl(scriptParam, callback) {
			var scripts = scriptParam;
			if(!jQuery.isArray(scriptParam)){
				scripts = [scriptParam];
			}
			for(var idx in scripts){
				if(typeof scripts[idx]==='undefined') {
					log.error('undefined script ' + idx + ' call define() first');
				}
				var script = scripts[idx];
				if(definitions[script].loadRequested!==true) {
					definitions[script].loadRequested = true;
				}
			}
			if(typeof callback!=='undefined') {
				callbackQueue.push({'scripts': scripts, 'callback': callback});			
			}
			if(loading) {
				return;
			}
			loading=true;
			loadScripts();
		}	
		
		function loadScripts() {
			for(var idx in  definitions){
				var def = definitions[idx];
				if(def.loadRequested && !def.loaded){
					def.loaded=true;
					debug();
					var scriptLocation = def.url;			
				 	info ('--> loading: ' + idx + ' from ' + scriptLocation);
					//jQuery('head').append('<script src="' + scriptLocation + '" ></script>')
					//loadScripts();
					jQuery.ajax({
						type: "GET",
						url: scriptLocation,
						data: null,
						success: function(data){
						    log('    loaded   ' + idx + ' from ' + scriptLocation);
							loadScripts();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
						    error('    failed to load '  + idx + ' from ' + scriptLocation);
							loadScripts();
						},
						dataType: 'script'
					});
					
					return;
				}				
			}
			processCallbackQueue();
		}	
		
		function processCallbackQueue() {
			debug();
			while(callbackQueue.length>0) {
				var call = callbackQueue[0];
				for(var i=0; i<call.scripts.length;i++){
					if(definitions[call.scripts[i]].loaded!==true) {
						error('script "' + call.scripts[i] + '" not loaded for callback, nested loads with callback are not allowed: use a script for nested method and declare dependency in the script.\n' +  call.callback);
						loadScripts();
						return;
					}
				}
				callbackQueue.shift();
				log('*** calling ' + call.callback);
				call.callback();
			}		
			loading=false;
		}
		
	     
		var cnt = 0;
		var enableDebug = false;
		function setDebugImpl(enableDebugParam) {
			enableDebug = enableDebugParam;
			cnt=0;
		}
		
		function setLoadedImpl(script){
			definitions[script].loaded=true;
		}
		
		function debug() {
			if(!enableDebug) {
				return;
			}
			cnt++;
			var s = cnt + '...';
			for(var idx in definitions) {
				var def=definitions[idx];
				if(def.loadRequested || def.loaded) {
					s+= '\n    ' + idx + '={loadRequested:' + def.loadRequested + ', loaded:' + def.loaded + ', url:' + def.url + '}'
				}
			}
			s+= '\n    callbackQueue:' + callbackQueue;
			log(s);		
		}
	
		return {
			define: function(script, url) {
				return defineImpl(script, url);
			},
			requires: function(scripts, callback) {
				return loadImpl(scripts, callback);
			},
			setLoaded: function(script) {
				return setLoadedImpl(script);
			},
			setDebug: function(enableDebug) {
				return setDebugImpl(enableDebug);
			},
			log: function(text) {
				log(text);
			},
			info: function(text) {
				debug(text);
			},
			warn: function(text) {
				warn(text);
			},
			error: function(text) {
				error(text);
			}
		};
	}();	
} 