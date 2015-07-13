<!-- Requirements for use:
	You must import the respective domain object (Hardware, Software, Location, etc)
	in the file that includes this one. 
	You must set the parameter "events" to the events returned by a EventBo call on an
	instance of this domain object (a list of events). -->
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="table-responsive">
	
	<h3>Events:</h3>
	<table class="table table-striped" id="resultsTable"
		style="display: full;">
		<thead>
			<tr>
				<th>Key</th>
				<th>Created</th>
				<th>Description</th>
				<th>Category</th>
			</tr>
		</thead>
		<tbody>

			
			<%@ page import="com.simoncomputing.app.winventory.domain.Event"%>
			<%@ page import="java.util.List"%>

			<%
			    List<Event> results = (List<Event>) request.getAttribute("events");

			    if (results != null) {

			        for (int i = 0; i < results.size(); i++) {
			%>
			<tr>
				<td><a href="${contextPath}/event/view?key=<%=results.get(i).getKey()%>"> 
				<%=results.get(i).getKey()%></a></td>
				<td><%=results.get(i).getDateCreated()%></td>
				<td><%=results.get(i).getDescription()%></td>
				<td><%=results.get(i).getCategory()%></td>
			</tr>
			<%
			    }
			    }
			%>
		</tbody>
	</table>
</div>