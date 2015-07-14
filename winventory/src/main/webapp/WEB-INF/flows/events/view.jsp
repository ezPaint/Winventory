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
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Event Info</h2>
					</div>
					<div class="padme">
					
					 
						<%@ page
							import="com.simoncomputing.app.winventory.domain.Event"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    Event event = (Event) request.getAttribute("event");

						    if (event != null) {
						%>


						<div class="media">
							<div class="media-left">
							</div>
							<div class="media-body">
								<ul class="list-group">
								
								    <!-- Displays the software's key -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Key</b>
										</div>
										<div class="col-md-9">
											<p><%=event.getKey()%></p>
										</div>
									</li>
									
									<!-- Displays the software's name -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Description:</b>
										</div>
										<div class="col-md-9">
											<p><%=event.getDescription()%></p>
										</div>
									</li>
									
									<!--  Displays the software's serial number -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Date Created</b>
										</div>
										<div class="col-md-9">
											<p><%=event.getDateCreated()%></p>
										</div>
									</li>
									
									<!-- Displays the software's license key -->
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Category</b>
										</div>
										<div class="col-md-9">
											<p><%=event.getCategory()%></p>
										</div>
									</li>
									
									
							<% }%> 
							
							<c:if test="${hardware != null && hardware.size() > 0}">
								<li class="list-group-item row">
										<div class="col-md-3">
											<b>Associated Hardware</b>
										</div>
										<div class="col-md-3">
											
										
										<b>
										<c:forEach var="i" begin="0" end="${hardware.size() - 1}">
											
										
											<a href="${contextPath}/hardware/view?key=
											${hardware.get(i).getKey()}">
											${hardware.get(i).getKey()}</a>
											<c:if test="${i != hardware.size() - 1}">
											,
											</c:if>
										
										</c:forEach>
										</b>
										</div>
										
										
										
								</li>
							</c:if>
							
							
							<c:if test="${software != null && software.size() > 0}">
								<li class="list-group-item row">
						
										<div class="col-md-3">
											<b>Associated Software</b>
										</div>
										<div class="col-md-3">
											
										
										<b>
										<c:forEach var="i" begin="0" end="${software.size() - 1}">
											<a href="${contextPath}/software/view?key=
											${software.get(i).getKey()}">
											${software.get(i).getKey()}</a>
											<c:if test="${i != software.size() - 1}">
											,
											</c:if>
										
										</c:forEach>
										</b>
										</div>
								</li>
							</c:if>
								</ul>
							</div>
						</div>
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