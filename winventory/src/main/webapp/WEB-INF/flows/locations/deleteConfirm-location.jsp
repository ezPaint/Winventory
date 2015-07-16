<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ page
							import="com.simoncomputing.app.winventory.domain.Location"%>
<% 

String delete = (String) request.getAttribute("delete");

if (delete != null) {	
						    Location location = (Location) request.getAttribute("location");

						    if (location != null) {
						%>
<c:if test="${userInfo.hasPermission.deleteLocation}">
<div class="alert alert-warning alert-dismissible" role="alert" >
	<div class="row no-margin">
		<div class="col-md-12">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" style="margin-right: 8px;"></span>
			Are you sure you want to delete this item forever? If you 
			delete it forever, it will be as if it never existed in the database, other than an event marking its deletion.
			If you deactivate it, then it will still exist in the history, but will be marked as "deactivated."
			<p  class="text-center" style="margin-top: 30px;"> <strong>Only
			delete this item if you are absolutely sure you want it out of the system <em>FOREVER</em>.</strong></p>
			<p class="text-center"> <strong>Otherwise, deactivate the item.</strong></p>
		</div>
	</div>
	<br>
	<div class="row no-margin delete-confirm" style="margin-top: 20px;">
		<!-- <div class="col-md-1"></div> -->
		<div class="col-md-3"></div>
		
		<div class="col-md-2">
		<form method="post" action="" class="pull-right">
			<button type="button" class="btn btn-default " data-dismiss="alert" aria-label="Close" >Cancel</button>
		</form>
		</div>
		
		<div class="col-md-2" >
		
		<form method="post" action="${contextPath}/location/edit-location" class="" style="margin:auto;">				
							<input type="hidden" id="button" name="button" value="Update">
							<input type="hidden" id="key" name="key" value="<%=location.getKey()%>">
							<input name="description" type="hidden" id="description" value="<%=location.getDescription()%>">
							<input name="addressId" type="hidden" id="addressId" value="<%=location.getAddressId()%>">
                            <%-- <input name="isActive" type="hidden" id="isActive" value="<%=location.getIsActive()%>"> --%>
							
							<button type="submit" class="btn btn-primary " >Deactivate</button>
							
		</form>
		
		</div>
		
		<div class="col-md-2">
		
			<form method="post" action="${contextPath}/location/delete-location" class="pull-left">
							
							<input type="hidden" id="key" name="key"
								value="<%=location.getKey()%>">
							
							<button type="submit" class="btn btn-danger " >Yes, I'm sure</button>
							
			</form>
		
		</div>
		
		<div class="col-md-3"></div>
		
		</div>
	</div>
</c:if>

<% } } %>