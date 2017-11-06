<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Log</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Log</h1>

		<c:forEach var="log" items="${logs}">
			<div class="row">

				<!-- Date -->
				<div class="col-md-2">
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss.SSS"
						value="${log.lastActivity}" />
				</div>

				<!-- IP -->
				<div class="col-md-3"><a href="${pageContext.request.contextPath}/admin/log/${log.singleIp}/">${log.ip}</a></div>

				<!-- User -->
				<div class="col-md-2">
					<c:if test="${not empty log.user}"><a href="${pageContext.request.contextPath}/admin/log/${log.user.id}">${log.user.firstName} ${log.user.lastName}</a></c:if>
				</div>

				<!-- Method -->
				<div class="col-md-1">${log.method}</div>

				<!-- URL -->
				<div class="col-md-3">${log.url}</div>
				
				<!-- URL -->
				<div class="col-md-1"><a href="${pageContext.request.contextPath}/admin/log/delete/${log.id}">delete</a></div>

			</div>

			<!-- Parameter -->
			<c:if test="${not empty log.parameter}">
				<div class="row">
					<div class="col-md-10 col-md-offset-2 alert alert-info">${log.parameter}</div>
				</div>
			</c:if>

			<!-- Comment -->
			<c:if test="${not empty log.comment}">
				<div class="row">
					<c:choose>
						<c:when
							test="${fn:containsIgnoreCase(log.comment, 'error') or fn:containsIgnoreCase(log.comment, 'exception')}">
							<div class="col-md-10 col-md-offset-2 alert alert-danger">${log.comment}</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-10 col-md-offset-2 alert alert-success">${log.comment}</div>
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