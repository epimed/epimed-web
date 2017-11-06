<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Add or modify user</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Add or modify user</h1>

		<form:form class="form-horizontall" method="post"
			modelAttribute="user"
			action="${pageContext.request.contextPath}/admin/user/add">

			<spring:bind path="id">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Login / ID</label>
					<div class="col-10">
						<form:input path="id" type="text" class="form-control " id="id" />
						<form:errors path="id" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="firstName">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">First name</label>
					<div class="col-10">
						<form:input path="firstName" type="text" class="form-control "
							id="firstName" />
						<form:errors path="firstName" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="lastName">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Last name</label>
					<div class="col-10">
						<form:input path="lastName" type="text" class="form-control "
							id="lastName" />
						<form:errors path="lastName" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="organization">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Organization</label>
					<div class="col-10">
						<form:input path="organization" type="text" class="form-control "
							id="organization" />
						<form:errors path="organization" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="role">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Role</label>
					<div class="col-10">
						<form:input path="role" type="text" class="form-control "
							id="role" />
						<form:errors path="role" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="ip">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">IP</label>
					<div class="col-10">
						<form:input path="ip" type="text" class="form-control " id="ip" />
						<form:errors path="ip" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<div class="pull-right">
				<button type="submit" class="btn btn-primary" name="button"
					value="save">Save</button>

				<button class="btn btn-danger"
					onclick="location.href='${pageContext.request.contextPath}/admin/user/">Cancel</button>
			</div>

		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>