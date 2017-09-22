<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Load experimental grouping with R</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<div class=bs-docs-section>
			<h1>Load experimental grouping with R</h1>
			<p>EpiMed provides an API that allows programmatic access to
				available clinical data.</p>
			<p>You can download an experimental grouping of one or several studies with a single command:</p>
			<div>
				<pre>${globalApplicationRootUrl}/<b>expgroup</b>/<var><b>list_of_GSE_numbers</b></var></pre>
			</div>
			<p>This command will generate an experimental grouping for
				requested GSE numbers in CSV format.</p>
				
			<p>Additional parameters can also be downloaded as follows:</p>
			<div>
				<pre>${globalApplicationRootUrl}/<b>parameters</b>/<var><b>list_of_GSE_numbers</b></var></pre>
			</div>
			
			<h3>Using with R</h3>
			<p>This code can be directly integrated into your R script:</p>
			<div>
				<pre><code>url = "${globalApplicationRootUrl}/expgroup/GSE30219"
df = read.csv2(url, header=TRUE, sep=";", stringsAsFactors=FALSE, dec=".", na.strings="")
df$relapsed = as.logical(df$relapsed)
df$dead = as.logical(df$dead)
</code></pre>
			</div>
			
			<h3>Examples</h3>

			<p>Download an experimental grouping for one study in CSV format:</p>

			<div class="panel panel-default">
				<div class="panel-body">
					<a href="${globalApplicationRootUrl}/expgroup/GSE2109">${globalApplicationRootUrl}/expgroup/GSE2109</a>
				</div>
			</div>
			
			<p>Download additional parameters:</p>

			<div class="panel panel-default">
				<div class="panel-body">
					<a href="${globalApplicationRootUrl}/parameters/GSE2109">${globalApplicationRootUrl}/parameters/GSE2109</a>
				</div>
			</div>

			<p>
				To download several studies, separate them by
				<code>,</code>
				or
				<code>space</code>
				:
			</p>

			<div class="panel panel-default">
				<div class="panel-body">
					<a
						href="${globalApplicationRootUrl}/expgroup/GSE9119,GSE11092,GSE9440">${globalApplicationRootUrl}/expgroup/GSE9119,GSE11092,GSE9440</a>
						<br />
						<a href="${globalApplicationRootUrl}/expgroup/GSE9119 GSE11092 GSE9440">${globalApplicationRootUrl}/expgroup/GSE9119
						GSE11092 GSE9440</a>
				</div>
			</div>
			
			<p>EpiMed-specific studies can also be used:</p>
			<div class="panel panel-default">
				<div class="panel-body">
					<a
						href="${globalApplicationRootUrl}/expgroup/PRO12">${globalApplicationRootUrl}/expgroup/PRO12</a>	
				</div>
			</div>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>