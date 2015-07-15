<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-2">
	<div class="list-group">
		<c:if test="${userInfo.hasPermission.readEvent}">
			<a id="results" href="results" class="list-group-item"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> All</span>
			</a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createEvent}">
			<a id="insert" href="insert" class="list-group-item"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> Add</span>
			</a>
		</c:if>

	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[5]).className = "list-group-item active";
</script>