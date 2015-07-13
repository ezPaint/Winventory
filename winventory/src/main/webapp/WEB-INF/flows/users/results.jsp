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
		$('#resultsTable').DataTable({
	        "pagingType": "full"
	    });
		document.getElementById("loader").style.visibility = "hidden";
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
				<div class="main">
					<div class="boom">
						<h2 class="center">Users</h2>
					</div>
					<div class="padme">
						<jsp:include page="/WEB-INF/includes/success.jsp" />
						<div id='loader'>
							<div class='diamond'></div>
							<div class='diamond'></div>
							<div class='diamond'></div>
						</div>
						<div class="table-responsive">
						<table class="table table-striped" id="resultsTable"
							style="display: none;">
							<thead>
								<tr>
									<th>Key</th>
									<th>Username</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
									<th>Cell Phone</th>
									<th>Work Phone</th>
									<th>Is Active</th>
									<th>Role Id</th>
								</tr>
							</thead>
							<tbody>

								<%@ page
									import="com.simoncomputing.app.winventory.domain.User"%>
								<%@ page import="java.util.ArrayList"%>

								<%
								    ArrayList<User> results = (ArrayList<User>) request.getAttribute("results");

								    if (results != null) {

								        for (int i = 0; i < results.size(); i++) {
								%>
								<tr>
									<td><%=results.get(i).getKey()%></td>
									<td><a href="view?key=<%=results.get(i).getKey()%>"> <%=results.get(i).getUsername()%></a></td>
									<td><%=results.get(i).getFirstName()%></td>
									<td><%=results.get(i).getLastName()%></td>
									<td><%=results.get(i).getEmail()%></td>
									<td><%=results.get(i).getCellPhone()%></td>
									<td><%=results.get(i).getWorkPhone()%></td>
									<td><%=results.get(i).getIsActive()%></td>
									<td><%=results.get(i).getRoleId()%></td>
								</tr>
								<%
								    }
								    }
								%>
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<br> <br> <br> <br>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>