<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Search for samples in the database</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<form:form class="form-horizontal" method="POST"
			modelAttribute="sampleForm"
			action="${pageContext.request.contextPath}/samples">

			<!-- Hidden attributes -->

			<form:hidden path="nbSamples" />
			<form:hidden path="listSeries" />
			<form:hidden path="listPlatforms" />
			<form:hidden path="urlParameter" />

			<h1>Search for samples in the database</h1>

			<!-- Platform type -->
			<spring:bind path="platformType">
				<div class="form-group">
					<label class="col-sm-2 control-label">Platform type</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="platformType"
							onchange="this.form.submit()">
							<form:option value="" label="--- Select all ---" />
							<c:forEach var="platformType" items="${listPlatformTypes}">
								<form:option value="${platformType}" label="${platformType}" />
							</c:forEach>
						</form:select>
						<form:errors path="platformType" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- Platform -->
			<spring:bind path="idPlatform">
				<div class="form-group">
					<label class="col-sm-2 control-label">Platform</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idPlatform">
							<form:option value="" label="--- Select all ---" />
							<c:forEach var="platform" items="${listPlatforms}">
								<form:option value="${platform._id}"
									label="${platform._id} - ${platform.title}" />
							</c:forEach>
						</form:select>
						<form:errors path="idPlatform" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- Topology -->
			<spring:bind path="idTopology">
				<div class="form-group">
					<label class="col-sm-2 control-label">Body site</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idTopology">
							<form:option value="" label="--- Select all ---" />
							<c:forEach var="topology" items="${listTopologies}">
								<form:option value="${topology._id.id_topology}"
									label="${topology._id.topology} - ${topology._id.topology_group}" />
							</c:forEach>
						</form:select>
						<form:errors path="idTopology" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<!-- Tissue status -->
			<spring:bind path="idTissueStatus">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Tissue status</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <form:radiobutton
								path="idTissueStatus" value="1" /> Normal
						</label> <label class="radio-inline"> <form:radiobutton
								path="idTissueStatus" value="2" /> Pathological non tumoral
						</label> <label class="radio-inline"> <form:radiobutton
								path="idTissueStatus" value="3" /> Tumoral
						</label>
						<form:errors path="idTissueStatus" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Tissue stage -->
			<spring:bind path="idTissueStage">
				<div class="form-group">
					<label class="col-sm-2 control-label">Tissue stage</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <form:radiobutton
								path="idTissueStage" value="1" /> Adult
						</label> <label class="radio-inline"> <form:radiobutton
								path="idTissueStage" value="2" /> Fetal
						</label> <label class="radio-inline"> <form:radiobutton
								path="idTissueStage" value="4" /> Embryonic
						</label>
						<form:errors path="idTissueStage" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Morphology -->
			<spring:bind path="idMorphology">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Morphology</label>
					<div class="col-sm-10">
						<form:select class="form-control" path="idMorphology">
							<form:option value="" label="--- Select all ---" />
							<c:forEach var="morphology" items="${listMorphologies}">
								<form:option value="${morphology._id.id_morphology}"
									label="${morphology._id.id_morphology} - ${morphology._id.morphology}" />
							</c:forEach>
						</form:select>
						<form:errors path="idMorphology" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<!-- Survival -->
			<spring:bind path="survival">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<div class="form-group">
						<label class="col-sm-2 control-label">Survival</label>
						<div class="col-sm-10">
							<label class="radio-inline"> <form:radiobutton
									path="survival" value="true" /> Only with survival information
							</label> <label class="radio-inline"> <form:radiobutton
									path="survival" value="false" /> Indifferent
							</label>
							<form:errors path="survival" class="control-label" />
						</div>
					</div>
				</div>
			</spring:bind>

			<p></p>

			<!-- Buttons -->

			<input type="submit" class="btn btn-primary" name="searchButton"
				value="Search" />

			<input type="submit" class="btn btn-danger" name="resetButton"
				value="Reset form" />
			
			<span class="text-danger"><form:errors path="*" class="control-label" /></span>

			
			<p></p>

			<!-- Result -->
			<c:if test="${not empty sampleForm.nbSamples}">

				<h1>Result</h1>

				<h3>Number of found samples: ${sampleForm.nbSamples}</h3>

				<c:if test="${sampleForm.nbSamples gt 0}">

					<button type="submit" class="btn btn-default btn-sm"
						name="downloadButton" value="exp_group">
						<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
						Download standard exp_group CSV
					</button>
					<button type="submit" class="btn btn-default btn-sm"
						name="downloadButton" value="parameters">
						<span class="glyphicon glyphicon-save" aria-hidden="true"></span>
						Download supplementary parameters CSV
					</button>

					<h3>Direct download link:</h3>

					<c:set var="url_exp_group"
						value="${globalApplicationRootUrl}/query/samples?${sampleForm.urlParameter}&document=exp_group" />
					<c:set var="url_parameters"
						value="${globalApplicationRootUrl}/query/samples?${sampleForm.urlParameter}&document=parameters" />
					<p>
						<a href="${url_exp_group}">${url_exp_group}</a>
					</p>
					<p>
						<a href="${url_parameters}">${url_parameters}</a>
					</p>

					<h3>Platforms: ${fn:length(sampleForm.listPlatforms)}</h3>
					<p>${sampleForm.listPlatforms}</p>

					<h3>Series: ${fn:length(sampleForm.listSeries)}</h3>
					<p>${sampleForm.listSeries}</p>

				</c:if>

			</c:if>

		</form:form>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>