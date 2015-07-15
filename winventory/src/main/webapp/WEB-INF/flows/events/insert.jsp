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
<script src='${contextPath}/resources/js/validator.js'
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
						<h2 class="center">Add Event</h2>
					</div>
					
					<div class="padme">
						<br>
						<c:if test="${not empty errors}">
	                    	<div class="alert alert-danger" role="alert">
	                    		<h3 class="error-header">Could not add event:</h3>
	  							<span class="sr-only">Errors:</span>
	  							<c:forEach items="${errors}" var="error">
	  								<p class="error-msg">
	  									<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	  									${error}
	  								</p>
	  							</c:forEach>
							</div>
                    	</c:if>
                    	
                    	<jsp:include page="/WEB-INF/includes/error.jsp" />
						
						<form class="form-horizontal" action="insert"
							data-toggle="validator" role="form" method="post">
							<div class="form-group">
								<label for="description" class="col-sm-2 control-label">Description </label>
								<div class="col-sm-9 search-field">
									<input name="description" type="text" id="description" class="form-control"
										placeholder="A description of what happened, 2000 char limit" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="hardware" class="col-sm-2 control-label"> Associated Hardware </label>
								<div class="col-sm-9">
									<input name="hardware" type="text" id="hardware"
										class="form-control" placeholder="Comma separated list of keys, for example 1, 2, 3">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="software" class="col-sm-2 control-label"> Associated Software </label>
								<div class="col-sm-9">
									<input name="software" type="text" id="software"
										class="form-control" placeholder="Comma separated list of keys, for example 1, 2, 3">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="software" class="col-sm-2 control-label"> Associated Locations </label>
								<div class="col-sm-9">
									<input name="location" type="text" id="location"
										class="form-control" placeholder="Comma separated list of keys, for example 1, 2, 3">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" class="btn btn-default">Add</button>
									<button type="reset" class="btn btn-default">Clear</button>
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