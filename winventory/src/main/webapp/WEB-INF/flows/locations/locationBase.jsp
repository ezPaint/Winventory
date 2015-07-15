<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-2">
	<div class="list-group">
		<h3>Locations:</h3>
		<c:if test="${userInfo.hasPermission.readLocation}">
			<a id="results-location" href="results-location"
				class="list-group-item"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> All</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createLocation}">
			<a id="insert-location" href="insert-location"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add</span></a>
		</c:if>
		<br>
		<h3>Addresses:</h3>
		<a id="results-address" href="results-address" class="list-group-item">
			<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				All</span>
		</a>
		<c:if test="${userInfo.hasPermission.createAddress}">
			<a id="insert-address" href="insert-address" class="list-group-item">
				<span class="glyphicon glyphicon-plus" aria-hidden="true">
					Add</span>
			</a>
		</c:if>
	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[5]).className = "list-group-item active";
</script>