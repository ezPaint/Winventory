<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ page
							import="com.simoncomputing.app.winventory.domain.Software"%>
<% 

String delete = (String) request.getAttribute("delete");

if (delete != null) {	
						    Software software = (Software) request.getAttribute("software");

						    if (software != null) {
						%>
<c:if test="${userInfo.hasPermission.deleteSoftware}">
<div class="alert alert-warning alert-dismissible row" role="alert" style="margin-left:0px; margin-right:0px;">
		<div class="col-md-6">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" style="margin-right: 8px;"></span>
		Are you sure you want to delete this item?
		</div>
		<div class="col-md-1"></div>
		<div class="col-md-5" style="margin-top: -5px;">
		<form method="post" action="${contextPath}/software/delete" class="form-horizontal pull-right">
							
							<input type="hidden" id="key" name="key"
								value="<%=software.getKey()%>">
							<button type="button" class="btn btn-default " data-dismiss="alert" aria-label="Close">Cancel</button>
							<button type="submit" class="btn btn-danger " style="margin-left: 3px;">Yes, I'm sure</button>
							
		</form>
		</div>

	</div>
</c:if>

<% } } %>