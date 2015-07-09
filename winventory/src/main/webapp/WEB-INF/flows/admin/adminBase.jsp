<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="col-md-2">
	<div class="list-group">
		<a id="results" href="${contextPath}/admin/setSmtp" class="list-group-item"> <span
			class="glyphicon glyphicon-envelope" aria-hidden="true"> SMTP</span></a>
		<a id="results" href="${contextPath}/admin/setGoogleClient" class="list-group-item"> <span
			class="glyphicon glyphicon-globe" aria-hidden="true"> Google Client</span></a>
		<!--  <a
			id="storage" href="storage" class="list-group-item"> <span
			class="glyphicon glyphicon-briefcase" aria-hidden="true">
				Storage</span></a> -->
		<c:if test="${userInfo.hasPermission.createHardware}">
			<a id="insert" href="${contextPath }/hardware/insert" class="list-group-item"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> Add
					Hardware</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createSoftware}">
			<a id="insert" href="${contextPath }/software/insert" class="list-group-item"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> Add
					Application</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createUser}">
			<a id="insert" href="${contextPath }/users/insert" class="list-group-item"> <span
				class="glyphicon glyphicon-plus" aria-hidden="true"> Add User</span></a>
		</c:if>
		<!-- <a id="advanced-search" href="advanced-search" class="list-group-item">
			<span class="glyphicon glyphicon-search" aria-hidden="true">
				Search</span>
		</a> -->
	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[5]).className = "list-group-item active";
</script>