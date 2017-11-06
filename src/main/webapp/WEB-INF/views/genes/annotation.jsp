<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Tissue-specific genes</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Tissue-specific genes</h1>

		<form:form class="form-horizontal" method="POST"
			action="${pageContext.request.contextPath}/genes/annotation">

			<!-- Organism -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Organism *</label>
				<div class="col-sm-10">
				
					<!-- Homo sapiens -->	
					<label class="radio-inline" onchange="this.form.submit()">
						<input type="radio" name="taxid"
						<c:if test="${selectedTaxid==9606}">checked="checked"</c:if>
						value="9606">Homo sapiens
					</label> 
					
					<!-- Mus musculus -->	
					<label class="radio-inline" onchange="this.form.submit()"><input
						type="radio" name="taxid"
						<c:if test="${selectedTaxid==10090}">checked="checked"</c:if>
						value="10090">Mus musculus
					</label>
				
				</div>
			</div>


			<!-- Source -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Source *</label>
				<div class="col-sm-5">
					<select class="form-control" name="source"
						onchange="this.form.submit()">
						<c:forEach var="source" items="${listSources}">
							<option value="${source}" label="${source}"
								<c:if test="${source==selectedSource}">selected</c:if> />
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Parameter -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Tissue *</label>
				<div class="col-sm-10">

					<c:if test="${not empty listParameters}">
						<c:forEach var="parameter" items="${listParameters}">
							<label class="radio-inline" onchange="this.form.submit()">
								<input type="radio" name="parameter" value="${parameter}"
								<c:if test="${parameter==selectedParameter}">checked='checked'</c:if>>
								${parameter}
							</label>
						</c:forEach>
					</c:if>

					<c:if test="${empty listParameters}">
						<span class="text-danger">No tissue available for your
							selection</span>
					</c:if>

				</div>
			</div>

			<!-- Annotations -->
			<div class="form-group">
				<label class="col-sm-2 control-label">Annotation *</label>
				<div class="col-sm-5">

					<c:if test="${not empty listUids}">
						<select class="form-control" name="uid"
							onchange="this.form.submit()">
							<option value="" label="--- Select annotation ---" />
							<c:forEach var="uid" items="${listUids}">
								<option value="${uid}" label="${uid}"
									<c:if test="${uid==selectedUid}">selected</c:if> />
							</c:forEach>
						</select>
					</c:if>

					<c:if test="${empty listUids}">
						<span class="text-danger">No annotation available for your
							selection</span>
					</c:if>

				</div>
			</div>


		</form:form>



		<!-- Result -->

		<c:if test="${not empty nbGenes}">
			<p>&nbsp;</p>
			<p>Number of found genes: ${nbGenes}</p>

			<c:if test="${nbGenes>0}">

				<spring:url value="/query/genes?annotations=${selectedUid}&format=excel"
					var="downloadExcel" />

				<button type="submit" class="btn btn-default btn-sm"
					onclick="location.href='${downloadExcel}'">
					<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
					Download genes in Excel format
				</button>

				<spring:url value="/query/genes?annotations=${selectedUid}&format=csv"
					var="downloadCsv" />

				<button type="submit" class="btn btn-default btn-sm"
					onclick="location.href='${downloadCsv}'">
					<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
					Download genes in CSV format
				</button>
			</c:if>

		</c:if>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>