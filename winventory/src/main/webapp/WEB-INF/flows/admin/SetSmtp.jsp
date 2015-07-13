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
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="adminBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">SMTP Settings</h2>
					</div>
					<div class="padme">
						<br>
						<c:if test="${not empty note}">
							<div class="alert alert-success" role="alert">
								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								<c:out value="${note}"/>
							</div>
						</c:if>
						<br>
						
						
						<form class="form-horizontal" action="setSmtp" role="form" method="post">
							<div class="form-group">
								<label for="hostname" class="col-sm-2 control-label">Host Name</label>
								<div class="col-sm-9 search-field">
									<input name="hostname" type="text" id="type" class="form-control"
										value="${smtpInfo.hostName}" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="username" class="col-sm-2 control-label">User Name</label>
								<div class="col-sm-9 search-field">
									<input name="username" type="text" id="type" class="form-control"
										value="${smtpInfo.authUserName}" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">User Password </label>
								<div class="col-sm-9 search-field">
									<input name="password" type="password" id="type" class="form-control"
										value="${smtpInfo.authPassword}" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="port" class="col-sm-2 control-label">Port </label>
								<div class="col-sm-9 search-field">
									<input name="port" type="text" id="type" class="form-control"
										value="${smtpInfo.port}" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="ssl" class="col-sm-2 control-label">SSL</label>
								<div class="col-sm-9">
									<c:if test="${smtpInfo.ssl}">
										<input style="margin-top: 10px" name="ssl" type="checkbox" id="type" 
											value="${smtpInfo.ssl}" checked> Enable SSL Connection
									</c:if>
									<c:if test="${not smtpInfo.ssl}">
										<input name="ssl" type="checkbox" id="type" 
											value="${smtpInfo.ssl}"> Enable SSL Connection
									</c:if>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" name="setSmtp" value="setSmtp" class="btn btn-default">Apply</button>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
						</form>
					</div>	
					<div class="boom">
						<h2 class="center">Send Test Email</h2>
					</div>
					<br>
					<div class ="padme">
						<c:if test="${not empty sent}">
							<div class="alert alert-success" role="alert">
								<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								<c:out value="${sent}"/>
							</div>
						</c:if>
								
						<c:if test="${not empty failed}">
							<div class="alert alert-danger" role="alert">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								<c:out value="${failed}"/>
							</div>
						</c:if>
					<br>
						
						<form class="form-horizontal" action="setSmtp" role="form" method="post">
							<div class="form-group">
								<label for="testaddress" class="col-sm-2 control-label">Test Address</label>
								<div class="col-sm-9 search-field">
									<input name="testaddress" type="text" id="type" class="form-control" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" name="testEmail" value="testEmail" class="btn btn-default">
									Test Email
									</button>
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