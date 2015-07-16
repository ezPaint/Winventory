<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="top-header">
		<div class="media-right">
		<br> 
		<img src="${contextPath}/resources/images/win_logo.png" width="85" height="85"
		style="margin-left: 5px;">
		<br>
	</div>
	<div class="media-body">
		<h2>The WINventory</h2>
		<h5>Stop losing your items, <i>win them</i></h5>
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
				<!-- Only display labels in navbar if user has read permission for the associated objects -->
				<c:if test="${userInfo.hasPermission.readSoftware}">
				<li id="software"><a href="${contextPath}/software/results">Software</a></li>
				</c:if>
				<c:if test="${userInfo.hasPermission.readHardware}">
				<li id="hardware"><a href="${contextPath}/hardware/results"
					target="_top">Hardware</a></li>
				</c:if>
				<c:if test="${userInfo.hasPermission.readBarcode}">
				<li id="barcodes"><a href="${contextPath}/barcodes/barcode"
					target="_top">Barcode</a></li>
				</c:if>
				<c:if test="${userInfo.hasPermission.readUser}">
				<li id="users"><a href="${contextPath}/users/results" target="_top">Users</a></li>
				</c:if>
				<c:if test="${userInfo.hasPermission.readLocation}">
				<li id="location"><a href="${contextPath}/location/results-location" target="_top">Location</a></li>
				</c:if>
				<c:if test="${userInfo.hasPermission.readEvent }">
				<li id="event"><a href="${contextPath}/event/insert" target="_top">Events</a></li>
				</c:if>
			</ul>
			<ul class="nav navbar-nav navbar-right">

				<!-- Make a button that is the current User's username -->
				<c:if test="${not empty userInfo.username}">
					<li><a href="${contextPath}/users/view?key=${userInfo.key}" target="_top" class="btn btn-link">${userInfo.username}</a></li>
				</c:if>
				<!--Only display admin button if user is admin -->
				<c:if test="${userInfo.roleId == 1}">
					<li><a href="${contextPath}/admin" target="_top"
						class="btn btn-link"> <span class="glyphicon glyphicon-cog"
							aria-hidden="true"></span>
					</a></li>
				</c:if>

				<li><a href="${contextPath}/logout" target="_top"
					class="btn btn-link"> <span class="glyphicon glyphicon-log-out"
						aria-hidden="true"></span>
				</a></li>
			</ul>
		</div>
	</div>
</nav>

<script>
	tabsArray = ["software", "hardware", "barcodes", "users", "location", "event"]
	var x = 0;
	for (x; x < tabsArray.length; x++){
		if (location.href.includes(tabsArray[x])){
			document.getElementById(tabsArray[x]).className="list-group-element active";
		}
	}
</script>