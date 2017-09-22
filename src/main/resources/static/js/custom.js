/**
 * Custom Javascript functions
 */

function message(message) {
	alert(message);
}

/** ========================================================================== */

/** ========================================================================== */

function getXMLHttpRequest() {
	var xhr = null;

	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("Your browser does not support XMLHTTPRequest...");
		return null;
	}

	return xhr;
}

/** ========================================================================== */