<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName}</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

<!-- AJAX functions -->
<script src="<c:url value="/js/ajax.js" />"></script>

<script>

</script>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Find positions for a list of gene symbols</h1>

		<p class="lead">Submit a list of gene symbols and download
			corresponding positions</p>

		<p>Recognized symbols: Entrez Gene ID, current and previous gene
			symbols, probesets, accession numbers from UniGene, UCSC, Ensembl,
			Uniprot, ...</p>


	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>