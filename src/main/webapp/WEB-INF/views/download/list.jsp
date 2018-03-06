<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}Download</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Download</h1>

		<c:forEach var="category" items="${mapDownload}">
			<h2>${category.key}</h2>

			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>File</th>
						<th>Format</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="download" items="${category.value}">
						<tr>
							<td><p><strong>${download.title}</strong></p>
							<p>${download.description}</p>
							<p><a href="${download.uri}">${download.name}</a></p></td>
							<td>${download.format}</td>
							<td>${download.dateCreated}</td>
						</tr>



					</c:forEach>

				</tbody>
			</table>

		</c:forEach>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>