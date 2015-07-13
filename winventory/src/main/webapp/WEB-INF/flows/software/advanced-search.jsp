<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Inventory</title>




<!-- Include Required Prerequisites for Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/moment.min.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap/3.3.2/css/bootstrap.css" /> -->  <!-- Duplicate -->

<!-- Include Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/1/daterangepicker-bs3.css" />
<script type="text/javascript">
    $(function() {
        $('input[name="purchasedDate"]').daterangepicker({format: 'YYYY-MM-DD'});
    });
    $(function() {
        $('input[name="expirationDate"]').daterangepicker({format: 'YYYY-MM-DD'});
    });
</script>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<%-- <script src='${contextPath}/resources/js/jquery-1.11.3.min.js' --%> <%-- Obsolete (can't use with Date Range Picker) --%>
<!-- 	type="text/javascript"></script> -->
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>

<script>
	function addDiv(container, name, type) {
		var newDiv = document.createElement('div');
		newDiv.innerHTML = "<br><div class=\"input-group extra-box\">"
				+ "<input name=\"" + name + "\" type=\"" + type + "\" class=\"form-control\">"
				+ "</div>";
		document.getElementById(container).appendChild(newDiv);
	}
</script>


<!-- Styles the dual-entry form (date purchased and expiration date) -->
<style>
.double-input .form-control {
	width: 50%;
	border-right-width: 1px;
}

.double-input .form-control:focus {
	border-right-width: 1px;
}

.input-group-addon {
	min-width: 200px;
	text-align: center;
}
</style>
</head>

<body>
    <jsp:include page="/base.jsp" />
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="swBase.jsp" />
            <div class="col-md-8">
                <jsp:include page="/WEB-INF/includes/error.jsp" />
                <div class="main">
                <div class="boom">
                        <h2 class="center">Software Advanced Search</h2>
                    </div>
                    <div class="padme">
                           <form action="advancedsearch" method="post">
                           
                           <!-- Name entry fields -->
							<div id="name_container">
								<div class="input-group" id="name_div">
									<span class="input-group-addon" id="basic-addon1">Name</span> <input
										name="name" type="text" class="form-control"
										placeholder="Microsoft Word"> <span class="input-group-btn"
										aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('name_container', 'name', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							
							<!-- Serial Number entry field -->
							<div id="serialNo_container">
								<div class="input-group" id=serialNo>
									<span class="input-group-addon" id="basic-addon1">Serial
										No</span> <input name="serialNo" type="text" class="form-control"
										placeholder="JFDKN889238SJDKLQLM"> <span class="input-group-btn"
										aria-hidden="true">
										<button class="btn btn-default" type="button"
											onclick="addDiv('serialNo_container', 'serialNo', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							
							<!-- Version entry field -->
							<div id="version_container">
								<div class="input-group" id="version_div">
									<span class="input-group-addon" id="basic-addon1">Version</span> <input
										name="version" type="text" class="form-control"
										placeholder="2015"> <span class="input-group-btn"
										aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('version_container', 'version', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							
							<!-- License Key entry field -->
							<div id="licenseKey_container">
								<div class="input-group" id="licenseKey_div">
									<span class="input-group-addon" id="basic-addon1">License Key</span> <input
										name="licenseKey" type="text" class="form-control"
										placeholder="123456789"> <span class="input-group-btn"
										aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('licenseKey_container', 'licenseKey', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br>
							
							<!-- Cost entry field -->
<!-- 							<div id="cost_container">
								<div class="input-group" id="cost_div">
									<span class="input-group-addon" id="basic-addon1">Cost</span> <input
										name="cost" type="text" class="form-control"
										placeholder="$77.00"> <span class="input-group-btn"
										aria-hidden="true">
										<button type="button" class="btn btn-default"
											onClick="addDiv('cost_container', 'cost', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> <br>
									</span>
								</div>
							</div>
							<br> -->
							<div id="cost_container">
								<div class="input-group double-input" id="cost_div">
									<span class="input-group-addon" id="basic-addon1">Cost</span> 
									<input name="minCost" type="text" class="form-control"
										placeholder="77.00"> 
									<input name="maxCost" type="text" class="form-control"
										placeholder="177.00">
										<span class="input-group-btn"
										aria-hidden="true">
										<!-- <button type="button" class="btn btn-default"
											onClick="addDiv('cost_container', 'cost', 'text'); return false;">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										</button> --> <br>
									</span>
								</div>
							</div>
							<br>
                            
							<!-- Date Purchased Range entry field -->
							<div id="purchasedDate_container">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">Date Purchased</span> 
									<input
										name="purchasedDate" type="text" id="purchasedDate" class="form-control"
										placeholder="YYYY-MM-DD  -  YYY-MM-DD">
								</div>
							</div>
							<br>
							
							<!-- Expiration Date Range entry field -->
                            <div id="expirationDate_container">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Expiration Date</span> 
                                    <input
                                        name="expirationDate" type="text" id="expirationDate" class="form-control"
                                        placeholder="YYYY-MM-DD  -  YYY-MM-DD">
                                </div>
                            </div>
                            <br>
							
<!-- 							Date Purchased Range entry field -->
<!-- 						 <div id="idContainer6" class="myClass"> -->
<!-- 								<div class="input-group double-input"> -->
<!-- 									<span class="input-group-addon" id="basic-addon1">Date -->
<!-- 										Purchased Range</span>  -->
<!-- 										<input name="purchasedDateStart" type="date" -->
<!-- 										class="form-control" placeholder="YYYY-MM-DD">  -->
<!-- 										<input -->
<!-- 										name="purchasedDateEnd" type="date" class="form-control" -->
<!-- 										placeholder="YYYY-MM-DD"> -->
<!-- 										<span class="input-group-btn" aria-hidden="true"> -->
<!--                                         <button class="btn btn-default" -->
<!--                                             onclick="showHelp()" type="button"> -->
<!--                                             <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> -->
<!--                                         </button> </span> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<br> -->
							

<!-- 							Expiration Date Range entry field -->
							 
<!-- 							<div id="idContainer7" class="myClass"> -->
<!-- 								<div class="input-group double-input"> -->
<!-- 									<span class="input-group-addon" id="basic-addon1">Expiration -->
<!-- 										Date Range</span> <input name="expirationDateStart" type="date" class="form-control" -->
<!-- 										placeholder="YYYY-MM-DD"> <input name="expirationDateEnd" -->
<!-- 										type="date" class="form-control" placeholder="YYYY-MM-DD"> -->
<!-- 										<span class="input-group-btn" aria-hidden="true"> -->
<!--                                         <button class="btn btn-default" -->
<!--                                             onclick="showHelp()" type="button"> -->
<!--                                             <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> -->
<!--                                         </button> </span> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<br> <input type="hidden" name="action" value="doInsert">
							<input type="hidden" name="table" value="property">
							
							
							
							<button type="submit" class="btn btn-default">Search</button>
							<button type="reset" class="btn btn-default">Clear</button>
						</form>
					</div>
				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>