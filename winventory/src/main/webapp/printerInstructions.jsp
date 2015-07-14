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
			Safari or Firefox.</p>
		</div>
		<div class="list-group-item">
			<h4>Trust Us</h4>
			<p>When attempting to print for the first time, the page should ask you to trust the DYMO plug-in.  If you do not,
			the barcode will not print.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure You have a DYMO Printer</h4>
			<p>Examples can be found <a href="http://www.dymo.com/en-US" target="_blank">here</a>.  If you do not have
			 a DYMO printer, the on-page print button will not work.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure the DYMO Software is installed</h4>
			<p>Most DYMO printers require DYMO drivers installed to run correctly.
			Download the appropriate one from this <a href="http://www.dymo.com/en-US/dymo-user-guides" target="_blank">page</a>.</p>
			<p>Keep in mind that, in some cases, the drivers distributed with purchase will be out of date and will not
			work. Please be sure to download the latest version from the site above.</p>
		</div>
		<div class="list-group-item">
			<h4>Ensure that your machine recognizes your DYMO printer</h4>
			<p>In some cases, even after the correct drivers have been installed, your DYMO printer will not appear on your printer list.</p>
			<p>View your computer's printer page and manually add it to your printer list.</p>
			<p>In some cases, the page still may not recognize the printer; in these cases, we recommend opening the DYMO application that comes with the driver and manually printing a label. In some cases, this fixes
			the issue.</p>
		</div>
		<div class="list-group-item">
			<h4>Contact the Developers</h4>
			<p>If the problem persists, please feel free to contact the developers at
			703-DONT-TRY to further look into the issue.</p>
		</div>
	</div>
</body>
</html>