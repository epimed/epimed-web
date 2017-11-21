<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

<!-- AJAX functions -->
<script src="<c:url value="/js/ajax.js" />"></script>

<script>

	function setPositionType() {
		var elements = document.getElementsByName('positionType');
		if (elements != null) {
			var i = 0;
			var isFound = false;
			while (!isFound && i < elements.length) {
				if (elements[i].checked) {
					isFound = true;
					positionType = elements[i].value;
				}
				i = i + 1;
			}
		}
	}

	function updateParameters() {
		idAssembly = document.getElementById("idAssembly").value;
		setPositionType();
	}

	window.onload = function() {
		url = "ajax/upload";
		jobtype = "position";
		jobid = "";
		enableSubmitButtonAfterUpload = true;
		updateParameters();
		document.getElementById('submitButton').addEventListener('click',
				startSubmit, false);
		document.getElementById('idAssembly').addEventListener('change',
				updateParameters, false);
		document.getElementById('positionTypeUnique').addEventListener('click',
				updateParameters, false);
		document.getElementById('positionTypeAll').addEventListener('click',
				updateParameters, false);
	}
</script>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Find positions for a list of gene symbols</h1>

		<p class="lead">Submit a list of gene symbols and download
			corresponding positions</p>


		<form:form class="form-horizontal" method="POST"
			action="${pageContext.request.contextPath}/genes/position">

			<!-- Organism -->
			<div class="form-group">

				<label class="col-sm-2 control-label">Organism</label>
				<div class="col-sm-10">

					<!-- Homo sapiens -->
					<label class="radio-inline" onchange="this.form.submit()">
						<input type="radio" name="organism"
						<c:if test="${selectedTaxid==9606}">checked="checked"</c:if>
						value="9606">Homo sapiens
					</label>

					<!-- Mus musculus -->
					<label class="radio-inline" onchange="this.form.submit()"><input
						type="radio" name="organism"
						<c:if test="${selectedTaxid==10090}">checked="checked"</c:if>
						value="10090">Mus musculus </label>

				</div>
			</div>



			<!-- Assembly -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Assembly</label>
				<div class="col-sm-5">
					<select class="form-control" name="idAssembly" id="idAssembly">
						<c:forEach var="assembly" items="${listAssemblies}">
							<option value="${assembly.uid}"
								label="${assembly.uid} - ${assembly.ucscCode}"
								<c:if test="${assembly.uid==selectedAssembly}">selected</c:if> />
						</c:forEach>
					</select>
				</div>
			</div>


			<!-- Output -->
			<div class="form-group">

				<label class="col-sm-2 control-label">Output type</label>
				<div class="col-sm-10">

					<!-- Unique position -->
					<label class="radio-inline"> <input type="radio"
						name="positionType" id="positionTypeUnique"
						<c:if test="${selectedPositionType=='unique'}">checked="checked"</c:if>
						value="unique">Only main position (unique per gene)
					</label>

					<!-- All positions -->
					<label class="radio-inline"><input type="radio"
						name="positionType" id="positionTypeAll"
						<c:if test="${selectedPositionType=='all'}">checked="checked"</c:if>
						value="all">All positions</label>

				</div>
			</div>


			<!-- Gene list -->

			<p>Recognized symbols: Entrez Gene ID, current and previous gene
				symbols, probesets, accession numbers from UniGene, UCSC, Ensembl,
				Uniprot, ...</p>


			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">List of
					genes</span>
				<textarea class="form-control" rows="3" id="input" name="input"
					placeholder="Put gene symbols here (ex. MLL1, KMT2A KAT6A; TP53)">${input}</textarea>
			</div>



		</form:form>


		<p></p>
		<form>
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
				disabled='disabled' onclick="getJob('excel')" value="Download Excel" />
			<p></p>

			<input name="downloadButton" type="submit" style="display: none;"
				disabled='disabled' onclick="getJob('csv')" value="Download CSV" />
			<p></p>
				
			<input name="downloadButton" type="submit" style="display: none;"
				disabled='disabled' onclick="getJob('bed')" value="Download BED" />
		</div>

	</div>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>