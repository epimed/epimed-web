<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Home</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

<!-- Chart -->
<%@ include file="/resources/charts/pieChart.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<div class="starter-template">
			<h1>Welcome to ${globalApplicationName}</h1>
			<p class="lead">The database contains ${nbSamples} samples in
				${nbSeries} studies.</p>
		</div>

		<div id="${chartId}" style="width: 900px; height: 400px;"></div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>