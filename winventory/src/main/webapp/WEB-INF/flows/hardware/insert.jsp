<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

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
			<jsp:include page="hwBase.jsp" />
			<div class="col-md-8">

				<div class="main">

					<div class="boom">
						<h2 class="center">Add Hardware</h2>
					</div>
					<div class="padme">
						<br>
						
						<c:if test="${not empty errors}">
							<div class="alert alert-danger" role="alert">
								<h3 class="error-header">Could not insert hardware</h3>
								<span class="sr-only">Errors</span>
								<c:forEach items="${errors}" var="error">
									<p class="error-msg">
										<span class="glyphicon glyphicon-exclamation-sign"
											aria-hidden="true"></span> ${error}
									</p>
								</c:forEach>
							</div>
						</c:if>
						
							<!-- include confirmation/error messages -->
							<jsp:include page="/WEB-INF/includes/error.jsp" />
							<jsp:include page="/WEB-INF/includes/success.jsp" />
							
						<form class="form-horizontal" action="insert"
							data-toggle="validator" role="form" method="post">
							<div class="form-group">
								<label for="type" class="col-sm-2 control-label">Type </label>
								<div class="col-sm-9 search-field">
									<input name="type" type="text" id="type"
										pattern="^[^\'\&quot]*$"
										class="form-control search-hardware-type" placeholder="Laptop"
										required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="col-sm-2 control-label">Description
								</label>
								<div class="col-sm-9">
									<input name="description" type="text" id="description"
										pattern="^[^\'\&quot]*$" class="form-control"
										placeholder="MacbookPro 13" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="cost" class="col-sm-2 control-label">Cost </label>
								<div class="col-sm-9">
									<input name="cost" type="text" id="cost" type="number"
										pattern="^[0-9]+(\.[0-9][0-9]?)*$" class="form-control"
										placeholder="70.00" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="date" class="col-sm-2 control-label">Date
									Purchased </label>
								<div class="col-sm-9">
									<input name="date" type="date" id="date" type="date"
										class="form-control" placeholder="" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<label for="date" class="col-sm-2 control-label">Condition
								</label>
								<div class="col-sm-9">

									<select name="condition" class="form-control" required>

										<%@ page
											import="com.simoncomputing.app.winventory.domain.RefCondition"%>
										<%@ page import="java.util.ArrayList"%>

										<%
										    ArrayList<RefCondition> conditions = (ArrayList<RefCondition>) request
										            .getAttribute("conditions");

										    if (conditions != null) {

										        for (int i = 0; i < conditions.size(); i++) {
										%>

										<option title="<%=conditions.get(i).getDescription()%>"><%=conditions.get(i).getCode()%></option>

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
								<label for="date" class="col-sm-2 control-label">Serial
									No </label>
								<div class="col-sm-9">
									<input name="serialNo" type="text" id="serialNo"
										pattern="^[^\'\&quot]*$" class="form-control"
										placeholder="1A2SDF6537H" required>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-2">
									<div class="radio">
										<label> <input type="radio" name="insertWith"
											value="user" checked> Assign to user
										</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="date" class="col-sm-2 control-label">Username
								</label>
								<div class="col-sm-9 search-field">
									<input name="username" type="text" class="form-control username-typeahead"
										placeholder="joe.shmo">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-2">
									<div class="radio">
										<label> <input type="radio" name="insertWith"
											value="location"> Place in storage
										</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="date" class="col-sm-2 control-label">
									Location ID </label>
								<div class="col-sm-9">
									<input name="locID" type="number" class="form-control"
										placeholder="12">
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2">
									<button type="submit" class="btn btn-default">Add</button>
									<button type="reset" class="btn btn-default">Clear</button>
								</div>
								<div class="col-sm-10 col-sm-offset-2">
									<div class="help-block with-errors"></div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>

	<jsp:include page="/WEB-INF/includes/footer.jsp" />

</body>

</html>