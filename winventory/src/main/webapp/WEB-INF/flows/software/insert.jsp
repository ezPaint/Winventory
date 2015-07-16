<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Software Inventory</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

<!-- Include Required Prerequisites for Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/moment.min.js"></script>

<!-- Include Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker-bs3.css" />
<script type="text/javascript">
$(function() {
    $('input[name="purchasedDate"]').daterangepicker({
    	format: 'YYYY-MM-DD',
    	minDate: '1970-01-01',
        singleDatePicker: true,
        showDropdowns: true
    }); 
    $('input[name="expirationDate"]').daterangepicker({
    	format: 'YYYY-MM-DD',
    	minDate: '1970-01-01',
        singleDatePicker: true,
        showDropdowns: true
    }); 

});
</script>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src="${contextPath}/resources/js/actions.js"
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/validator.js' type="text/javascript"></script>
</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="swBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">
						<h2 class="center">Insert Software</h2>
					</div>
					<div class="padme">
						<br>                          

						<form class="form-horizontal" action="insert"
							data-toggle="validator" role="form" method="post" name="myform">
							
							<!-- Name entry field -->
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">Name </label>
								<div class="col-sm-9 search-field">
									<input name="name" type="text" id="name"
										class="form-control search-software-name"
										placeholder="Microsoft Word" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- Serial number entry field -->
							<div class="form-group">
								<label for="serialNo" class="col-sm-2 control-label">Serial
									Number </label>
								<div class="col-sm-9">
									<input name="serialNo" type="text" id="serialNo"
										class="form-control" placeholder="JDKF8287KD922" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- License Key entry field -->
							<div class="form-group">
								<label for="cost" class="col-sm-2 control-label">License
									Key </label>
								<div class="col-sm-9">
									<input name="licenseKey" type="text" id="licenseKey"
										type="text" class="form-control"
										placeholder="JDK3-JKSL-SKD8-923J" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- Version entry field -->
							<div class="form-group">
								<label for="version" class="col-sm-2 control-label">Version</label>
								<div class="col-sm-9">
									<input name="version" type="text" id="version"
										class="form-control" placeholder="Professional 2013" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- Cost entry field -->
							<div class="form-group">
								<label for="cost" class="col-sm-2 control-label">Cost $</label>
								<div class="col-sm-9">
									<input name="cost" type="text" step="any" min="0" id="version" pattern="^[0-9]+(\.[0-9][0-9]?)*$"
										class="form-control" placeholder="89.99" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>

                            <!-- Date purchased entry field -->
							<div class="form-group">
								<label for="purchasedDate" class="col-sm-2 control-label">Date
									Purchased </label>
								<div class="col-sm-9">
									<input name="purchasedDate" type="date" id="purchasedDate"
										class="form-control" placeholder="YYYY-MM-DD" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- Expiration date entry field -->
							<div class="form-group">
								<label for="expirationDate" class="col-sm-2 control-label">Expiration
									Date </label>
								<div class="col-sm-9">
									<input name="expirationDate" type="date" id="expirationDate"
										class="form-control" placeholder="YYYY-MM-DD" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<!-- Description entry field -->
							<div class="form-group">
								<label for="description" class="col-sm-2 control-label">Description</label>
								<div class="col-sm-9">
									<input name="description" type="text" id="description"
										class="form-control"
										placeholder="Compatible with Windows XP/Vista/7/8/10">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="isActive" class="col-sm-2 control-label">Activate item?
									</label>
								<div class="col-sm-9">
									<input name="isActive" type="checkbox" id="isActive"
										class="" style="margin-top: 12px;" value="true" checked> Yes, this item will be active immediately.
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