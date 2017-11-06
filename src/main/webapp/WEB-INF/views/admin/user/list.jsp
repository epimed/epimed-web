<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}-Users</title>

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

		<h1>Users</h1>

		<p>
			<a href="${pageContext.request.contextPath}/admin/user/add">add
				user</a>
		</p>

		<c:forEach var="user" items="${users}">
			<div class="row">

				<div class="col-md-2">${user.id}</div>

				<div class="col-md-2"><strong>${user.firstName}</strong>&nbsp;<strong>${user.lastName}</strong></div>

				<div class="col-md-1">${user.organization}</div>

				<div class="col-md-1">${user.role}</div>

				<div class="col-md-4">${user.ip}</div>

				<div class="col-md-2">
					<a
						href="${pageContext.request.contextPath}/admin/user/update/${user.id}">update</a>
					<a
						href="${pageContext.request.contextPath}/admin/user/delete/${user.id}">delete</a>

				</div>

			</div>



			<!-- Log -->

			<div class="row">
				<div class="col-md-3 col-md-offset-2">
					<a href="${pageContext.request.contextPath}/admin/job/${user.id}">show
						all jobs of this user</a>
				</div>
				<div class="col-md-7">
					<a
						href="${pageContext.request.contextPath}/admin/job/delete/${user.id}">delete
						all jobs of this user</a>
				</div>
			</div>


			<!-- Logs-->

			<div class="row">
				<div class="col-md-3 col-md-offset-2">
					<a href="${pageContext.request.contextPath}/admin/log/${user.id}">show
						all logs of this user</a>
				</div>
				<div class="col-md-7">
					<a
						href="${pageContext.request.contextPath}/admin/log/delete/${user.id}">delete
						all logs of this user</a>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">&nbsp;</div>
			</div>


		</c:forEach>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>