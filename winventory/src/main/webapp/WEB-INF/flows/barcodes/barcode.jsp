<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<script
	src='https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
	
<script type="text/javascript">
$(document).ready(function() {
    var table = $('#hardwares').DataTable({
    	"pagingtype":"full"
    });
    $('#hardwares tr').on( 'click', 'td:eq(7)', function () {
    	var input;
    	var parentNode = $(this).parent();
    	var pk = parentNode.find("td:nth-child(1)").text();
    	var icon = parentNode.find("td:nth-child(8)");
        $(this).find("span").toggleClass('glyphicon glyphicon-remove').toggleClass('glyphicon glyphicon-repeat')
        if ( parentNode.hasClass('alert alert-warning') ) {
            parentNode.removeClass('alert alert-warning');
            input = $("#"+pk).attr({
            	value:null
            });
        }
        else {
            table.$('tr.alert alert-warning').removeClass('alert alert-warning');
            parentNode.addClass('alert alert-warning');
            $('<input>').attr({
            	type:'hidden',
            	id:pk,
            	name:'removeHw',
            	value:pk
            }).appendTo('form');
        }
    } );
 
/*     $('#button').click( function () {
        table.row('.selected').remove().draw( false );
    } ); */
} );
</script>
</head>
<body>
	<jsp:include page="/base.jsp"/>
    <div class="container-fluid">
	    <form id="form" class="form-group center" method="post" action="${contextPath}/barcodes/barcode">
       		<label>Barcode Entry</label>
       		<input style="width:50%;margin-left:25%" id="barcode" name="barcode" class="form-control center"
       			pattern=".{11,13}" placeholder="Scan Barcode Now" type="text" autocomplete="off" autofocus/>
	   		<input type="hidden" id="toSubmit" name="toSubmit" value=false/>
	   	</form>
	   	<c:if test="${not empty error}">
			<div class="alert alert-danger text-center">${error}</div>
		</c:if>
		<br> <br>
		<div class="row">
			<div class="col-xs-6 form-group">
				<label>Owner</label> <input id="person" name="person" class="form-control"
					type="text" value="${user.firstName}" readonly/>
			</div>
			<div class="col-xs-6 form-group">
				<label>Location</label> <input id="location" name="location" class="form-control"
					type="text" value="${location.description}" readonly/>
			</div>
			<c:if test="${not empty hardware}">
				<div class="col-xs-12">
					<label for="hardwares">Hardware</label>
					<table id="hardwares" class="table">
						<thead>
							<tr>
								<th>Key</th>
								<th>Type</th>
								<th>Cost</th>
								<th>Condition</th>
								<th>Description</th>
								<th>User</th>
								<th>Location</th>
								<th>Remove</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="i" items="${hardware}">
								<tr>
									<td><c:out value="${i.key}"/></td>
									<td><c:out value="${i.type}"/></td>
									<td><c:out value="${i.cost}"/></td>
									<td><c:out value="${i.condition}"/></td>
									<td><c:out value="${i.getShortDescription()}"/></td>
									<td><c:out value="${ub.read(i.userId).firstName}"/></td>
									<td><c:out value="${lb.read(i.locationId).description}"/></td>
									<td><span style="margin-left:15%" class="glyphicon glyphicon-remove"></span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
	</div>
	<div class="center">
	 	<button class="btn btn-default" onclick="finalSubmission()">Confirm Changes</button>
	</div>
</div>
<script>
function finalSubmission(){
	var hiddenInput = document.getElementById("toSubmit");
	hiddenInput.value=true;
	document.getElementById("form").submit();
}

/* function toggleRemove(obj, pk){
	var crntName = obj.parentNode.parentNode.className;
	nextName = '';
	var input;
	if (crntName==''){
		input = document.createElement("input");
		input.setAttribute("type","hidden");
		input.setAttribute("name","removeHw");
		input.setAttribute("id",pk);
		input.setAttribute("value",pk);
		document.getElementById("form").appendChild(input);
		nextName='alert alert-warning';
	} else {
		input = document.getElementById(pk);
		input.setAttribute("value",null);
	}
	obj.parentNode.parentNode.className=nextName;
} */
</script>
</body>
</html>