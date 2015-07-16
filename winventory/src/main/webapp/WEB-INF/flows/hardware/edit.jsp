<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
<link rel="shortcut icon"
	href="${contextPath}/resources/images/favicon.png">
<meta charset="UTF-8">
<title>Winventory | Edit Hardware</title>

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
				<div class="main">
					<div class="boom">
						<h2 class="center">Edit Hardware Info</h2>
					</div>
					<br>
					<div class="padme">
						<c:if test="${not empty error}">
							<jsp:include page="/WEB-INF/includes/error.jsp" />
						</c:if>
						<c:if test="${empty error}">
							<jsp:include page="/WEB-INF/includes/success.jsp" />

							<%@ page
								import="com.simoncomputing.app.winventory.domain.Hardware"%>
							<%@ page import="java.util.ArrayList"%>

							<%
							    Hardware hardware = (Hardware) request.getAttribute("hardware");
																																																																																																												if (hardware != null) {
							%>

							<form class="form-horizontal"
								action="edit?key=<%=hardware.getKey()%>" data-toggle="validator"
								role="form" method="post">
								<div class="form-group">
									<label for="type" class="col-sm-2 control-label">Type </label>
									<div class="col-sm-9 search-field">
										<input name="type" type="text" id="type"
											pattern="^[^\'\&quot]*$"
											class="form-control search-hardware-type"
											value="<%=hardware.getType()%>" required>
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
											value="<%=hardware.getDescription()%>" required>
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
											value="<%=hardware.getCost()%>" required>
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
											class="form-control" value="">
									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>
								<div class="form-group">
									<label for="condition" class="col-sm-2 control-label">Condition
									</label>
									<div class="col-sm-9">

										<select name="condition" class="form-control" required>

											<%@ page
												import="com.simoncomputing.app.winventory.domain.RefCondition"%>
											<%@ page import="java.util.ArrayList"%>

											<%
											    ArrayList<RefCondition> conditions = (ArrayList<RefCondition>) request.getAttribute("conditions");
																																																																																																																																																																																																for (int i = 0; i < conditions.size(); i++) {
											%>

											<option title="<%=conditions.get(i).getDescription()%>"
												value="<%=conditions.get(i).getCode()%>"><%=conditions.get(i).getCode()%></option>

											<%
											    }
											%>

										</select>

									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>
								<div class="form-group">
									<label for="serialNo" class="col-sm-2 control-label">Serial
										No </label>
									<div class="col-sm-9">
										<input name="serialNo" type="text" id="serialNo"
											pattern="^[^\'\&quot]*$" class="form-control"
											value="<%=hardware.getSerialNo()%>" required>
									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>

								<div class="form-group">
									<label for="date" class="col-sm-2 control-label">Software
										Installed </label>
									<div class="col-sm-9 search-field">
										<input name="software" type="text" class="form-control"
											placeholder="Comma seperated 1, 4, 7 etc." value="${commas}">
									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>

								<%
								    if (hardware.getUserId() != null) {
								%>
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
										<input name="username" type="text"
											class="form-control username-typeahead"
											value="<%=request.getAttribute("username")%>">
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

								<%
								    } else {
								%>
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
									<div class="col-sm-9">
										<input name="username" type="text" class="form-control"
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
												value="location" checked> Place in storage
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="date" class="col-sm-2 control-label">
										Location ID </label>
									<div class="col-sm-9">
										<input name="locID" type="number" class="form-control"
											value="<%=request.getAttribute("locationID")%>">
									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>

								<%
								    }
								%>

								<c:if test="${hardware.getIsActive()}">

									<div class="form-group">
										<div class="col-sm-10 col-sm-offset-2">
											<div class="checkbox">
												<label> <input value="true" name="isActive"
													type="checkbox" checked> Is Active
												</label>
											</div>
										</div>
										<div class="col-sm-10 col-sm-offset-2">
											<div class="help-block with-errors"></div>
										</div>
									</div>
								</c:if>

								<c:if test="${not hardware.getIsActive()}">

									<div class="form-group">
										<div class="col-sm-10 col-sm-offset-2">
											<div class="checkbox">
												<label> <input value="true" name="isActive"
													type="checkbox"> Is Active
												</label>
											</div>
										</div>
										<div class="col-sm-10 col-sm-offset-2">
											<div class="help-block with-errors"></div>
										</div>
									</div>
								</c:if>

								<div class="form-group">
									<div class="col-sm-10 col-sm-offset-2">
										<button type="submit" class="btn btn-primary">Submit</button>
										<button type="reset" class="btn btn-default"
											onClick="reloadConditions(); return false;">Reset</button>
										<a
											href="${contextPath}/hardware/view?key=<%=hardware.getKey()%>"
											class="btn btn-default">Cancel</a>
									</div>
									<div class="col-sm-10 col-sm-offset-2">
										<div class="help-block with-errors"></div>
									</div>
								</div>
								<input type="hidden" id="key" name="key"
									value="<%=hardware.getKey()%>">
							</form>

							<%
							    }
							%>
						
					</div>
					<div class="row">
						<div class="col-md-12">
							<br> <br>
							<hr>
							<br>
							<c:if test="${userInfo.hasPermission.deleteHardware}">
								<form action="${contextPath}/hardware/view" role="form"
									method="get" class="form-group">

									<input type="hidden" id="key" name="key"
										value="<%=hardware.getKey()%>"> <input type="hidden"
										id="delete" name="delete" value="true">
									<button type="submit" class="btn btn-danger btn-lg btn-center">Delete
										Forever</button>
								</form>
								<br>
								<br>
							</c:if>
						</div>
					</div>
					</c:if>

				</div>
				<br> <br>
			</div>
			<br> <br> <br> <br>
		</div>
	</div>
	<jsp:include page="/WEB-INF/includes/footer.jsp" />

	<script>
		var date = '${pageContext.request.getAttribute("hardware").getPurchaseDate()}';
		var month = date.substring(4, 7);

		if (month == 'Jan') {
			month = '01';
		} else if (month == 'Feb') {
			month = '02';
		} else if (month == 'Mar') {
			month = '03';
		} else if (month == 'Apr') {
			month = '04';
		} else if (month == 'May') {
			month = '05';
		} else if (month == 'Jun') {
			month = '06';
		} else if (month == 'Jul') {
			month = '07';
		} else if (month == 'Aug') {
			month = '08';
		} else if (month == 'Sep') {
			month = '09';
		} else if (month == 'Oct') {
			month = '10';
		} else if (month == 'Nov') {
			month = '11';
		} else if (month == 'Dec') {
			month = '12';
		}

		var day = date.substring(8, 10);
		var year = date.substring(24);

		var today = new Date();
		today = year + '-' + month + '-' + day;
		$("#date").val(today);//attr("value", today);

		var condition = '${pageContext.request.getAttribute("hardware").getCondition()}';
		$('select[name=condition]').val(condition);
	</script>
	<script>
		function reloadConditions() {
			var type = '${pageContext.request.getAttribute("hardware").getType()}';
			console.log(type);
			$("#type").val(type);
			var description = '${pageContext.request.getAttribute("hardware").getDescription()}';
			console.log(description);
			$("#description").val(description);
			var cost = '${pageContext.request.getAttribute("hardware").getCost()}';
			console.log(cost);
			$("#cost").val(cost);
			var serialNo = '${pageContext.request.getAttribute("hardware").getSerialNo()}';
			console.log(serialNo);
			$("#serialNo").val(serialNo);
			var condition = '${pageContext.request.getAttribute("hardware").getCondition()}';
			console.log(condition);
			$('select[name=condition]').val(condition);

			var date = '${pageContext.request.getAttribute("hardware").getPurchaseDate()}';
			var month = date.substring(4, 7);

			if (month == 'Jan') {
				month = '01';
			} else if (month == 'Feb') {
				month = '02';
			} else if (month == 'Mar') {
				month = '03';
			} else if (month == 'Apr') {
				month = '04';
			} else if (month == 'May') {
				month = '05';
			} else if (month == 'Jun') {
				month = '06';
			} else if (month == 'Jul') {
				month = '07';
			} else if (month == 'Aug') {
				month = '08';
			} else if (month == 'Sep') {
				month = '09';
			} else if (month == 'Oct') {
				month = '10';
			} else if (month == 'Nov') {
				month = '11';
			} else if (month == 'Dec') {
				month = '12';
			}

			var day = date.substring(8, 10);
			var year = date.substring(24);

			var today = new Date();
			today = year + '-' + month + '-' + day;
			$("#date").val(today);
		}
	</script>

</body>

</html>