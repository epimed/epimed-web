/**
 * Custom Javascript functions
 */

var url, listElements, totalElements, totalUploaded, currentIndex, taxid, idAssembly, positionType, jobid, idSelectedPlatform, jobtype, enableSubmitButtonAfterUpload;

/** ========================================================================== */

//To log everything on console
function debug(s) {
	var debug = document.getElementById('debug');
	if (debug) {
		debug.innerHTML = debug.innerHTML + '<br/>' + s;
		debug.scrollTop = debug.scrollHeight;
	}
}

/** ========================================================================== */

function updateProgressBar(percentComplete) {
	var bar = document.getElementById('bar');
	bar.style.width = percentComplete + '%';
	bar.innerHTML = percentComplete + ' %';
}

/** ========================================================================== */

//On processing
function onUploadProgress(e) {
	debug("Processing " + listElements[currentIndex] + "\t ("
			+ (totalUploaded + 1) + "/" + totalElements + ")");
}

/** ========================================================================== */

//Will be called when upload is completed
function onUploadComplete(e) {

	var ajaxResponse = JSON.parse(e.target.responseText);
	debug("---> " + ajaxResponse.message);
	// console.log(e);

	jobid = ajaxResponse.jobid;
	var jobidElement = document.getElementById("jobid")
	if (jobidElement!=null) {
		jobidElement.innerHTML = "Job ID " + jobid;
	}

	totalUploaded = totalUploaded + 1;
	currentIndex = currentIndex + 1;
	if (totalUploaded < totalElements) {
		var percentComplete = parseInt((totalUploaded) * 100
				/ totalElements);
		updateProgressBar(percentComplete);
		uploadNext();
	} 
	else {
		updateProgressBar(100);
		terminate();
	}
}

/** ========================================================================== */

function onUploadFailed(e) {
	debug("Error catched while processing " + listElements[currentIndex]);
}

/** ========================================================================== */

function init() {

	// === Initiate global variables ===
	var input = document.getElementById('input');
	if (input!=null) {
		listElements = convertStringToArray(input.value);
	}

	totalElements = 0;
	if (listElements!=null) {
		totalElements = listElements.length;
	}
	totalUploaded = 0;
	currentIndex = 0;
	setTaxid();

	// === Manage DOM elements ===
	updateProgressBar(0);
	document.getElementById("debug").innerHTML = "";
	debug("Job initiated");
	document.getElementById('submitButton').disabled = true;
	manageElement('downloadButton', ['hide', 'disable']);
}

/** ========================================================================== */

function uploadNext() {
	var symbol = listElements[currentIndex];

	var fd = new FormData();
	fd.append("jobid", jobid);
	fd.append("symbol", symbol);
	fd.append("taxid", taxid);
	fd.append("totalElements", totalElements);
	fd.append("currentIndex", currentIndex);
	fd.append("idSelectedPlatform", idSelectedPlatform);
	fd.append("idAssembly", idAssembly);
	fd.append("positionType", positionType);
	fd.append("jobtype", jobtype);
	if (currentIndex==0) {
		fd.append("listElements", listElements);
	}

	var xhr = new XMLHttpRequest();
	xhr.upload.addEventListener("progress", onUploadProgress, false);
	xhr.addEventListener("load", onUploadComplete, false);
	xhr.addEventListener("error", onUploadFailed, false);
	xhr.open("POST", url, true);
	xhr.send(fd);
}

/** ========================================================================== */

function terminate() {
	debug("Job terminated");
	debug("File(s) generated, please click on download link(s) below");
	manageElement('downloadButton', ['show', 'enable']);

	if (enableSubmitButtonAfterUpload==null || enableSubmitButtonAfterUpload) {
		document.getElementById('submitButton').disabled = false;
	}
}

/** ========================================================================== */

function manageElement(name, actions) {

	var buttons = document.getElementsByName(name);

	for (i=0; i<buttons.length; i++) {
		var button = buttons[i];
		for (j=0; j<actions.length; j++) {
			var action = actions[j];

			if (action=='hide' || action=='none') {
				button.style.display = 'none';
			}

			if (action=='show' || action=='display' || action=='block') {
				button.style.display = 'block';
			}

			if (action=='enable' || action=='enabled') {
				button.disabled = false;
			}

			if (action=='disable' || action=='disabled') {
				button.disabled = true;
			}

		}
	}

}

/** ========================================================================== */

function setTaxid() {
	var organisms = document.getElementsByName('organism');
	if (organisms!=null) {
		var i = 0;
		var isFound = false;
		taxid = 9606; // default value Homo sapiens
		while (!isFound && i < organisms.length) {
			if (organisms[i].checked) {
				isFound = true;
				taxid = organisms[i].value;
			}
			i = i + 1;
		}
	}
}


/** ========================================================================== */

function convertStringToArray(text) {
	try {
		text = text.replace(/\[|\]/gi, "");
		return text.trim().split(/[\s,\s;\s:\s|]+/);
	} catch (err) {
		debug("ERROR. " + err.message);
		return null;
	}
}

/** ========================================================================== */

function startSubmit() {
	init();
	uploadNext();
}

/** ========================================================================== */

function getJob(format) {
	var urljobid = "nojobid";
	if (jobid!=null) {
		urljobid = jobid
	}
	var url = "job/download/?" + "jobid=" + jobid + "&format=" + format;
	debug("Download file in format " + format + " for jobid " + jobid);
	window.location.href = url;
}

/** ========================================================================== */