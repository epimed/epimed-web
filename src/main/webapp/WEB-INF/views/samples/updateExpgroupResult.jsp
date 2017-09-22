<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Experimental Grouping</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Add annotations to experimental grouping</h1>
		
		<c:if test="${success}">
			<p class="text-success">${message}</p>
			
			<c:if test="${not empty url}">
				<p>Download the updated experimental grouping in CSV format: <a href="${pageContext.request.contextPath}/expgroup/${url}">${pageContext.request.serverName}${pageContext.request.contextPath}/expgroup/${url}</a></p>
			</c:if>
			
		</c:if>
		
		<c:if test="${not success}">
			<p class="text-danger">${message}</p>
		</c:if>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>