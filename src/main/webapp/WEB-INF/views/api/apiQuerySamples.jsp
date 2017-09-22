<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - API query for samples</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<div class=bs-docs-section>
			<h1>API query for samples</h1>
			<p>EpiMed provides an API that allows programmatic access to
				available clinical data.</p>

			<h2>Query composition</h2>

			<div>
				<pre>${globalApplicationRootUrl}/query/<b>samples?</b><var><b>key=value</b></var></pre>
			</div>

			<ul>
				<li><var>
						<b>key</b>
					</var> corresponds to a column name in the experimental grouping</li>
				<li><var>
						<b>value</b>
					</var> corresponds to the value of this column</li>
			</ul>

			<p>
				Several "key-value" expressions can be chained with
				<code>&</code>
				symbol:
			</p>

			<div>
				<pre>${globalApplicationRootUrl}/query/<b>samples?</b><var><b>key1=value1&key2=value2</b></var></pre>
			</div>

			<p>
				Several values can be added with
				<code>,</code>
				symbol:
			</p>

			<div>
				<pre>${globalApplicationRootUrl}/query/<b>samples?</b><var><b>key=value1,value2</b></var></pre>
			</div>

			<p>
			
			<h2>Basic query parameters</h2>
			<%@ include file="tableParametersSamples.jsp"%>
			
			<h2>Examples</h2>
			
			<h3>Series</h3>
			<p>The following link generates the experimental grouping of GSE30219 series in CSV format:</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?series=GSE30219&document=exp_group&format=csv</pre>
			</div>
			
			<p>"document=exp_group" and "format=csv" are optional as they are default values. 
			The above query can be reduced to: </p>
			
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?series=GSE30219</pre>
			</div>
			
			<h3>Platforms</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?id_platform=GPL570,GPL96</pre>
			</div>
			
			<h3>Tissue status</h3>
			<p>Normal tissues:</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?id_tissue_status=1</pre>
			</div>
			
			<h3>Tissue stage</h3>
			<p>Adult tissues:</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?id_tissue_stage=1</pre>
			</div>
			
			<h3>Age</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?series=GSE30219&age_min=50&age_max=60</pre>
			</div>
			
			<h3>Survival</h3>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?id_tissue_status=3&survival=true</pre>
			</div>
			<p>or equivalent</p>
			<div>
				<pre>${globalApplicationRootUrl}/query/samples?id_tissue_status=3&survival</pre>
			</div>
		</div>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>