<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}- Import a study from external
	database</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

<!-- AJAX functions -->
<script src="<c:url value="/js/ajax.js" />"></script>

<script>
	function initJob() {
		jobtype = "series";
		url = "ajax/upload/series";
		enableSubmitButtonAfterUpload = false;
		listElementsString = document.getElementById('listElements').innerHTML;
		listElements = convertStringToArray(listElementsString);
		jobid = document.getElementById('obtainedJobId').innerHTML;
		startSubmit();
	}
</script>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Import a study from external database</h1>

		<p>This web page allows you to import clinical data
			samples from an external database (NCBI GEO, ArrayExpress, NIH TCGA)
			into EpiMed Database</p>

		<p>Currently, the data import is automated only for NCBI GEO
			database. When the import is finished, the data will be immediately
			available for downloading in CSV format or through our API REST. For
			other data sources the automated import is not yet available. Your
			request will be sent to data manager. The data will be imported in a few days.</p>

		<p style="margin-bottom: 0.5cm;"></p>

		<form class="form-horizonta" method="post"
			action="${pageContext.request.contextPath}/series/add">

			<!-- Database -->
			<div class="form-group">
				<label class="col-2 control-label">External database</label>
				<div class="col-3">
					<select name="database" class="form-control">
						<option>NCBI GEO</option>
						<option>Array Express</option>
						<option>NIH TCGA</option>
						<option>Other</option>
					</select>
				</div>
			</div>


			<div class="form-group">
				<label class="col-2 control-label">Accession ID</label>
				<div class="col-3">
					<input type="text" name="idSeries" class="form-control"
						value="${idSeries}"
						placeholder="GSE number (ex. GSE30219), M-TAB number (ex. M-TAB-1733)">
				</div>
			</div>

			<div class="form-group">
				<button type="submit" class="btn btn-primary" name="button"
					value="submit">Submit</button>

			</div>

		</form>


		<!-- Error message, if present -->
		<c:if test="${not empty message}">
			<p style="margin-bottom: 1cm;"></p>
			<div>
				<span class="text-danger">${message}</span>
			</div>
		</c:if>


		<!-- Study to import, if present -->

		<c:if test="${not empty series}">
			<p style="margin-bottom: 1cm;"></p>

			<h2>Study&nbsp;${series.id}</h2>
			<div class="row">
				<div class="col-md-2">Title</div>
				<div class="col-md-10">${series.title}</div>
			</div>
			<div class="row">
				<div class="col-md-2">Number of samples</div>
				<div class="col-md-10">${series.nbSamples}</div>
			</div>
			<div class="row">
				<div class="col-md-2">Platforms</div>
				<div class="col-md-10">${series.platforms}</div>
			</div>

			<p style="margin-bottom: 0.5cm;"></p>


			<div class="form-group">
				<button type="submit" class="btn btn-success" id="submitButton"
					onclick="initJob()" name="submitButton" value="import">Import
					this study into EpiMed Database</button>
			</div>


			<p style="margin-bottom: 1cm;"></p>

			<div class="progress">
				<div id="bar" class="progress-bar" role="progressbar"
					style="width: 0%;" aria-valuenow="0" aria-valuemin="0"
					aria-valuemax="100"></div>
			</div>

			<div id='debug' style='height: 100px; overflow: auto'></div>

			<p style="margin-bottom: 1cm;"></p>

			<div>
				<input name="downloadButton" type="submit" style="display: none;"
					disabled='disabled'
					onclick="window.location.href='${pageContext.request.contextPath}/expgroup/${series.id}';"
					value="Download standard exp_group CSV" />
				<p></p>
				<input name="downloadButton" type="submit" style="display: none;"
					disabled='disabled'
					onclick="window.location.href='${pageContext.request.contextPath}/parameters/${series.id}';"
					value="Download original parameters CSV" />
			</div>


			<!-- Hidden values -->
			<div id='obtainedJobId' style='visibility: hidden'>${jobid}</div>
			<div id='listElements' style='visibility: hidden'>${listElements}</div>

		</c:if>
	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>