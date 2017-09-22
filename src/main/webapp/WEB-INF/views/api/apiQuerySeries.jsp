<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - API query for series</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<div class=bs-docs-section>
			<h1>API query for series</h1>

			<h2>Query composition</h2>

			<div>
				<pre>${globalApplicationRootUrl}/query/<b>series?</b><var><b>key=value</b></var></pre>
			</div>
			
			<h2>Basic query parameters</h2>
			<%@ include file="tableParametersSeries.jsp"%>
			
			<h2>Examples</h2>
			
			<h3>Download all series in CSV format</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/series</pre>
			</div>
			<p>or equivalent</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/series?format=csv</pre>
			</div>
			
			<h3>Download all series in JSON format</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/series?format=json</pre>
			</div>

		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>