<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - API query for platforms</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<div class=bs-docs-section>
			<h1>API query for platforms</h1>

			

			<h2>Query composition</h2>

			<div>
				<pre>${globalApplicationRootUrl}/query/<b>platforms?</b><var><b>key=value</b></var></pre>
			</div>

			<h2>Basic query parameters</h2>
			<%@ include file="tableParametersPlatforms.jsp"%>

			<h2>Examples</h2>

			<h3>Download all platforms in CSV format</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/platforms</pre>
			</div>
			<p>or equivalent</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/platforms?format=csv</pre>
			</div>

			<h3>Download all platforms in JSON format</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/platforms?format=json</pre>
			</div>

		</div>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>