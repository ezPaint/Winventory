<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | Software</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/normalize.css' />
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/bootstrap.css' />

<script src='${contextPath}/resources/js/actions.js'
	type="text/javascript"></script>
<script
	src="http://labelwriter.com/software/dls/sdk/js/DYMO.Label.Framework.latest.js"
	type="text/javascript" charset="UTF-8">
	
</script>
<script src='${contextPath}/resources/js/jquery-1.11.3.min.js'
	type="text/javascript"></script>
<script src='${contextPath}/resources/js/bootstrap.min.js'
	type="text/javascript"></script>

<script>
	function printBarcode() {
		var text = '${barcode}';
		text = text.substring(0, text.length - 1);
		try {
			var labelXml = '<?xml version="1.0" encoding="utf-8"?> <DieCutLabel Version="8.0" Units="twips"> <PaperOrientation>Landscape</PaperOrientation> <Id>Address</Id> <PaperName>30252 Address</PaperName> <DrawCommands> <RoundRectangle X="0" Y="0" Width="1581" Height="5040" Rx="270" Ry="270"/> </DrawCommands> <ObjectInfo> <BarcodeObject> <Name>BARCODE</Name> <ForeColor Alpha="255" Red="0" Green="0" Blue="0"/> <BackColor Alpha="255" Red="255" Green="255" Blue="255"/> <LinkedObjectName></LinkedObjectName> <Rotation>Rotation0</Rotation> <IsMirrored>False</IsMirrored> <IsVariable>False</IsVariable> <Text>'
					+ text
					+ '</Text> <Type>Ean13</Type> <Size>Small</Size> <TextPosition>Bottom</TextPosition> <TextFont Family=".Helvetica Neue DeskInterface" Size="10" Bold="False" Italic="False" Underline="False" Strikeout="False"/> <CheckSumFont Family=".Helvetica Neue DeskInterface" Size="10" Bold="False" Italic="False" Underline="False" Strikeout="False"/> <TextEmbedding>None</TextEmbedding> <ECLevel>0</ECLevel> <HorizontalAlignment>Center</HorizontalAlignment> <QuietZonesPadding Left="0" Right="0" Top="0" Bottom="0"/> </BarcodeObject> <Bounds X="331.2" Y="57.59995" Width="1796.513" Height="600"/> </ObjectInfo> </DieCutLabel>';
			var label = dymo.label.framework.openLabelXml(labelXml);
			var printers = dymo.label.framework.getPrinters();
			if (printers.length == 0) {
				document.getElementById("noPrinters").style.display = "";
			}
			var printer = printers[0];
			var printerName = printer.name;
			label.print(printerName);
		} catch (err) {
			document.getElementById("warning").style.display = "";
		}
	}
</script>

</head>

<body>
	<jsp:include page="/base.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="swBase.jsp" />
			<div class="col-md-8">
				<div class="main">
					<div class="boom">




						<h2 class="center">Application Info</h2>
					</div>
					<div class="padme">
						<div class="container-fluid">
							<div class="row no-margin">

								<!-- include confirmation/error messages -->
								<jsp:include page="/WEB-INF/includes/error.jsp" />
								<jsp:include page="/WEB-INF/includes/success.jsp" />
								<jsp:include page="/WEB-INF/flows/software/deleteConfirm.jsp" />

								<!-- Get the information of the software object selected by the user to view. -->
								<%@ page
									import="com.simoncomputing.app.winventory.domain.Software"%>
								<%@ page import="java.util.ArrayList"%>

								<%
									Software software = (Software) request.getAttribute("software");

									if (software != null) {
								%>


								<div class="col-md-4">
									<img
										src="${contextPath}/getBarcodeImage?key=${software.getKey()}&table=3"
										class="img img-responsive"> <br>
									<button onclick="printBarcode();" style="margin-left: 40%"
										class="btn btn-success">Print</button>
									<br> <br>
									<div id="noPrinters" class="text-center" style="display: none">
										No valid DYMO printers were found.</div>
									<div id="warning" class="text-center" style="display: none">
										If you are experiencing difficulties printing, please visit
										this <a href="${contextPath}/printerInstructions.jsp">link</a>.
									</div>
								</div>
								<div class="col-md-8">
									<ul class="list-group" style="word-wrap: break-word;">

										<!-- Displays the software's key -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Key</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getKey()%></p>
											</div>
										</li>

										<!-- Displays the software's name -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Name</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getName()%></p>
											</div>
										</li>

										<!--  Displays the software's serial number -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Serial Number</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getSerialNo()%></p>
											</div>
										</li>

										<!-- Displays the software's license key -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>License Key</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getLicenseKey()%></p>
											</div>
										</li>

										<!-- Displays the software's version -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Version</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getVersion()%></p>
											</div>
										</li>

										<!-- Displays the software's cost -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Cost</b>
											</div>
											<div class="col-md-9">
												<p><%=String.format("%.2f", software.getCost())%></p>
											</div>
										</li>

										<!-- Displays the software's purchase date -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Date Purchased</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getPurchasedDate()%></p>
											</div>
										</li>

										<!-- Displays the software's expiration date -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Expiration Date</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getExpirationDate()%></p>
											</div>
										</li>

										<!-- Displays the software's description -->
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Description</b>
											</div>
											<div class="col-md-9">
												<p><%=software.getDescription()%></p>
											</div>
										</li>
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Is Active?</b>
											</div>
											<div class="col-md-9">
												<p><%="" + software.getIsActive()%></p>
											</div>
										</li>
										<li class="list-group-item row">
											<div class="col-md-3">
												<b>Barcode</b>
											</div>
											<div class="col-md-9">
												<p>
													<c:out value="${barcode}"></c:out>
												</p>
											</div>
										</li>
									</ul>
									<form method="get" action="${contextPath}/software/view"
										class="form-horizontal">
										<c:set var="isSelf" value="${userInfo.key == param.key }" />
										<c:if test="${userInfo.hasPermission.updateSoftware}">
											<a class="btn btn-warning btn-center" style="margin: auto;"
												href="edit?key=<%=software.getKey()%>" role="button">Edit</a>
										</c:if>
									</form>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12"></div>
							</div>

							<%
								} else {
							%>
							<br>
							<div class="alert alert-danger" role="alert">Either no key
								was given, or the key does not exist</div>
							<%
								}
							%>
							<%@ page
								import="com.simoncomputing.app.winventory.domain.Software"%>
							<%@ page import="com.simoncomputing.app.winventory.util.Barcoder"%>






							<div class="table-responsive">
								<br> <br>
								<h3>
									Hardware using
									<%=software.getName()%></h3>

								<br>
								<table class="table table-striped" id="resultsTable"
									style="display: full;">
									<thead>
										<tr>
											<th>Key</th>
											<th>Type</th>
											<th>Description</th>
										</tr>
									</thead>
									<tbody>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.Hardware"%>
										<%@ page import="java.util.ArrayList"%>
										<c:if test="${hardware != null && hardware.size() > 0}">
											<c:forEach var="i" begin="0" end="${hardware.size() - 1}">
												<tr>
													<td><a
														href="${contextPath}/hardware/view?key=${hardware.get(i).getKey()}"
														class="btn btn-primary"> ${hardware.get(i).getKey()}</a></td>
													<td>${hardware.get(i).getType()}</td>
													<td>${hardware.get(i).getDescription()}</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>





							<jsp:include page="/WEB-INF/includes/events.jsp" />

						</div>
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