<!-- Requirements for use:
	You must import the respective domain object (Hardware, Software, Location, etc)
	in the file that includes this one. 
	You must set the parameter "events" to the events returned by a EventBo call on an
	instance of this domain object (a list of events). -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


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
<link type="text/css" rel="stylesheet"
	href='${contextPath}/resources/css/style.css'>

<script type="text/javascript">
	$(document).ready(function() {
		$('#resultsTable').DataTable({
			"pagingType" : "full"
		});
		document.getElementById("loader").style.visibility = "hidden";
		document.getElementById("resultsTable").style.display = "table";
	});
</script>

<div class="table-responsive">
	<br> <br>
	<h3>Events</h3>

	<br>
	<table class="table table-striped" id="resultsTable"
		style="display: full;">
		<thead>
			<tr>
				<th>Key</th>
				<th>Description</th>
				<th>Occurred</th>
				<th>By User</th>
				<th>Category</th>
			</tr>
		</thead>
		<tbody>

			<%@ page import="com.simoncomputing.app.winventory.domain.Event"%>
			<%@ page import="java.util.List"%>

			<c:if test="${events != null && events.size() > 0}">
				<c:forEach var="i" begin="0" end="${events.size() - 1}">
					<tr>
						<td><a
							href="${contextPath}/event/view?key=${events.get(i).getKey()}"
							class="btn btn-default">
								${events.get(i).getKey()}</a></td>
						<td>${fn:substring(events.get(i).getDescription(), 0, 90)}<c:if
								test="${fn:length(events.get(i).getDescription()) > 90}">...</c:if>
						</td>
						<td>${events.get(i).getDateCreated()}</td>
						<td><a
							href="${contextPath}/users/view?key=${events.get(i).getCreatorId()}">
								${events.get(i).getUser().getUsername()}</a></td>


						<td>${events.get(i).getCategory()}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>