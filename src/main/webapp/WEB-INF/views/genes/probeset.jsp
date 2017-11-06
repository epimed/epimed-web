<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}- Find probesets for a list of
	gene symbols</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

<!-- AJAX functions -->
<script src="<c:url value="/js/ajax.js" />"></script>

<script>
	function updateForm() {
		setTaxid();
		idSelectedPlatform = document.getElementById("idSelectedPlatform").value;
		window.location.href = "${pageContext.request.contextPath}/genes/probeset?taxid="
				+ taxid + "&idSelectedPlatform=" + idSelectedPlatform;
	}

	window.onload = function() {
		document.getElementById("hs").addEventListener('click', updateForm,
				false);
		document.getElementById("mm").addEventListener('click', updateForm,
				false);
		document.getElementById("idSelectedPlatform").addEventListener(
				'change', updateForm, false);
		idSelectedPlatform = document.getElementById("idSelectedPlatform").value;
		url = "ajax/upload";
		jobtype = "probeset";
		jobid = "";
		enableSubmitButtonAfterUpload = true;
		document.getElementById('submitButton').addEventListener('click',
				startSubmit, false);
	}
</script>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Find probesets for a list of gene symbols</h1>

		<p class="lead">Submit a list of gene symbols and download
			corresponding probesets</p>

		<p>Recognized symbols: Entrez Gene ID, current and previous gene
			symbols, probesets, accession numbers from UniGene, UCSC, Ensembl,
			Uniprot, ...</p>

		<div>
			<form>

				<!-- Organism -->

				<p class="lead">Select a platform:</p>

				<c:set var="hsckeck" value="checked='checked'" />
				<c:set var="mmckeck" value="" />
				<c:if test="${not empty taxid and taxid==10090}">
					<c:set var="hsckeck" value="" />
					<c:set var="mmckeck" value="checked='checked'" />
				</c:if>

				<label class="radio-inline"> <input type="radio"
					name="organism" ${hsckeck} value="9606" id="hs">Homo
					sapiens
				</label> <label class="radio-inline"><input type="radio"
					name="organism" ${mmckeck} value="10090" id="mm">Mus
					musculus</label>
				<p></p>

				<!-- Platforms -->

				<select class="form-control" id="idSelectedPlatform">
					<c:forEach var="platform" items="${listPlatforms}">
						<c:choose>
							<c:when test="${platform.uid==idSelectedPlatform}">
								<option value="${platform.uid}" selected="selected">${platform.uid} - ${platform.title}</option>
							</c:when>
							<c:otherwise>
								<option value="${platform.uid}">${platform.uid} - ${platform.title}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>

				<p style="margin-bottom: 0.5cm;"></p>

				<!-- Gene list -->

				<p class="lead">Enter a list of genes:</p>

				<div class="input-group">
					<span class="input-group-addon" id="basic-addon1">List of
						genes</span>
					<textarea class="form-control" rows="3" id="input"
						placeholder="Put gene symbols here (ex. MLL1, KMT2A KAT6A; TP53)"></textarea>
				</div>

				<p></p>
				<input id="submitButton" type="button" value="Submit" />

			</form>

			<p style="margin-bottom: 1cm;"></p>

			<div class="progress">
				<div id="bar" class="progress-bar" role="progressbar"
					style="width: 0%;" aria-valuenow="0" aria-valuemin="0"
					aria-valuemax="100"></div>
			</div>

			<div id='debug' style='height: 100px; overflow: auto'></div>

			<p style="margin-bottom: 1cm;"></p>

			<p id='jobid'></p>

			<div>
				<input name="downloadButton" type="submit" style="display: none;"
					disabled='disabled' onclick="getJob('excel')"
					value="Download Excel" />
				<p></p>

				<input name="downloadButton" type="submit" style="display: none;"
					disabled='disabled' onclick="getJob('csv')" value="Download CSV" />
			</div>

		</div>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>