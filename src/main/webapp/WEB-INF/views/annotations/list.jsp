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
	<div class="container-fluid">

		<h1>List of annotations</h1>

		<table class="table table-condensed">
			<thead>
				<tr>
					<th>annotation</th>
					<th>source</th>
					<th>dataset</th>
					<th>subtype</th>
					<th>level</th>
					<th>tissue</th>
					<th>stage</th>
					<th>tissue_group_level1</th>
					<th>tissue_group_level2</th>
					<th>tissue_group_level3</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="annotation" items="${listAnnotations}">
					<tr>
						<td>${annotation.uid}</td>
						<td>${annotation.source}</td>
						<td>${annotation.dataset}</td>
						<td>${annotation.subtype}</td>
						<td>${annotation.level}</td>
						<td>${annotation.tissue}</td>
						<td>${annotation.tissueStage}</td>
						<td>${annotation.tissueGroupLevel1}</td>
						<td>${annotation.tissueGroupLevel2}</td>
						<td>${annotation.tissueGroupLevel3}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>



	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>