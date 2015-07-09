<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="top-header">
	<div class="media-right">
		<br> <img src="${contextPath}/resources/images/SC-Logo.png"
			height="50" width="50" alt="what">
	</div>
	<div class="media-body">
		<br>
		<h4>The Winventory</h4>
		<br>
	</div>
</div>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse" id="navbar">
			<ul class="nav navbar-nav">
				<!-- Only display labels in navbar if user has read permission for the associate objects -->
				<%-- <c:if test="${userInfo.hasPermission.readDatabases}"> --%>
				<li id="databases"><a href="#">Databases</a></li>
				<%-- </c:if>
				<c:if test="${userInfo.hasPermission.readServices}"> --%>
				<li id="services"><a href="#">Services</a></li>
				<%-- </c:if>
				<c:if test="${userInfo.hasPermission.readSoftware}"> --%>
				<li id="software"><a href="${contextPath}/software">Software</a></li>
				<%-- </c:if>
				<c:if test="${userInfo.hasPermission.readServers}"> --%>
				<li id="servers"><a href="#">Servers</a></li>
				<%-- </c:if>
				<c:if test="${userInfo.hasPermission.readHardware}"> --%>
				<li id="hardware"><a href="${contextPath}/hardware/results"
					target="_top">Hardware</a></li>
				<%-- </c:if>
				<c:if test="${userInfo.hasPermission.readBarcode}"> --%>
				<li id="barcodes"><a href="${contextPath}/barcodes/barcode"
					target="_top">Barcode</a></li>
				<%-- </c:if> --%>
				<%-- <c:if test="${userInfo.hasPermission.readUser}"> --%>
				<li id="users"><a href="${contextPath}/users/results" target="_top">Users</a></li>
				<%-- </c:if> --%>
			</ul>
			<ul class="nav navbar-nav navbar-right">

				<!-- Make a button that is the current User's username -->
				<c:if test="${not empty userInfo.username}">
					<li><a href="#" target="_top" class="btn btn-link">${userInfo.username}</a></li>
				</c:if>
				<!--Only display admin button if user is admin -->
				<c:if test="${userInfo.roleId == 1}">
					<li><a href="${contextPath}/admin" target="_top"
						class="btn btn-link"> <span class="glyphicon glyphicon-user"
							aria-hidden="true"></span>
					</a></li>
				</c:if>

				<li><a href="#" target="_top" class="btn btn-link"> <span
						class="glyphicon glyphicon-cog" aria-hidden="true"></span>
				</a></li>
				<li><a href="${contextPath}/logout" target="_top"
					class="btn btn-link"> <span class="glyphicon glyphicon-log-out"
						aria-hidden="true"></span>
				</a></li>
			</ul>
		</div>
	</div>
</nav>

<script>
	pathArray = location.href.replace('#', '').split('/');
	document.getElementById(pathArray[4]).className = "active";
</script>