<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}-JobHeaders</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<!-- Dismissible alert -->
		<%@ include file="../../inc/dismissibleAlert.jsp"%>

		<h1>Headers</h1>


		<p>
			<a href="${pageContext.request.contextPath}/admin/header/add">add
				header</a>
		</p>

		<c:forEach var="h" items="${listHeaders}">

			<div class="row">

				<div class="col-md-2">${h.id}</div>

				<div class="col-md-8">${h.header}</div>

				<div class="col-md-2">
					<a
						href="${pageContext.request.contextPath}/admin/header/update/${h.id}">update</a>
					<a
						href="${pageContext.request.contextPath}/admin/header/delete/${h.id}">delete</a>

				</div>

			</div>

		</c:forEach>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>