<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Winventory Password Change</title>
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
        <div class="top-header">
            <div class="media-right">
                <br>
                <img src="${contextPath}/resources/images/SC-Logo.png" height="50" width="50" alt="">
            </div>
            <div class="media-body">
                <br>
                <h4>The Winventory</h4>
                <br>
            </div>
        </div>
        

	<div class="container">
        <div class="row">
            <div class="col-sm-6 col-md-4 col-md-offset-4 text-center">
                <div class="account-wall">
                <div class="">
                Input your new password
                <br> 
					<form data-toggle="validator" role="form" name="resetform" action="changepassword" method="POST" accept-charset="UTF-8" role="form" class="form-login">
						<div class="form-group">
						   
						      <input type="password" data-minlength="8" class="form-control" id="newPassword" name="newPassword" placeholder="Password" required>
						      <span class="help-block">Minimum of 8 characters</span>
						  
						     <input class="btn btn-lg btn-primary btn-block" type="submit" value="Submit">  
						  </div>
					</form>
				</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>
</html>