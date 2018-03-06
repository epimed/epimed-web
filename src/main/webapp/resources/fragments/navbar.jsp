<!-- Static navbar -->
<nav class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">${globalApplicationName} <span class="glyphicon glyphicon-home" aria-hidden="true" title="${globalApplicationName}"></span></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<!-- <li class="active"><a href="#">Home</a></li>  -->
				<!--  <li><a href="#about">About</a></li> -->
				<!--  <li><a href="#contact">Contact</a></li> -->
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Clinical data <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/series">Download experimental grouping</a></li>
						<li><a href="${pageContext.request.contextPath}/series/add">Import a study from external database</a></li>
						<li><a href="${pageContext.request.contextPath}/samples">Search for samples in the database</a></li>
						<li><a href="${pageContext.request.contextPath}/updateExpgroup">Add annotations to experimental grouping</a></li>
					</ul>
				</li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">Gene annotations <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/genes/update">Update a list of gene symbols</a></li>
						<li><a href="${pageContext.request.contextPath}/genes/probeset">Find probesets for a list of gene symbols</a></li>
						<li><a href="${pageContext.request.contextPath}/genes/position">Find positions for a list of gene symbols</a></li>
					</ul>
				</li>
				
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">API for R users <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/apiExpGroup">Load experimental grouping with R</a></li>
						<li><a href="${pageContext.request.contextPath}/apiQuerySamples">API query for samples</a></li>
						<li><a href="${pageContext.request.contextPath}/apiQuerySeries">API query for series</a></li>
						<li><a href="${pageContext.request.contextPath}/apiQueryPlatforms">API query for platforms</a></li>
						<li><a href="${pageContext.request.contextPath}/apiQueryGenes">API query for gene annotations</a></li>
						<li><a href="${pageContext.request.contextPath}/apiQueryTissueSpecificGenes">API query for tissue-specific genes</a></li>
					</ul>
				</li>
				<li><a href="${pageContext.request.contextPath}/download">Download</a></li>
				
			</ul>

		</div>
		<!--/.nav-collapse -->
	</div>
</nav>
