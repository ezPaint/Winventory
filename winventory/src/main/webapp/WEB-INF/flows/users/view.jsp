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

<script type="text/javascript">
	$(document).ready(function() {
		$('#resultsTable').DataTable();
		document.getElementById("resultsTable").style.display = "table";
	});
</script>
</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="userBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />

				<div class="main">
					<div class="boom">
						<h2 class="center">User Info</h2>
					</div>

					<div class="padme" style="margin-bottom: 200px;">
						<%@ page
							import="com.simoncomputing.app.winventory.domain.User"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    User user = (User) request.getAttribute("user");

						    if (user != null) {
						%>


						<div class="media">
							<div class="media-left">
								<img class="profile-img-big"
									src="${contextPath}/resources/images/SC-Logo-Black-On-White.png">
							</div>
							<div class="media-body">
								<ul class="list-group">
								
									<li class="list-group-item row">
										<div class="col-md-3">
										
											<b>Key</b>
											
										</div>
										<div class="col-md-9">
											<p><%=user.getKey()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
										
											<b>Username</b>
											
										</div>
										<div class="col-md-9">
											<p><%=user.getUsername()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
										
											<b>First Name</b>
										
										</div>
										<div class="col-md-9">
											<p><%=user.getFirstName()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
										
											<b>Last Name</b>
										
										</div>
										<div class="col-md-9">
											<p><%=user.getLastName()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
										
											<b>Email</b>
										
										</div>
										<div class="col-md-9">
											<p><%=user.getEmail()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
											
											<b>Cell Phone</b>
										
										</div>
										<div class="col-md-9">
											<p><%=user.getCellPhone()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Work Phone</b>
										</div>
										<div class="col-md-9">
											<p><%=user.getWorkPhone()%></p>
										</div>
									</li>
									
									<li class="list-group-item row">
										<div class="col-md-3">
											
											<b>Active</b>
										
										</div>
										<div class="col-md-9">
											<p><%=user.getIsActive()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Role Id</b>
										</div>
										<div class="col-md-9">
											<p><%=user.getRoleId()%></p>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div>
							<%-- <c:if test="${userInfo.hasPermission.updateHardware}"> --%>
							<a class="btn btn-default" href="edit?key=<%=user.getKey()%>"
								role="button">Edit</a>
							<%-- </c:if> --%>
						</div>

						<%
						    }
						%>
					
					
					
					
						<!-- <div id='loader' style="padding-top: 750px;">
							<div class='diamond'></div>
							<div class='diamond'></div>
							<div class='diamond'></div>
						</div> -->
						
						
						<div class="user-hardware-title">
							<h2>User's Hardware</h2>
						</div>
						
						<%@ page
										import="com.simoncomputing.app.winventory.domain.Hardware"%>
									<%@ page import="java.util.ArrayList"%>
						
						<%
							
							// Table of hardware the user owns
							
							ArrayList<Hardware> results = (ArrayList<Hardware>) request.getAttribute("results");

					    	if (results != null && results.size() != 0) {
						
						%>
						
						<div class="table-responsive" >
							<table class="table table-striped" id="resultsTable"
								style="display: none;">
								<thead>
									<tr>
										<th>Key</th>
										<th>Type</th>
										<th>Cost</th>
										<th>Condition</th>
										<th>Purchase Date</th>
										<th>Description</th>
										<th>Serial No.</th>
									</tr>
								</thead>
								<tbody>

									

									<%
											
									        for (int i = 0; i < results.size(); i++) {
									%>
									<tr>
										<td><a href="${contextPath}/hardware/view?key=<%=results.get(i).getKey()%>">
												<%=results.get(i).getKey()%></a></td>
										<td><%=results.get(i).getType()%></td>
										<td><%=results.get(i).getCost()%></td>
										<td><%=results.get(i).getCondition()%></td>
										<td><%=results.get(i).getPurchaseDate()%></td>
										<td><%=results.get(i).getDescription()%></td>
										<td><%=results.get(i).getSerialNo()%></td>
									</tr>
									<%
									    }
									    
									%>
								</tbody>
							</table>
							
						</div>
						
						<%
					    	} // endif(hardware results == null)
					    	else {
						%>
						<p class="user-hardware-message">This user owns no hardware. </p>
						</div>
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
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>