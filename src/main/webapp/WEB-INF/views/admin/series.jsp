<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Series</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Series</h1>

		<c:forEach var="series" items="${listSeries}">
			<div class="row">

				<!-- Date -->
				<div class="col-md-2">
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss.SSS"
						value="${series.importDate}" />
				</div>

				<!-- IP -->
				
				<div class="col-md-2"><strong>${series.id}</strong></div>

				<!-- Nb samples -->
				<div class="col-md-1">${series.nbSamples}</div>

				<!-- Platforms -->
				<div class="col-md-5">${series.platforms}</div>

				<!-- Access -->
				<div class="col-md-1">${series.access}</div>
				
				<!-- Status -->
				<div class="col-md-1">${series.status}</div>


			</div>

			<!-- Delete button and submitted date -->
			<div class="row">
				<div class="col-md-2">
					<a
						href="${pageContext.request.contextPath}/admin/series/delete/${series.id}">delete</a>
				</div>
				<div class="col-md-10">imported by ${series.user.firstName} ${series.user.lastName} ${series.ip}</div>
			</div>

			<!-- Parameter -->

			<div class="row">
				<div class="col-md-10 col-md-offset-2 alert alert-info">${series.title}</div>
			</div>



		</c:forEach>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>