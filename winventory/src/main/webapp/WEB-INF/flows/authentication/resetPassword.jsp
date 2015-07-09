<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Password Reset</title>
    <meta charset="UTF-8">
    
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
	<div class="container">
        <div class="row">
            <div class="col-sm-6 col-md-4 col-md-offset-4 text-center">
                <div class="account-wall">
                <div class="">
                Enter the email address associated with your account. We'll send you an email with a link to reset your password.

                
				<form name="resetform" action="" method="POST" accept-charset="UTF-8" role="form" class="form-login">
					<div class="">
					<input  id="email"  name="email"  class="form-control" placeholder="Email Address"
			                                    	type="text" value="" autocomplete="off" > 
			        </div>
			         <input class="btn btn-lg btn-primary btn-block" type="submit" value="Send">                
					</form>
				</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>