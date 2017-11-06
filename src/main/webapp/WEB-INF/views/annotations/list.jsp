<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}-Annotations</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>List of annotations</h1>

		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Annotation</th>
					<th>Source</th>
					<th>Type</th>
					<th>Parameter</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="annotation" items="${listAnnotations}">
					<tr>
						<td>${annotation.uid}</td>
						<td>${annotation.source}</td>
						<td>${annotation.type}</td>
						<td>${annotation.parameter}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>