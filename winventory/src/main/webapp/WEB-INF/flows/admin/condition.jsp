<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Admin</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

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
			<jsp:include page="adminBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Conditions</h2>
					</div>
					<div class="padme">
						<br>
						<c:if test="${not empty pass}">
							<div class="alert alert-success" role="alert">
								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								<c:out value="${pass}"/>
							</div>
						</c:if>
						<c:if test="${not empty fail}">
							<div class="alert alert-danger" role="alert">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								<c:out value="${fail}"/>
							</div>
						</c:if>

						<div class="table-responsive">
							<table class="table table-striped" id="resultsTable">
								<thead>
									<tr>
										<th style="border-bottom: 0px">Condition Name</th>
										<th style="border-bottom: 0px">Description</th>
										<th style="border-bottom: 0px">Delete</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="con" items="${conditions}">
										<tr>
											<th style="padding-top: 15px"><c:out value="${con.code}"/></th>
											<th style="padding-top: 15px"><c:out value="${con.description}"/></th>
											<th>
												<form action="condition" method="post" role="form">
													<button class="btn btn-default" type="submit" name="delete" value="${con.code}" >Delete</button>
												</form>
											</th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					
					
					</div>
					<div class="boom">
						<h2 class="center">Add New Condition</h2>
					</div>
					<div class="padme"  style="min-height: 0px" >
						<br>
						<br>
						
						<form class="form-horizontal" action="condition" role="form" method="post">
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">Name</label>
								<div class="col-sm-9 search-field">
									<input name="name" type="text" id="type" class="form-control" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
								<label for="description" class="col-sm-2 control-label">Description</label>
								<div class="col-sm-9 search-field">
									<input name="description" type="text" id="type" class="form-control" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" class="btn btn-default" name="add" value="add">Add</button>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>

	<jsp:include page="/WEB-INF/includes/footer.jsp" />

</body>

</html>