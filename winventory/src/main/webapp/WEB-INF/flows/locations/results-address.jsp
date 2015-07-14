<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<script type="text/javascript">
	$(document).ready(function() {
		$('#resultsTable').DataTable({
			"pagingType" : "full"
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
			<jsp:include page="locationBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />
				<div class="main">
					<div class="boom">
						<h2 class="center">${page_header}</h2>
					</div>
					<div class="padme">
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
										<th>Name</th>
										<th>Street 1</th>
										<th>Street 2</th>
										<th>City</th>
										<th>State</th>
										<th>Zip Code</th>
									</tr>
								</thead>
								<tbody>

									<%@ page
										import="com.simoncomputing.app.winventory.domain.Address"%>
									<%@ page import="java.util.ArrayList"%>

									<%
									    ArrayList<Address> results = (ArrayList<Address>) request.getAttribute("results");

									    if (results != null) {

									        for (int i = 0; i < results.size(); i++) {
									%>
									<tr>
										<td><a href="view-address?key=<%=results.get(i).getKey()%>">
												<%=results.get(i).getKey()%></a></td>
										<td><%=results.get(i).getName()%></td>
										<td><%=results.get(i).getStreet1()%></td>
										<td><%=results.get(i).getStreet2()%></td>
										<td><%=results.get(i).getCity()%></td>
										<td><%=results.get(i).getState()%></td>
										<td><%=results.get(i).getZipcode()%></td>
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
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>