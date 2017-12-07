<form:form class="form-inline pull-right" method="GET"
	modelAttribute="form" action="${pageContext.request.contextPath}/series">

	<div class="form-group">
		<input type="text" class="form-control" name="text" id="text"
			value="${text}" size=50 placeholder="Accession ID, terms">
	</div>

	<div class="form-group">
		<button type="submit" class="btn btn-default" name="buttonSearch"
			value="Search">
			<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
			Search
		</button>
	</div>
</form:form>