<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- Software's side/navigation bar -->
<div class="col-md-2">
	<div class="list-group">
		<a id="results" href="${contextPath}/software/results" class="list-group-item">
			<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				List</span>
		</a>
		<c:if test="${userInfo.hasPermission.createSoftware}">
		<a id="insert" href="${contextPath}/software/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add</span>
			</a>
		</c:if>
		<a id="advancedsearch" href="${contextPath}/software/advancedsearch"
            class="list-group-item"> <span class="glyphicon glyphicon-search"
            aria-hidden="true"> Search</span>
        </a>
	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[5]).className = "list-group-item active";
</script>