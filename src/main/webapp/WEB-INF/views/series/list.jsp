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

		<form:form method="POST" commandName="form" action="series">

			<h1>Download experimental grouping</h1>
	
			<p class="lead">Select one or several studies:</p>

			<c:forEach var="series" items="${listSeries}">
				<div class="checkbox">
					<label><input type="checkbox" id="listIdSeries" name="listIdSeries"
						value="${series.id}"> ${series.id} (${series.nbSamples}) -
						${series.title} </label>
				</div>
			</c:forEach>

			<p></p>
			<input class="btn btn-primary" type="submit" value="Download Excel" />
			<span class="text-danger">${message}</span>
			<p></p>
		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>