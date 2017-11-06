<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}-Jobs</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Jobs</h1>

		<c:forEach var="job" items="${jobs}">
			<div class="row">

				<!-- Date -->
				<div class="col-md-2">
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss.SSS"
						value="${job.lastActivity}" />
				</div>

				<!-- IP -->
				<div class="col-md-3">${job.ip}</div>

				<!-- User -->
				<div class="col-md-2">
					<c:if test="${not empty job.user}"><a href="${pageContext.request.contextPath}/admin/job/${job.user.id}">${job.user.firstName} ${job.user.lastName} (${job.user.id})</a></c:if>
				</div>

				<!-- Job ID -->
				<div class="col-md-2"><a href="${pageContext.request.contextPath}/query/jobs?jobid=${job.id}&format=csv">${job.id}</a></div>

				<!-- Nb Elements current / total -->
				<div class="col-md-2"><a href="${pageContext.request.contextPath}/admin/job/${job.type}">${job.type}</a> ${job.current}/${job.total}</div>

				<!-- Status -->
				<c:choose>
					<c:when test="${job.status=='error'}">
						<div class="col-md-1">
							<span class="text-danger">${job.status}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-1">${job.status}</div>
					</c:otherwise>
				</c:choose>


			</div>

			<!-- Delete button and submitted date -->
			<div class="row">
				<div class="col-md-2">
					<a
						href="${pageContext.request.contextPath}/admin/job/delete/${job.id}">delete</a>
				</div>
				<div class="col-md-10">
					Job submitted on
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss.SSS"
						value="${job.submissionDate}" />
				</div>
			</div>

			<!-- Parameter -->
			<c:if test="${not empty job.elements}">
				<div class="row">
					<div class="col-md-10 col-md-offset-2 alert alert-info">${job.mainObject} ${job.elements}</div>
				</div>
			</c:if>

			<!-- Comment -->
			<c:if test="${not empty job.comment}">
				<div class="row">
					<c:choose>
						<c:when test="${job.status=='error'}">
							<div class="col-md-10 col-md-offset-2 alert alert-danger">${job.comment}</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-10 col-md-offset-2 alert alert-success">${job.comment}</div>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>



		</c:forEach>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>