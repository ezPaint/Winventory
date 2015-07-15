<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.simoncomputing.app.winventory.domain.Location"%>
<%@ page import="com.simoncomputing.app.winventory.util.Barcoder"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory</title>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />
<link type="text/css" rel="stylesheet"
	href='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css
' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>
<script
	src='https://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js'
	type="text/javascript"></script>
<script src="http://labelwriter.com/software/dls/sdk/js/DYMO.Label.Framework.latest.js"
	type="text/javascript" charset="UTF-8"> </script>
<script
	src='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js'
	type="text/javascript"></script>
<script src="http://labelwriter.com/software/dls/sdk/js/DYMO.Label.Framework.latest.js"
        type="text/javascript" charset="UTF-8"> </script>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>

<script type="text/javascript">

function printBarcode() {
	var text = '${barcode}';
	text=text.substring(0,text.length-1);
	try{
		var labelXml = '<?xml version="1.0" encoding="utf-8"?> <DieCutLabel Version="8.0" Units="twips"> <PaperOrientation>Landscape</PaperOrientation> <Id>Address</Id> <PaperName>30252 Address</PaperName> <DrawCommands> <RoundRectangle X="0" Y="0" Width="1581" Height="5040" Rx="270" Ry="270"/> </DrawCommands> <ObjectInfo> <BarcodeObject> <Name>BARCODE</Name> <ForeColor Alpha="255" Red="0" Green="0" Blue="0"/> <BackColor Alpha="255" Red="255" Green="255" Blue="255"/> <LinkedObjectName></LinkedObjectName> <Rotation>Rotation0</Rotation> <IsMirrored>False</IsMirrored> <IsVariable>False</IsVariable> <Text>'+text+'</Text> <Type>Ean13</Type> <Size>Small</Size> <TextPosition>Bottom</TextPosition> <TextFont Family=".Helvetica Neue DeskInterface" Size="10" Bold="False" Italic="False" Underline="False" Strikeout="False"/> <CheckSumFont Family=".Helvetica Neue DeskInterface" Size="10" Bold="False" Italic="False" Underline="False" Strikeout="False"/> <TextEmbedding>None</TextEmbedding> <ECLevel>0</ECLevel> <HorizontalAlignment>Center</HorizontalAlignment> <QuietZonesPadding Left="0" Right="0" Top="0" Bottom="0"/> </BarcodeObject> <Bounds X="331.2" Y="57.59995" Width="1796.513" Height="600"/> </ObjectInfo> </DieCutLabel>';
		var label = dymo.label.framework.openLabelXml(labelXml);
		var printers = dymo.label.framework.getPrinters();
		if (printers.length==0){
			document.getElementById("noPrinters").style.display="";
		}
		var printer = printers[0];
		var printerName = printer.name;
		label.print(printerName);
	} catch (err) {
		document.getElementById("warning").style.display="";
	}
}
</script>

</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="locationBase.jsp" />
			<div class="col-md-8">
				<jsp:include page="/WEB-INF/includes/error.jsp" />

				<div class="main">
					<div class="boom">
						<h2 class="center">Location Info</h2>
					</div>

					<div class="padme">
					
					<c:if test="${not empty errors}">
                    	<div class="alert alert-danger" role="alert">
                    		<h3 class="error-header">Could not delete location:</h3>
  							<span class="sr-only">Errors:</span>
  							<c:forEach items="${errors}" var="error">
  								<p class="error-msg">
  									<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  									${error}
  								</p>
  							</c:forEach>
						</div>
                    </c:if>

						<%
						    Location location = (Location) request.getAttribute("location");

						    if (location != null) {
						%>

						<div class="col-md-4">
							<img src="${contextPath}/getBarcodeImage?key=${location.getKey()}&table=4"
								class="img img-responsive">
								<br>
								<button onclick="printBarcode();" style="margin-left:40%" class="btn btn-success">Print</button>
								<br>
								<br>
								<div id="noPrinters" class="text-center" style="display:none">
									No valid DYMO printers were found.
								</div>
								<div id="warning" class="text-center" style="display:none">
									If you are experiencing difficulties printing, please visit this <a href="${contextPath}/printerInstructions.jsp">link</a>.
								</div>
						</div>
						<div class="col-md-8">
							<ul class="list-group" style="word-wrap: break-word;">
								<%@ page import="com.simoncomputing.app.winventory.domain.Address"%>

								<%
								    Address address = (Address) request.getAttribute("address");

								        if (address != null) {
								%>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Address</b>
									</div>
									<div class="col-md-8">
										<a href="${contextPath}/location/view-address?key=<%= address.getKey() %>"><%=address.getName()%></a>
									</div>
								</li>

								<%
								    }
								%>

								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Key</b>
									</div>
									<div class="col-md-8">
										<p><%=location.getKey() %></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Description</b>
									</div>
									<div class="col-md-4">
										<p><%=location.getDescription()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Is Active</b>
									</div>
									<div class="col-md-8">
										<p><%=location.getIsActive()%></p>
									</div>
								</li>
								<li class="list-group-item row">
									<div class="col-md-4">
										<b>Barcode</b>
									</div>
									<div class="col-md-8">
										<p>${barcode}</p>
									</div>
								</li>
							</ul>
						</div>

						<form class="form-horizontal pull-right" action="view-location" method="post">
							<%-- <c:if test="${userInfo.hasPermission.deleteLocation}"> --%>
							<input type="hidden" id="key" name="key"
								value="<%=location.getKey()%>">
							<button type="submit" class="btn btn-danger">Delete</button>
							<%-- </c:if> --%>
						<%-- <c:if test="${userInfo.hasPermission.updateLocation}"> --%>
						<a class="btn btn-default"
							href="edit-location?key=<%=location.getKey()%>" role="button">Edit</a> 
						<%-- </c:if> --%>
						</form>
						
						<div class="container-fluid"></div>
												
						<%
						    }
						%>
						
						<jsp:include page="/WEB-INF/includes/events.jsp" />

					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>