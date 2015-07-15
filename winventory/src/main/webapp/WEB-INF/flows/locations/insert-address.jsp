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
						<h2 class="center">Add Address</h2>
					</div>
					
					<div class="padme">
						<br>
						<c:if test="${not empty errors}">
                    	<div class="alert alert-danger" role="alert">
                    		<h3 class="error-header">Could not add address:</h3>
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
								<label for="name" class="col-sm-2 control-label">Address Name
								</label>
								<div class="col-sm-9">
									<input name="name" type="text" id="name" pattern="^[^\'\&quot]*$"
										class="form-control" placeholder="Main Office"
										<%if(request.getParameterMap().containsKey("name")) {%>
										value="<%=request.getAttribute("name")%>" 
										<%}%>
										required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="street1" class="col-sm-2 control-label">Street Address
								</label>
								<div class="col-sm-9">
									<input name="street1" type="text" id="street1" pattern="^[^\'\&quot]*$"
										class="form-control" placeholder="123 Main St" 
										<%if(request.getParameterMap().containsKey("street1")) {%>
										value="<%=request.getAttribute("street1")%>" 
										<%}%>
										required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="street2" class="col-sm-2 control-label">Address Line 2
								</label>
								<div class="col-sm-9">
									<input name="street2" type="text" id="street2"
										class="form-control" placeholder="Apt # 101"
										<%if(request.getParameterMap().containsKey("street2")) {%>
										value="<%=request.getAttribute("street2")%>" 
										<%}%>>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
								
							<div class="form-group">
								<label for="city" class="col-sm-2 control-label">City
								</label>
								<div class="col-sm-9">
									<input name="city" type="text" id="city" pattern="^[^\'\&quot]*$"
										class="form-control" placeholder="Anytown" 
										<%if(request.getParameterMap().containsKey("city")) {%>
										value="<%=request.getAttribute("city")%>" 
										<%}%>
										required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							
							<div class="form-group">
								<label for="state" class="col-sm-2 control-label">State
								</label>
								<div class="col-sm-9">
									<select id = "state" name="state" class="form-control" required>
									
										<option value="AL">Alabama</option>
										<option value="AK">Alaska</option>
										<option value="AZ">Arizona</option>
										<option value="AR">Arkansas</option>
										<option value="CA">California</option>
										<option value="CO">Colorado</option>
										<option value="CT">Connecticut</option>
										<option value="DE">Delaware</option>
										<option value="DC">District Of Columbia</option>
										<option value="FL">Florida</option>
										<option value="GA">Georgia</option>
										<option value="HI">Hawaii</option>
										<option value="ID">Idaho</option>
										<option value="IL">Illinois</option>
										<option value="IN">Indiana</option>
										<option value="IA">Iowa</option>
										<option value="KS">Kansas</option>
										<option value="KY">Kentucky</option>
										<option value="LA">Louisiana</option>
										<option value="ME">Maine</option>
										<option value="MD">Maryland</option>
										<option value="MA">Massachusetts</option>
										<option value="MI">Michigan</option>
										<option value="MN">Minnesota</option>
										<option value="MS">Mississippi</option>
										<option value="MO">Missouri</option>
										<option value="MT">Montana</option>
										<option value="NE">Nebraska</option>
										<option value="NV">Nevada</option>
										<option value="NH">New Hampshire</option>
										<option value="NJ">New Jersey</option>
										<option value="NM">New Mexico</option>
										<option value="NY">New York</option>
										<option value="NC">North Carolina</option>
										<option value="ND">North Dakota</option>
										<option value="OH">Ohio</option>
										<option value="OK">Oklahoma</option>
										<option value="OR">Oregon</option>
										<option value="PA">Pennsylvania</option>
										<option value="RI">Rhode Island</option>
										<option value="SC">South Carolina</option>
										<option value="SD">South Dakota</option>
										<option value="TN">Tennessee</option>
										<option value="TX">Texas</option>
										<option value="UT">Utah</option>
										<option value="VT">Vermont</option>
										<option value="VA">Virginia</option>
										<option value="WA">Washington</option>
										<option value="WV">West Virginia</option>
										<option value="WI">Wisconsin</option>
										<option value="WY">Wyoming</option>
									</select>				
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
    
							<div class="form-group">
								<label for="zipcode" class="col-sm-2 control-label">Zip Code
								</label>
								<div class="col-sm-9">
									<input name="zipcode" type="text" id="zipcode" pattern="^\d{5}(?:[-\s]\d{4})?$" 
									data-error="Please enter a valid zip code."
										class="form-control" placeholder="12345" 
										<%if(request.getParameterMap().containsKey("zipcode")) {%>
										value="<%=request.getAttribute("zipcode")%>" 
										<%}%>
										required>
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
		<%if(request.getParameterMap().containsKey("state")) {%>
		document.getElementById('state').value = '<%=request.getAttribute("state")%>';
		<%}%>
	</script>

</body>
</html>