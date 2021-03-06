<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Edit User</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

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
				<jsp:include page="/WEB-INF/includes/error.jsp" />
				<div class="main">
					<div class="boom">
						<h2 class="center">Edit User</h2>
					</div>
					<br>
					<div class="padme">
					
					
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

						<%@ page
							import="com.simoncomputing.app.winventory.domain.User"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    User user = (User) request.getAttribute("user");

												    if (user != null) {
						%>

						<form class="form-horizontal" action="edit"
							data-toggle="validator" role="form" method="post">
							
							<div class="form-group">
							
								<label for="key" class="col-sm-2 control-label">Key </label>
								
								<div class="col-sm-9 search-field">
									<input name="key" type="text" id="key" pattern="^[^\'\&quot]*$"
										class="form-control search-hardware-type"
										value="<%=user.getKey()%>" readonly>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="username" class="col-sm-2 control-label">Username </label>
								
								<div class="col-sm-9 search-field">
									<input name="username" type="text" id="username" pattern="^[^\'\&quot]*$"
										class="form-control"
										value="<%=user.getUsername()%>" disabled>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="firstName" class="col-sm-2 control-label">First Name </label>
								
								<div class="col-sm-9 search-field">
									<input name="firstName" type="text" id="firstName" pattern="^[^\'\&quot]*$"
										class="form-control"
										value="<%=user.getFirstName()%>">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="lastName" class="col-sm-2 control-label">Last Name
								</label>
								<div class="col-sm-9">
									<input name="lastName" type="text" id="lastName" pattern="^[^\'\&quot]*$"
										class="form-control" value="<%=user.getLastName()%>">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="email" class="col-sm-2 control-label">Email </label>
								
								<div class="col-sm-9">
									<input name="email" type="text" id="email" type="text" pattern="^[0-9]+(\.[0-9][0-9]?)*$"
										class="form-control" value="<%=user.getEmail()%>" disabled>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="cellPhone" class="col-sm-2 control-label">Cell Phone</label>
									
								<div class="col-sm-9">
									<input name="cellPhone" type="text" id="cellPhone" 
										class="form-control" value="<%=user.getCellPhone() %>" >
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
							
								<label for="workPhone" class="col-sm-2 control-label">Work Phone</label>
									
								<div class="col-sm-9">
									<input name="workPhone" type="text" id="workPhone" 
										class="form-control" value="<%=user.getWorkPhone() %>" >
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="condition" class="col-sm-2 control-label">Role
								</label>
								<div class="col-sm-9">

									<select name="roleTitle" class="form-control" required>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.Role"%>
										<%@ page import="java.util.ArrayList"%>

										<%
											String roleTitle = user.getRoleTitle();
										    ArrayList<Role> roles = (ArrayList<Role>) request
																				            .getAttribute("roles");

																				    if (roles != null) {

																				        for (int i = 0; i < roles.size(); i++) {
										%>
											<%  if (roles.get(i).getTitle().equals(roleTitle)) { %>
											
												<option value="<%=roles.get(i).getTitle()%>" selected><%=roles.get(i).getTitle()%></option>
											
											<% } else { %>
												<option value="<%=roles.get(i).getTitle()%>" ><%=roles.get(i).getTitle()%></option>
											<% } %>

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
								<label for="isActive" class="col-sm-2 control-label">Is Active
									</label>
								<div class="col-sm-9">
									<input name="isActive" type="checkbox" id="isActive"
										class="" style="margin-top: 12px;" value="<%=user.getIsActive()%>"> Check the box if this user is active.
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" class="btn btn-primary">Submit
										</button>
									<button type="reset" class="btn btn-default" onClick="reloadConditions(); return false;">Reset
										</button>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<%-- <input type="hidden" id="key" name="key"
								value="<%=hardware.getKey()%>"> --%>
						</form>
 <br>
                         <br>
                         <br>
                         <br>
                         <hr>
                         <br>
                         <!-- <div class="center"> -->
                         	<!-- <div class="col-md-5"></div> -->
                         	<!-- <div class="col-md-4"> -->
                         
                          	<form action="${contextPath}/users/view" role="form" method="get" class="form-group">

								<c:if test="${userInfo.hasPermission.deleteSoftware }">
                                    <input type="hidden" id="key" name="key" value="<%=user.getKey()%>">
                                        <input type="hidden" id="delete" name="delete" value="true">
                                    <button type="Submit" name="button" value="Delete" class="btn btn-danger btn-lg btn-center">Delete Forever</button>
                                    
								</c:if>
						 	</form>
						
						 	<!-- </div>
                         	<div class="col-md-3"></div> -->
						 <!-- </div> -->

						<%
						    }
						%>
                                        
                        <br>
                        <div>
                            
                        </div>
                    </div>
                </div>
                <br>
                <br>
            </div>
            <br>
            <br>
            <br>
            <br>
        </div>
    </div>
    <jsp:include page="/WEB-INF/includes/footer.jsp" /> 

</body>

</html>