<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="${contextPath}/resources/images/favicon.png">
<title>Winventory</title>

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
<script src='${contextPath}/resources/js/validator.js'
	type="text/javascript"></script>

<!--  TYPEAHEAD  -->
<script src="${contextPath}/resources/js/typeahead.bundle.js"></script>
<script src="${contextPath}/resources/js/winventory-typeahead.js"></script>
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
						<h2 class="center">Edit Location Info</h2>
					</div>
					<br>
					<div class="padme">

						<%@ page
							import="com.simoncomputing.app.winventory.domain.Location"%>
						<%@ page import="java.util.ArrayList"%>

						<%
						    Location location = (Location) request.getAttribute("location");

							if (location != null) {
						%>

						<form class="form-horizontal" action="edit-location"
							data-toggle="validator" role="form" method="post">
							<div class="form-group">
								<label for="description" class="col-sm-2 control-label">Description </label>
								<div class="col-sm-9 search-field">
									<input name="description" type="text" id="description"
										pattern="^[^\'\&quot]*$"
										class="form-control search-hardware-type"
										value="<%=location.getDescription()%>" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="addressId" class="col-sm-2 control-label">Address ID </label>
								<div class="col-sm-9">
									<select name="addressId" class="form-control" required>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.Address"%>
										<%@ page import="java.util.ArrayList"%>

										<%
										    ArrayList<Address> addresses = (ArrayList<Address>) request
										            .getAttribute("addresses");

										    if (addresses != null) {

										        for (int i = 0; i < addresses.size(); i++) {
										%>

										<option value="<%=addresses.get(i).getKey()%>"><%=addresses.get(i).getName()%></option>

										<%
										    }
										    }
										%>

									</select>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="isActive" class="col-sm-2 control-label">Is Active
								</label>
								<div class="col-sm-9">
									<select name="isActive" class="form-control" required>
										<option title="true" value="true">True</option>
										<option title="false" value="false">False</option>
									</select>

								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" class="btn btn-primary">Submit
										</button>
									<button type="reset" class="btn btn-default"
										onClick="reloadConditions(); return false;">Reset
										</button>
									<a
										href="${contextPath}/location/view-location?key=<%=location.getKey()%>"
										class="btn btn-default">Cancel</a>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<input type="hidden" id="key" name="key"
								value="<%=location.getKey()%>">
						</form>

						<%
						    }
						%>

						<br>
						<div></div>
					</div>
				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />
	<script>
		var addressId = '${pageContext.request.getAttribute("location").getAddressId()}';
		$('select[name=addressId]').val(addressId);
		var isActive = '${pageContext.request.getAttribute("location").getIsActive()}';
		$('select[name=isActive]').val(isActive);
	</script>
	<script>
		function reloadConditions() {
			var description = '${pageContext.request.getAttribute("location").getDescription()}';
			$("#description").val(description);
			var addressId = '${pageContext.request.getAttribute("location").getAddressId()}';
			$('select[name=addressId]').val(addressId);
			var isActive = '${pageContext.request.getAttribute("location").getIsActive()}';
			$('select[name=isActive]').val(isActive);
		}
	</script>

</body>

</html>