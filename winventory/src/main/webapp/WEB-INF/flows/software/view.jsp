<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Inventory</title>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="swBase.jsp" />
			<div class="col-md-8">



				<div class="main">
					<div class="boom">
						<h2 class="center">Application Info</h2>
					</div>

					<div class="padme">
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Software"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    Software software = (Software) request.getAttribute("software");

						    if (software != null) {
						%>


						<div class="media">
							<div class="media-left">
								<img class="media-object"
									src="${contextPath}/resources/images/barcode.gif">
							</div>
							<div class="media-body">
								<ul class="list-group">
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Key</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getKey()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Name</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getName()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Serial Number</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getSerialNo()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>License Key</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getLicenseKey()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Version</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getVersion()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Cost</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getCost()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Date Purchased</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getPurchasedDate()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Expiration Date</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getExpirationDate()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Description</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getDescription()%></p>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div>
							<%-- <c:if test="${userInfo.hasPermission.updateSoftware}"> --%>
								<a class="btn btn-default"
									href="edit?key=<%=software.getKey()%>" role="button">Edit</a>
							<%-- </c:if> --%>
						</div>
						<%
						    } else {
						%>
						<br>
						<div class="alert alert-danger" role="alert">Either no key
							was given, or the key does not exist</div>
						<%
						    }
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>

	<div class="top-header">
		<div class="center">
			<br>
			<h4>SimonComputing &copy; 2015</h4>
			<br>
		</div>
	</div>
</body>

</html>