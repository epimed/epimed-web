<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Add or modify header</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Add or modify header</h1>

		<form:form class="form-horizontall" method="post"
			modelAttribute="jobHeader"
			action="${pageContext.request.contextPath}/admin/header/add">

			<spring:bind path="id">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">ID</label>
					<div class="col-10">
						<form:input path="id" type="text" class="form-control " id="id" />
						<form:errors path="id" class="control-label" />
					</div>
				</div>
			</spring:bind>

			<spring:bind path="header">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-2 control-label">Header</label>
					<div class="col-10">
						<form:input path="header" type="text" class="form-control "
							id="header" />
						<form:errors path="header" class="control-label" />
					</div>
				</div>
			</spring:bind>


			<div class="pull-right">
				<button type="submit" class="btn btn-primary" name="button"
					value="save">Save</button>

				<button class="btn btn-danger"
					onclick="location.href='${pageContext.request.contextPath}/admin/header/">Cancel</button>
			</div>

		</form:form>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>