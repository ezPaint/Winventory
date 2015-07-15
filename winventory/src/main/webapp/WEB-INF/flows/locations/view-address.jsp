<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.simoncomputing.app.winventory.domain.Address"%>
<%@ page import="com.simoncomputing.app.winventory.util.Barcoder"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory</title>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css
' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
<script
	src='https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
<script
	src='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js'
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>

</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="locationBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />

				<div class="main">
					<div class="boom">
						<h2 class="center">Address Info</h2>
					</div>

					<div class="padme">
					
					<c:if test="${not empty errors}">
                    	<div class="alert alert-danger" role="alert">
                    		<h3 class="error-header">Could not delete address:</h3>
  							<span class="sr-only">Errors:</span>
  							<c:forEach items="${errors}" var="error">
  								<p class="error-msg">
  									<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  									${error}
  								</p>
  							</c:forEach>
						</div>
                    </c:if>


						<%
						    Address address = (Address) request.getAttribute("address");

						    if (address != null) {
						%>

						<div class="col-md-12">
							<ul class="list-group" style="word-wrap: break-word;">
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Key</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getKey() %></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Name</b>
									</div>
									<div class="col-md-4">
										<p><%=address.getName()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Street 1</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getStreet1()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Street 2</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getStreet2()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>City</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getCity()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>State</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getState()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Zip Code</b>
									</div>
									<div class="col-md-8">
										<p><%=address.getZipcode()%></p>
									</div>
								</li>
							</ul>
						</div>

						<form class="form-horizontal pull-right" action="view-address" method="post">
							<%-- <c:if test="${userInfo.hasPermission.deleteAddress}"> --%>
							<input type="hidden" id="key" name="key"
								value="<%=address.getKey()%>">
							<input type="hidden" id="name" name="name"
								value="<%=address.getName()%>">
							<input type="hidden" id="street1" name="street1"
								value="<%=address.getStreet1()%>">
							<input type="hidden" id="street2" name="street2"
								value="<%=address.getStreet2()%>">
							<input type="hidden" id="city" name="city"
								value="<%=address.getCity()%>">
							<input type="hidden" id="state" name="state"
								value="<%=address.getState()%>">
							<input type="hidden" id="zipcode" name="zipcode"
								value="<%=address.getZipcode()%>">
							<button type="submit" class="btn btn-danger">Delete</button>
							<%-- </c:if> --%>
						<%-- <c:if test="${userInfo.hasPermission.updateAddress}"> --%>
						<a class="btn btn-default"
							href="edit-address?key=<%=address.getKey()%>" role="button">Edit</a> 
						<%-- </c:if> --%>
						</form>
						
						<div class="container-fluid"></div>
												
						<%
						    }
						%>
						
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