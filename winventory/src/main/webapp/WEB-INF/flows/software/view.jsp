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
					
					    <!-- Get the information of the software object selected by the user to view. -->
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Software"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    Software software = (Software) request.getAttribute("software");

						    if (software != null) {
						%>


						<div class="col-md-4">
							<img src="${contextPath}/getBarcodeImage?key=${software.getKey()}&table=3"
								class="img img-responsive">
							</div>
							<div class="media-body">
								<ul class="list-group">
								
								    <!-- Displays the software's key -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Key</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getKey()%></p>
										</div>
									</li>
									
									<!-- Displays the software's name -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Name</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getName()%></p>
										</div>
									</li>
									
									<!--  Displays the software's serial number -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Serial Number</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getSerialNo()%></p>
										</div>
									</li>
									
									<!-- Displays the software's license key -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>License Key</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getLicenseKey()%></p>
										</div>
									</li>
									
									<!-- Displays the software's version -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Version</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getVersion()%></p>
										</div>
									</li>
									
									<!-- Displays the software's cost -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Cost</b>
										</div>
										<div class="col-md-9">
											<p><%= String.format("%.2f", software.getCost()) %></p>
										</div>
									</li>
									
									<!-- Displays the software's purchase date -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Date Purchased</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getPurchasedDate()%></p>
										</div>
									</li>
									
									<!-- Displays the software's expiration date -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Expiration Date</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getExpirationDate()%></p>
										</div>
									</li>
									
									<!-- Displays the software's description -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Description</b>
										</div>
										<div class="col-md-9">
											<p><%=software.getDescription()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Barcode</b>
										</div>
										<div class="col-md-9">
											<p><%=Barcoder.getBarcode(software)%></p>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div>
						    <!-- Button for the user to edit/delete the software object. -->
							<%-- <c:if test="${userInfo.hasPermission.updateSoftware}"> --%>
								<a class="btn btn-default"
									href="edit?key=<%=software.getKey()%>" role="button">Edit</a>
								<a class="btn btn-default" href="${contextPath}/software">Cancel</a>
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
						<%@ page import="com.simoncomputing.app.winventory.domain.Software"%>
						<%@ page import="com.simoncomputing.app.winventory.util.Barcoder"%>
						<jsp:include page="/WEB-INF/includes/events.jsp" />
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>