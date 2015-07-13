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
	
<!--  TYPEAHEAD  -->
<script src="${contextPath}/resources/js/typeahead.bundle.js"></script>
<script src="${contextPath}/resources/js/winventory-typeahead.js"></script>
	
</head>
<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="userBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Add User</h2>
					</div>
					
					<div class="padme">
						<br>
						<c:if test="${not empty errors}">
	                    	<div class="alert alert-danger" role="alert">
	                    		<h3 class="error-header">Could not add user:</h3>
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
								<label for="username" class="col-sm-2 control-label">Username </label>
								<div class="col-sm-9 search-field">
									<input name="username" type="text" id="username" class="form-control"
										placeholder="first.last" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="email" class="col-sm-2 control-label"> Email </label>
								<div class="col-sm-9">
									<input name="email" type="email" id="email"
										class="form-control" placeholder="first.last@email.com" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="isGoogle" class="col-sm-2 control-label">Use Google Login?
									</label>
								<div class="col-sm-9">
									<input name="isGoogle" type="checkbox" id="isGoogle"
										class="" style="margin-top: 24px;" value="true"> Yes, this user will login with the Gmail address listed above.
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="firstName" class="col-sm-2 control-label">First Name
								</label>
								<div class="col-sm-9">
									<input name="firstName" type="text" id="firstName"
										class="form-control" placeholder="First">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="lastName" class="col-sm-2 control-label"> Last Name </label>
								<div class="col-sm-9">
									<input name="lastName" type="text" id="lastName" type="text"
										class="form-control" placeholder="Last">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							
							<div class="form-group">
								
								<label for="text" class="col-sm-2 control-label">Cell Phone
								</label>
								<div class="col-sm-9">
								
									<input name="cellPhone" type="text" id="cellPhone"
										class="form-control" placeholder="540-668-1234">
										
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
									
								</div>
								
							</div>
							<div class="form-group">
								
								<label for="text" class="col-sm-2 control-label">Work Phone
								</label>
								<div class="col-sm-9">
								
									<input name="workPhone" type="text" id="workPhone"
										class="form-control" placeholder="703-123-4567">
										
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
									
								</div>
								
							</div>
							<div class="form-group">
								<label for="isActive" class="col-sm-2 control-label">Activate user?
									</label>
								<div class="col-sm-9">
									<input name="isActive" type="checkbox" id="isActive"
										class="" style="margin-top: 12px;" value="true" checked> Yes, this user will be active immediately.
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="roleTitle" class="col-sm-2 control-label">Role
								</label>
								<div class="col-sm-9">

									<select name="roleTitle" class="form-control" required>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.Role"%>
										<%@ page import="java.util.ArrayList"%>

										<%
										    ArrayList<Role> roles = (ArrayList<Role>) request
										            .getAttribute("roles");

										    if (roles != null) {

										        for (int i = 0; i < roles.size(); i++) {
										%>

										<option><%=roles.get(i).getTitle()%></option>

										<%
										    }
										    }
										%>

									</select>

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