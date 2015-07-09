function addDiv(container, id) {
	var newDiv = document.createElement('div');
	newDiv.innerHTML = "<br><div class=\"input-group extra-box\">"
			+ "<input name=\"" + id + "\" type=\"text\" class=\"form-control\">"
			+ "</div>";
	debugger;
	document.getElementById(container).appendChild(newDiv);
}