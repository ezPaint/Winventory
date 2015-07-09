<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Inventory</title>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
</head>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="adminBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Admin Dashboard</h2>
					</div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">General Information</h3>
						</div>
						<div class="panel-body">${totHw} pieces of Hardware on record</div>
						<div class="panel-body">${numSw} pieces of Applications on record</div>
						<div class="panel-body">${numUsers} Users on record</div>
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Reports</h3>
						</div>
						
						<!-- TODO: figure out how to calculate values for progress bars -->
						<!-- Current progress bar values are random and hard-coded in -->
						<div class="panel-body">
							Hardware in Use
							<div class="progress">
								<div class="progress-bar" role="progressbar"
									aria-valuemin="0" aria-valuemax="100"
									style="width: 87%;"></div>
							</div>
							Applications in Use
							<div class="progress">
								<div class="progress-bar" role="progressbar"
									aria-valuenow="{numSw}" aria-valuemin="0" aria-valuemax="100"
									style="width: 70%;">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>