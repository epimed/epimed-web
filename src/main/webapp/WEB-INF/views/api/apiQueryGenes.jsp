<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}- API query for gene annotations</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">


		<h1>API query for gene annotations</h1>
		
		<h2>Submit a query from R code</h2>
		
		<p>You can perform all the queries available on this web site directly from your R code.</p>
		
		<h3>Update a list of gene symbols</h3>
		
		<p>For this query your need to provide: symbols and taxid (use standard NCBI taxid). The query type is <b>update</b>. </p>
		
		<div>
<pre><code>library(httr)
url = "${globalApplicationRootUrl}/query/genes/<b>update</b>"
body = list(symbols="ATAD2, BRDT", taxid=9606)
job = POST(url, body = body, encode = "form")
# Job ID
jobid = content(job)$jobid
</code></pre>
		</div>
		
		<h3>Find probesets for a list of gene symbols</h3>
		
		<p>For this query your need to provide: symbols, platform and taxid (use standard NCBI taxid). The query type is <b>probeset</b>. </p>

		<div>
<pre><code>library(httr)
url = "${globalApplicationRootUrl}/query/genes/<b>probeset</b>"
body=list(symbols="ATAD2, BRDT", platform="GPL570", taxid=9606)
job = POST(url, body = body, encode = "form")
# Job ID
jobid = content(job)$jobid
</code></pre>
		</div>

		<h3>Find positions for a list of gene symbols</h3>
		
		<p>For this query your need to provide: symbols, assembly, positionType ("unique" or "all") and taxid (use standard NCBI taxid). The query type is <b>position</b>. </p>

		<div>
<pre><code>library(httr)
url = "${globalApplicationRootUrl}/query/genes/<b>position</b>"
body=list(symbols="ATAD2, BRDT", assembly="GRCh38", positionType="unique", taxid=9606)
job = POST(url, body = body, encode = "form")
# Job ID
jobid = content(job)$jobid
</code></pre>
		</div>
		
		<h3>Check the status of my query</h3>
		
		<p>If your list of symbols is long, the query may take some time. You can check the status of your query with the following command.</p>
		
		<div>
<pre><code>library(jsonlite)
url = "${globalApplicationRootUrl}/query/jobstatus?jobid=YOUR_JOB_ID"
job = fromJSON(url)
# Job ID
job$jobid
# Job status
job$status
</code></pre>
		</div>		
		
		<table class="table">
    		<thead>
      			<tr>
       				<th>Status</th>
        			<th>Meaning</th>
      			</tr>
    		</thead>
    		<tr>
    			<td>init</td>
    			<td>The job has been initialized. It is not yet terminated.</td>
    		</tr>
    		<tr>
    			<td>progress</td>
    			<td>The job is currently in progress.</td>
    		</tr>
    		<tr>
    			<td>success</td>
    			<td>The job is terminated with success.</td>
    		</tr>
    		<tr>
    			<td>error</td>
    			<td>An error has occurred during job execution.</td>
    		</tr>
		</table>
		
		
		<h2>Get the result of a query with Job ID</h2>

		<p>
			When you perform any search for gene annotations on this web site
			(for example, to update gene symbols or to find probsets) or via R code, you obtain
			a unique identification number for your request: your <b>Job ID</b>.
			The result of your request is stored in the database with your Job
			ID. Thus, you can load it again by using your personal Job ID.
		</p>

		<h3>Where to find Job ID?</h3>
		<p>Job ID is displayed on the page where you perform a search for
			gene annotations.</p>
		<p>If you submit a request via R code, your Job ID is available in <code>content(job)$jobid</code> variable.</p>

		<h3>Query composition</h3>

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

	
	
	</div>
	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>