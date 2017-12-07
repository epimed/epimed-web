<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Download experimental grouping</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Download experimental grouping</h1>

		<!-- Buttons to import and search series -->
		<div>
			<%@ include file="buttonImportSeries.jsp"%>
			<%@ include file="buttonSearchSeries.jsp"%>
		</div>

		<p style="margin-bottom: 0.5cm;"></p>


		<p class="lead">
			Found ${fn:length(listSeries)} studies.
			<c:if test="${not empty listSeries}">
				Please select one or several studies and click on download button.
			</c:if>
		</p>

		<form:form method="POST" modelAttribute="form" action="series">


			<c:forEach var="series" items="${listSeries}">


				<div class="checkbox">
					<label><input type="checkbox" id="listIdSeries"
						name="listIdSeries" value="${series.id}"> ${series.id}
						(${series.nbSamples}) - ${series.title} </label>

					<c:if
						test="${not empty series.status and series.status=='imported' }">
						<span class="text-danger"><i> - WARNING! This study has not yet
								been analyzed, only original parameters are available</i></span>
					</c:if>

					<c:if test="${not empty series.status and (series.status=='error' or series.status=='incomplete')}">
						<span class="text-danger"><i> - WARNING! This study is incomplete, some data can be missing</i></span>
					</c:if>

				</div>

			</c:forEach>

			<p></p>
			
			<c:if test="${not empty  listSeries}">
				<input class="btn btn-primary" type="submit" value="Download Excel" />
			</c:if>
			<span class="text-danger">${message}</span>
			<p></p>
		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>