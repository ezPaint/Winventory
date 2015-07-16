<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>

    <title>Inventory</title>
    <meta charset="UTF-8">

	<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/style.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/normalize.css' />
    <link type="text/css" rel="stylesheet" href='${contextPath}/resources/css/bootstrap.css' />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-social.css">

    <script src='${contextPath}/resources/js/actions.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' type="text/javascript"></script>
    <script src='${contextPath}/resources/js/bootstrap.min.js' type="text/javascript"></script>

  
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
                    <div>
                        <h3 class="login-title">Welcome Back</h3>
                    </div>
                	<img class="profile-img" src="${contextPath}/resources/images/SC-Logo-Black-On-White.png"
                    alt=""/>
                    <c:if test="${not empty error}">
                    	<div class="alert alert-danger" role="alert">
  							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  							<span class="sr-only">Error:</span>
  								<c:out value="${error}"/>
						</div>
                    </c:if>
                    <div class="">
                        <form name="loginform" action="" method="POST" accept-charset="UTF-8" role="form" class="form-login">
                        	<div class="form-horizontal">
                                <div class="" >
                                    <input  id="login-username"  name="username"  class="form-control" placeholder="Username or Email"
                                    	type="text" value="" autocomplete="off" > 
                                </div>
                                <div class="">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Login">
                                <!-- <div>
                                	<label class="checkbox pull-left">
                                    	<input name="rememberMe" type="checkbox" value="true"> Remember Me
                                	</label>
                                </div> -->
                                 <div style="margin-top: 5px" class="">
                                    	
                                    	 <a href="resetpassword" >Forgot Password?</a> 
                                </div>
                            </div>
                        </form>
                    </div>
                    <c:if test="${not empty GC}">
	                    <div class="col-md-12 or-line">
							<div class="col-xs-5"> <hr> </div>
							<div class="col-xs-2"><h4>or</h4></div>
							<div class="col-xs-5"> <hr> </div>
						</div>
						<br><br><br><br>
						<form action="${contextPath }/login/googleLogin" method="get" class="form-login">
							<div class="form-horizontal">
	                    	<input class="btn btn-lg btn-google btn-block " type="submit" value="Login with Google+">
	                    	</div>
	                    </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/includes/footer.jsp" />
    
</body>
</html>