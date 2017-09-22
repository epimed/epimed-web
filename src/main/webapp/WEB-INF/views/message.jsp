<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Message</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<div class="starter-template">
			<h1>Message</h1>
			<p>${message}</p>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>