<% 

String success = (String) request.getAttribute("success");

if (success != null) {

%>
<div class="alert alert-success alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<span class="glyphicon glyphicon-ok" aria-hidden="true" style="margin-right: 8px;"></span>
	Your changes have been saved. 
</div>

<% } %>