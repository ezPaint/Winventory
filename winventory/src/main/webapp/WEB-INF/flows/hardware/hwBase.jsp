<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-2">
	<div class="list-group">
		<c:if test="${userInfo.hasPermission.readHardware}">
			<a id="results" href="results" class="list-group-item"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> All</span></a>
			<a id="owned" href="owned" class="list-group-item"> <span
				class="glyphicon glyphicon-user" aria-hidden="true"> Owned</span>
			</a>
			<a id="storage" href="storage" class="list-group-item"> <span
				class="glyphicon glyphicon-home" aria-hidden="true"> Stored</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createHardware}">
			<a id="insert" href="insert" class="list-group-item"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> Add</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.readHardware}">
			<a id="advanced-search" href="advanced-search"
				class="list-group-item"> <span
				class="glyphicon glyphicon-search" aria-hidden="true"> Search</span>
			</a>
		</c:if>
	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[5]).className = "list-group-item active";
</script>