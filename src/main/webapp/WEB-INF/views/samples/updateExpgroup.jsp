<%@ include file="/resources/fragments/jstlTags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${globalApplicationName} - Add annotations to experimental grouping</title>

<!-- Header -->
<%@ include file="/resources/fragments/header.jsp"%>

</head>

<body>

	<!-- Navigation bar -->
	<%@ include file="/resources/fragments/navbar.jsp"%>


	<!-- Container -->
	<div class="container">

		<h1>Add annotations to experimental grouping</h1>
		<div class="lead">Please upload annotations in a CSV file</div>

		<p>
			Accepted format: semi-colon
			<code>;</code>
			separated CSV file
			<code>*.csv</code>
		</p>

		<p>Your file must contain:
		<ul>
			<li>a header (one line)</li>
			<li>a column named <code>id_sample</code> with corresponding
				sample names (ex. GSM748053, GSM748054, etc)
			</li>
		</ul>
		All other columns should correspond to new annotations that you would
		like to add to the experimental grouping.
		<p>
			At least two columns are mandatory, one of which is
			<code>id_sample</code>
			.
		</p>


		<form:form method="post" action="updateExpgroup"
			commandName="fileUpload" enctype="multipart/form-data">

			<div>
				<label for="file" class="btn btn-primary">Upload file</label> <input
					type="file" name="file" id="file" accept=".csv"
					onchange="this.form.submit()" style="display: none" />
			</div>


			<p></p>
			<div>
				<p>
					<form:errors path="file" cssClass="text-danger" />
				</p>
			</div>


		</form:form>

		<p></p>


		<!-- Samples and annotations successfully found -->
		<c:if test="${not empty listAnnotations}">

			<p class="text-success">File "${file.name}" has been successfully uploaded.</p>

			<p class="lead">Please select the annotations to load in the
				database:</p>

			<form:form method="POST" commandName="form" action="importExpgroup">

				<c:forEach var="annotation" items="${listAnnotations}">
					<div class="checkbox">
						<label><input type="checkbox" id="listId" name="listId"
							value="${annotation}" checked />${annotation} <c:if
								test="${annotation!=mapHeaders[annotation]}"> (renamed from "${mapHeaders[annotation]}")</c:if>
						</label>
					</div>
				</c:forEach>

				<p></p>
				<input class="btn btn-primary" type="submit"
					value="Import selected annotations" />

			</form:form>
		</c:if>

	</div>

	<!-- Footer -->
	<%@ include file="/resources/fragments/footer.jsp"%>
</body>
</html>