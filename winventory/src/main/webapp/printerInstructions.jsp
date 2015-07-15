<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
span.highlight{
	background-color: yellow;
	}
</style>
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
	<div class="list-group-item">
	<h5>
	<ol>
		
		<li>Get yourself a Dymo Printer compatible with your computer.</li>
		<br>
		<li> <span class="highlight">Do not plug in the printer until prompted to do so by the software installation.</span></li>
		<br>
		<li>Install the appropriate software for your Operating System by:</li>
		<br>
		<ol type="a">
			<li> Following this <a href="http://www.dymo.com/en-US/dymo-user-guides" target="_blank">link</a> and find the software compatible with your printer and download then run it.</li>
			<br>
			<left><span class="highlight">Only use the CD if you absolutely must, it's software tends to be out of date.</span></left>
			<br>
			<br>		
			<li>Insert the CD that came with your printer and follow the prompts for installation.</li>
			<br>
			</ol>
		<br>
		<li>Unbox your printer and follow its instructions to plug in all the necessary cables, but do not connect to your computer yet.</li>
		<br>
		<li>Load a roll of labels into the printer. </li>
		<br>
		<li>Connect the printer and make sure that the computer displays it in your list of eligible printers.</li>
		<br>
		<li>With all this completed you should be able to now print from the webpage!</li>
		<br>
		<li>When attempting to print for the first time, the page should ask you to trust the DYMO plug-in.  If you select
			 not to trust the plug-in, the barcode will not print</li>
	</ol>
	</h5>
	</div>
	<div class="center">
		<h1>Troubleshooting</h1>
	</div>
	<div class="list-group">
		<div class="list-group-item">
			<h4>No valid DYMO printers were found?</h4>
			<h5>Check your browser</h5>
			<p style="padding-left:3em">Only Safari and Internet Explorer are support page to printer functionality. If you would like to print directly from the page, please use another browser, such as
			Safari or Internet Explorer. Our apologies, but we aren't happy about it either.</p>
			<h5>Ensure You have a DYMO Printer</h5>
			<p style="padding-left:3em">Examples can be found <a href="http://www.dymo.com/en-US" target="_blank">here</a>.  If you do not have
			 a DYMO printer, the on-page print button will not work.</p>
		</div>
		<div class="list-group-item">
			<h4>Still Not Found?</h4>
			<h5>Check your printers list.</h5>
			<p style="padding-left:3em">In some cases, even after the correct drivers have been installed, your DYMO printer will not appear on your printer list.</p>
			<p style="padding-left:3em">View your computer's printer page and manually add it to your printer list.</p>
			<h5>Verify through DYMO application.</h5>
			<p style="padding-left:3em">In some cases, the page still may not recognize the printer; in these cases, we recommend opening the DYMO application that comes with the driver and manually printing a label.
			This may fix the issue.</p>
		</div>
		<div class="list-group-item">
			<h4>The Print Button clicks, but nothing appears to happen?</h4>
			<h5> Verify that the printer is plugged into your machine and turned on. </h5>
			<p style="padding-left:3em">Despite having all the necessary software installed, the DYMO API is not complex enough to check if a printer is actually connected to your machine.</p>
			<h5>Check Print Queue</h5>
			<p style="padding-left:3em"> Open your print queue and see if the job is waiting to be printed. If so, it is likely your printer is not connected to the computer, or the printer is off.</p>
			<p style="padding-left:3em"> Please connect the printer and check that it is on and see if it begins printing. If not, clear the print queue, and then attempt to print your job again</p>
		</div>
		<div class="list-group-item">
			<h4>Contact the Developers</h4>
			<p>If the problem persists, please feel free to contact the developers at
			703-DONT-TRY to further look into the issue.</p>
		</div>
	</div>
</body>
</html>