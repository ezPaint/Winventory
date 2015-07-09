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
	<jsp:include page="/WEB-INF/flows/software/swBase.jsp" />

			<div class="container-fluid">
				<div class="row">
					<h1 class="center">Application Basic Search</h1>
					<br>
					<form action="search" method="get">
						<div class="input-group">
							<input name="searchText" type="text" class="form-control"
								placeholder="Search Applications"> <span
								class="input-group-btn">
								<button class="btn btn-default" type="submit">Search</button>
							</span>
						</div>
					</form>
					<br>
					<div class="panel panel-default">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>Name</th>
										<th>Serial Number</th>
										<th>Version</th>
										<th>License Key</th>
										<th>Cost</th>
										<th>Purchased Date</th>
										<th>Expiration Date</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${swResult}">
									<c:forEach var="i" begin="0" end="${softwares.size()-1}">
									<tr>
									<td>${softwares.get(i).getName()}</td>
									<td>${softwares.get(i).getSerialNo()}</td>
									<td>${softwares.get(i).getVersion()}</td>
									<td>${softwares.get(i).getLicenseKey()}</td>
									<td>${softwares.get(i).getCost()}</td>
									<td>${softwares.get(i).getPurchasedDate()}</td>
									<td>${softwares.get(i).getExpirationDate()}</td>
									</tr>
									</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
					<nav class="center">
						<ul class="pagination">
							<li class=""><a href="#" aria-label="Previous"><span
									aria-hidden="true">&laquo;</span></a></li>
							<li class="active"><a href="#">1 <span class="sr-only"></span></a>
							</li>
							<li class=""><a href="#">2 </a></li>
							<li class=""><a href="#">3 <span class="sr-only"></span></a>
							</li>
							<li class=""><a href="#">4 <span class="sr-only"></span></a>
							</li>
							<li class=""><a href="#">5 <span class="sr-only"></span></a>
							</li>
							<li class=""><a href="#" aria-label="Next"><span
									aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
				</div>
				<br> <br> <br> <br>
			</div>
		</div>
	</div>
	<div class="top-header">
		<div class="center">
			<br>
			<h4>SimonComputing © 2015</h4>
			<br>
		</div>
	</div>
</body>

</html>