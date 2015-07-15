<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Winventory</title>
    <meta charset="UTF-8">
    
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-social.css">

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/validator.js' type="text/javascript"></script>
    
</head>
<body>

       
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="locationBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Add Location</h2>
					</div>
					
					<div class="padme">
						<br>
						<c:if test="${not empty errors}">
                    	<div class="alert alert-danger" role="alert">
                    		<h3 class="error-header">Could not add location:</h3>
  							<span class="sr-only">Errors:</span>
  							<c:forEach items="${errors}" var="error">
  								<p class="error-msg">
  									<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  									${error}
  								</p>
  							</c:forEach>
						</div>
                    </c:if>
						
						
						<form class="form-horizontal" action=""
							data-toggle="validator" role="form" method="post">			
							
							<div class="form-group">
								<label for="address" class="col-sm-2 control-label">Address
								</label>
								<div class="col-sm-9">

									<select id="address" name="address" class="form-control" required>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.Address"%>
										<%@ page import="java.util.ArrayList"%>

										<%
										    ArrayList<Address> addresses = (ArrayList<Address>) request
										            .getAttribute("addresses");

										    if (addresses != null) {

										        for (int i = 0; i < addresses.size(); i++) {
										%>

										<option value="<%=addresses.get(i).getKey()%>"><%=addresses.get(i).getName()%></option>

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
								<label for="description" class="col-sm-2 control-label">Description
								</label>
								<div class="col-sm-9">
									<input name="description" type="text" id="description"
										class="form-control" placeholder="Description" 
										<%if(request.getParameterMap().containsKey("description")) {%>
										value="<%=request.getAttribute("description")%>" 
										<%}%>
										required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
								
							<div class="form-group">
								<label for="isActive" class="col-sm-2 control-label">Active Location
									</label>
								<div class="col-sm-9">
									<input name="isActive" type="checkbox" id="isActive"
										class="" style="margin-top: 24px;" value="true"> Yes, the location is currently active.
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
	
	<script>
		<%if(request.getParameterMap().containsKey("address")) {%>
		document.getElementById('address').value = '<%=request.getAttribute("address")%>';
		<%}%>
	</script>

</body>
</html>