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
</head>
<jsp:include page="/base.jsp"></jsp:include>
<jsp:include page="/WEB-INF/flows/events/eventBase.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-8">
			<div class="main">
				<div class="boom">
					<h2 class="center">Events</h2>
				</div>
						<jsp:include page="/WEB-INF/includes/error.jsp" />
						<jsp:include page="/WEB-INF/includes/events.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>