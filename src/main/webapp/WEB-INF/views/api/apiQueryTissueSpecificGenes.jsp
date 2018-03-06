<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - API query for tissue specific genes</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<h1>API query for tissue specific genes</h1>

		
	<p>To query tissue-specific genes your need to provide:</p>
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
	<p>Humain testis-specific genes as defined by EpiMed from RNA-seq data</p>
	<p><a href="${globalApplicationRootUrl}/query/genes?annotations=EpiMed_restricted_1_testis_adult&taxid=9606">${globalApplicationRootUrl}/query/genes?annotations=EpiMed_restricted_1_testis_adult&taxid=9606</a></p>
<div>
<pre><code>url = "${globalApplicationRootUrl}/query/genes?annotations=EpiMed_restricted_1_testis_adult&taxid=9606"
df = read.csv2(url, header=TRUE, sep=";")
</code></pre>
</div>
	
	</div>
	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>