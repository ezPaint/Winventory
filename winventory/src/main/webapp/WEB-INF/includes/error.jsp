<% 

String error = (String) request.getAttribute("error");

if (error != null) {

%>
<div class="alert alert-warning alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<strong>Error:</strong> <%= error %>
</div>

<% } %>