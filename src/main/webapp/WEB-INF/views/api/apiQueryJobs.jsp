<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}-APIqueryfor gene annotations</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<h1>API query for gene annotations</h1>

		<p>
			When you perform any search for gene annotations on this web site
			(for example, to update gene symbols or to find probsets), you obtain
			a unique identification number for your request: your <b>Job ID</b>.
			The result of your request is stored in the database with your Job
			ID. Thus, you can load it again by using your personal Job ID.
		</p>

		<h2>Where to find my Job ID?</h2>
		<p>Job ID is displayed on the page where you perform a search for
			gene annotations.</p>

		<h2>Query composition</h2>

		<div>
			<pre>${globalApplicationRootUrl}/query/jobs?jobid=<var>YOUR_JOB_ID</var></pre>
		</div>

		<p>The query will generate a CSV file that you can download or
			directly import into your R code</p>
		
		<div>
<pre><code>url = "${globalApplicationRootUrl}/query/jobs?jobid=YOUR_JOB_ID"
df = read.csv2(url, header=TRUE, sep=";")
</code></pre>
		</div>

		<p>For gene positions, it is possible to organize columns accordingly to standard BED format. Just add <code>format=bed</code> to your request: </p>
		
		<div>
<pre><code>url = "${globalApplicationRootUrl}/query/jobs?jobid=YOUR_JOB_ID&format=bed"
df = read.csv2(url, header=TRUE, sep=";")
</code></pre>
		</div>

	<h2>Query for tissue-specific genes</h2>
	<p>To query tissue-specific gene your need to know:</p>
	<ul>
		<li>Gene annotation corresponding to your search (ex. RNASEQ_restricted_testis). 
		Please, check a <a href="${pageContext.request.contextPath}/annotations">list of currently available annotations</a>.</li>
		<li>Organism taxid: 9606 for Homo sapiens, 10090 for Mus musculus</li>
	</ul>
	
	
	<p>The query will generate a CSV file that you can download or directly import into your R code</p>
	
	<div>
<pre><code>url = "${globalApplicationRootUrl}/query/genes?annotations=ANNOTATION&taxid=TAXID"
df = read.csv2(url, header=TRUE, sep=";")
</code></pre>
	</div>
	
	<h3>Example</h3>
	<p>Humain testis-specific genes as defined by RNA-seq</p>
	<p><a href="${globalApplicationRootUrl}/query/genes?annotations=RNASEQ_restricted_testis&taxid=9606">${globalApplicationRootUrl}/query/genes?annotations=RNASEQ_restricted_testis&taxid=9606</a></p>
<div>
<pre><code>url = "${globalApplicationRootUrl}/query/genes?annotations=RNASEQ_restricted_testis&taxid=9606"
df = read.csv2(url, header=TRUE, sep=";")
</code></pre>
</div>
	
	</div>
	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>