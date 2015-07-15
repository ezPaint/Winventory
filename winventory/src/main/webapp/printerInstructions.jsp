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
</head>

<style>
	div.list-group-item{
		margin-left:25%;
		margin-right:25%;
	}
</style>

<body>
	<jsp:include page="/base.jsp"/>
	<div class="center">
		<h1>Printer Installation</h1>
	</div>
	<div class="list-group">
		<div class="list-group-item">
			<h4>Check your browser</h4>
			<p>Due to recent changes in Chrome, DYMO's software has become out-of-date and will not 
			function correctly within the Google Chrome browser.</p>
			<p>If you would like to print directly from the page, please use another browser, such as
			Safari or Internet Explorer. Our apologies, but we aren't happy about it either.</p>
		</div>
		<div class="list-group-item">
			<h4>Trust Us</h4>
			<p>When attempting to print for the first time, the page should ask you to trust the DYMO plug-in.  If you elect
			 not to trust the plug-in, the barcode will not print.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure You have a DYMO Printer</h4>
			<p>Examples can be found <a href="http://www.dymo.com/en-US" target="_blank">here</a>.  If you do not have
			 a DYMO printer, the on-page print button will not work.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure the DYMO Software is installed</h4>
			<p>Most DYMO printers require DYMO drivers installed to run correctly.
			Download the appropriate one for your Operating System from this <a href="http://www.dymo.com/en-US/dymo-user-guides" target="_blank">page</a>.</p>
			<p>Please also note that DYMO drivers ask that the printer not be plugged into the machine until the installation software asks for it.
			 We recommend that this instruction be carried out to ensure correct setup.</p>
			<p>Keep in mind that, in some cases, the drivers distributed with purchase will be out of date and will not
			work. Please be sure to download the latest version from the site above.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure that your machine recognizes your DYMO printer</h4>
			<p>In some cases, even after the correct drivers have been installed, your DYMO printer will not appear on your printer list.</p>
			<p>View your computer's printer page and manually add it to your printer list.</p>
			<p>In some cases, the page still may not recognize the printer; in these cases, we recommend opening the DYMO application that comes with the driver and manually printing a label.
			This may fix the issue.</p>
		</div>
		<div class="list-group-item">
			<h4>Verify your machine is connected to an active Dymo Printer </h4>
			<p>Despite having all the necessary software installed, the DYMO API is not complex enough to check if a printer is actually connected to your machine.</p>
			<p> Open your print queue and see if the job is waiting to be printed. If so, it is likely your printer is not connected to the computer, or the printer is off.</p>
			<p> Please connect the printer and check that it is on and see if it begins printing. If not, clear the print queue, and then attempt to print your job again</p>
		</div>
		<div class="list-group-item">
			<h4>Contact the Developers</h4>
			<p>If the problem persists, please feel free to contact the developers at
			703-DONT-TRY to further look into the issue.</p>
		</div>
	</div>
</body>
</html>