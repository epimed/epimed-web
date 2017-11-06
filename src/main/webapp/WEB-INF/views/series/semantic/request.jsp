<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Request for data import</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">
		<h1>Request for data import</h1>

		<p style="margin-bottom: 0.5cm;"></p>

		<div class="panel panel-success">
			<div class="panel-heading">New request</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-2">Database</div>
					<div class="col-md-10">${database}</div>
				</div>

				<div class="row">
					<div class="col-md-2">Accession ID</div>
					<div class="col-md-10">${idSeries}</div>
				</div>
			</div>
			<div class="panel-footer">Your request has been sent to data manager.</div>
		</div>




	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>