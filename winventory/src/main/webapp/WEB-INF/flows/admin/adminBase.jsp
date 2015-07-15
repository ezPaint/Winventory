<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="col-md-2">
	<div class="list-group">
		<c:if test="${userInfo.roleId == 1 }">
			<a id="results" href="${contextPath}/admin/condition"
				class="list-group-item"> <span class="glyphicon glyphicon-cog"
				aria-hidden="true"> Conditions</span></a>
			<a id="insert" href="${contextPath }/admin/role"
				class="list-group-item"> <span class="glyphicon glyphicon-lock"
				aria-hidden="true"> Roles & Permissions</span></a>
			<a id="results" href="${contextPath}/admin/setSmtp"
				class="list-group-item"> <span
				class="glyphicon glyphicon-envelope" aria-hidden="true"> SMTP</span></a>
			<a id="results" href="${contextPath}/admin/setGoogleClient"
				class="list-group-item"> <span class="glyphicon glyphicon-globe"
				aria-hidden="true"> Google Client</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createHardware}">
			<a id="insert" href="${contextPath }/hardware/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add Hardware</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createSoftware}">
			<a id="insert" href="${contextPath }/software/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add Software</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createUser}">
			<a id="insert" href="${contextPath }/users/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add User</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createEvent}">
			<a id="insert" href="${contextPath }/event/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add Event</span></a>
		</c:if>
		<c:if test="${userInfo.hasPermission.createLocation}">
			<a id="insert" href="${contextPath }/location/insert-location"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add Location</span></a>
		</c:if>
	</div>
</div>

<script>
	pathArray = location.href.replace('#', '').replace('?', '/').split('/');
	document.getElementById(pathArray[4]).className = "list-group-item active";
</script>