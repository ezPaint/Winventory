<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Winventory | User</title>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">

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
<script
	src='https://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js'
	type="text/javascript"></script>
<script
	src="http://labelwriter.com/software/dls/sdk/js/DYMO.Label.Framework.latest.js"
	type="text/javascript" charset="UTF-8">
	
</script>

<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>


<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#hardwareResultsTable').DataTable({
							"pagingType" : "full"
						});
						document.getElementById("loader").style.visibility = "hidden";
						document.getElementById("hardwareResultsTable").style.display = "table";
					});
</script>
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
			<jsp:include page="userBase.jsp" />
			<div class="col-md-8">


				<div class="main">
					<div class="boom">
						<h2 class="center">User Info</h2>
					</div>
					<br>
					<div class="container-fluid">

						<!-- Include the success/delete/error messages -->
						<jsp:include page="/WEB-INF/includes/success.jsp" />
						<jsp:include page="/WEB-INF/flows/users/deleteConfirm.jsp" />
						<jsp:include page="/WEB-INF/includes/error.jsp" />

						<div class="row no-margin">


							<%@ page import="com.simoncomputing.app.winventory.domain.User"%>
							<%@ page import="java.util.ArrayList"%>

							<%
							    User user = (User) request.getAttribute("user");
																																		    if (user != null) {
							%>


							<!-- <div class="media"> -->
							<div class="col-md-4">
								<img class="profile-img-big"
									src="${contextPath}/resources/images/SC-Logo-Black-On-White.png">
							</div>

							<div class="col-md-8">
								<ul class="list-group" style="word-wrap: break-word;">

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Key</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getKey()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Username</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getUsername()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>First Name</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getFirstName()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Last Name</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getLastName()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Email</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getEmail()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Cell Phone</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getCellPhone()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Work Phone</b>
										</div>
										<div class="col-md-9">
											<p><%=user.getWorkPhone()%></p>
										</div>
									</li>

									<li class="list-group-item row">
										<div class="col-md-3">

											<b>Active</b>

										</div>
										<div class="col-md-9">
											<p><%=user.getIsActive()%></p>
										</div>
									</li>
									<li class="list-group-item row">
										<div class="col-md-3">
											<b>Role</b>
										</div>
										<div class="col-md-9">
											<p><%=user.getRoleTitle()%></p>
										</div>
									</li>
								</ul>
								<form method="get" action="${contextPath}/users/view"
									class="form-horizontal">
									<c:set var="isSelf" value="${userInfo.key == param.key }" />
									<c:if
										test="${userInfo.hasPermission.updateUser || (userInfo.hasPermission.updateSelf && isSelf)}">
										<a class="btn btn-warning btn-center" style="margin: auto;"
											href="edit?key=<%=user.getKey()%>" role="button">Edit</a>
									</c:if>
								</form>
							</div>

							<%
							    }
							%>




							<!-- <div id='loader' style="padding-top: 750px;">
							<div class='diamond'></div>
							<div class='diamond'></div>
							<div class='diamond'></div>
						</div> -->

							<div class="row"></div>
							<div class="user-hardware-title">
								<h2>User's Hardware</h2>
							</div>

							<%@ page
								import="com.simoncomputing.app.winventory.domain.Hardware"%>
							<%@ page import="java.util.ArrayList"%>

							<%
							    // Table of hardware the user owns
																																			
																																			ArrayList<Hardware> results = (ArrayList<Hardware>) request.getAttribute("results");

																																	    	if (results != null && results.size() != 0) {
							%>

							<div class="table-responsive">
								<table class="table table-striped" id="hardwareResultsTable"
									style="display: table;">
									<thead>
										<tr>
											<th>Key</th>
											<th>Type</th>
											<th>Cost</th>
											<th>Condition</th>
											<th>Purchase Date</th>
											<th>Description</th>
											<th>Active</th>
											<th>Serial No.</th>
										</tr>
									</thead>
									<tbody>
										<%
										    for (int i = 0; i < results.size(); i++) {
										%>
										<tr>
											<td><a href="view?key=<%=results.get(i).getKey()%>"
												class="btn btn-primary"> <%=results.get(i).getKey()%></a></td>
											<td><%=results.get(i).getType()%></td>
											<td><%=results.get(i).getCost()%></td>
											<td><%=results.get(i).getCondition()%></td>
											<td><%=results.get(i).getPurchaseDate()%></td>
											<td><%=results.get(i).getDescription()%></td>
											<td><%=results.get(i).getIsActive()%></td>
											<td><%=results.get(i).getSerialNo()%></td>
										</tr>
										<%
										    }
										%>
									</tbody>
								</table>
							</div>

							<%
							    } // endif(hardware results == null)
																																	    	else {
							%>
							<p class="user-hardware-message">This user owns no hardware.</p>

							<%
							    }
							%>
						</div>
						<!-- end padme -->


						<jsp:include page="/WEB-INF/includes/events.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
		<br> <br> <br>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
</body>

</html>