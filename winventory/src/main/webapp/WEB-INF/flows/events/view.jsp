<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Event</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

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
	<jsp:include page="eventBase.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Event Info</h2>
					</div>
					<div class="padme">
						<c:choose>
							<c:when test="${validKey == true}">
								<%@ page import="com.simoncomputing.app.winventory.domain.Event"%>
								<%@ page import="java.util.ArrayList"%>

								<%
								    Event event = (Event) request.getAttribute("event");

								            if (event != null) {
								%>


								<div class="media">
									<div class="media-left"></div>
									<div class="media-body">
										<ul class="list-group" style="word-wrap: break-word;">

											<!-- Displays the event's key -->
											<li class="list-group-item row">
												<div class="col-md-3">
													<b>Key</b>
												</div>
												<div class="col-md-9">
													<p><%=event.getKey()%></p>
												</div>
											</li>

											<!-- Displays the event's description -->
											<li class="list-group-item row">
												<div class="col-md-3">
													<b>Description:</b>
												</div>
												<div class="col-md-9">
													<p><%=event.getDescription()%></p>
												</div>
											</li>

											<!--  Displays the date the event was created -->
											<li class="list-group-item row">
												<div class="col-md-3">
													<b>Date Created</b>
												</div>
												<div class="col-md-9">
													<p><%=event.getDateCreated()%></p>
												</div>
											</li>

											<!-- Displays the event's category -->
											<li class="list-group-item row">
												<div class="col-md-3">
													<b>Category</b>
												</div>
												<div class="col-md-9">
													<p><%=event.getCategory()%></p>
												</div>
											</li>

											<li class="list-group-item row">
												<div class="col-md-3">
													<b>Created By</b>
												</div>
												<div class="col-md-9">
													<p>
														<a
															href="${contextPath}/users/view?key=
											<%=event.getCreatorId()%>">
															${username}</a>
													</p>
												</div>
											</li>


											<%
											    }
											%>

											<c:if test="${hardware.getKey() != null}">
												<li class="list-group-item row">
													<div class="col-md-3">
														<b>Associated Hardware</b>
													</div>
													<div class="col-md-3">
														<a
															href="${contextPath}/hardware/view?key=
											${hardware.getKey()}">
															${hardware.getShortDescription()}</a>
													</div>
												</li>
											</c:if>

											<c:if test="${software.getKey() != null}">
												<li class="list-group-item row">
													<div class="col-md-3">
														<b>Associated Software</b>
													</div>
													<div class="col-md-3">
														<a
															href="${contextPath}/software/view?key=
											${software.getKey()}">
															${software.getName()}</a>
													</div>
												</li>
											</c:if>

											<c:if test="${location.getKey() != null}">
												<li class="list-group-item row">
													<div class="col-md-3">
														<b>Associated Location</b>
													</div>
													<div class="col-md-3">
														<a
															href="${contextPath}/location/view-location?key=
											${location.getKey()}">
															${location.getDescription()}</a>
													</div>
												</li>
											</c:if>

											<c:if test="${user.getKey() != null}">
												<li class="list-group-item row">
													<div class="col-md-3">
														<b>Associated User</b>
													</div>
													<div class="col-md-3">
														<a
															href="${contextPath}/users/view?key=
											${user.getKey()}">
															${user.getUsername()} </a>
													</div>
												</li>
											</c:if>
										</ul>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<h2>The requested event could not be found. Either the
									event id is incorrect or the event no longer exists.</h2>
							</c:otherwise>

						</c:choose>
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