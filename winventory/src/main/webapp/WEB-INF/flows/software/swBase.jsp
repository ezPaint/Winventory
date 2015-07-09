<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Software's (left) sidebar -->
<div class="col-md-2">
	<div class="list-group">
		<a href="${contextPath}/winventory/software" class="list-group-item">
			<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				List</span>
		</a>
		<%-- <c:if test="${userInfo.hasPermission.createHardware}"> --%>
		<a href="${contextPath}/winventory/software/insert"
				class="list-group-item"> <span class="glyphicon glyphicon-plus"
				aria-hidden="true"> Add</span>
			</a>
		<%-- </c:if> --%>
		<a href="${contextPath}/winventory/software/search"
			class="list-group-item"> <span class="glyphicon glyphicon-search"
			aria-hidden="true"> Search</span>
		</a>
		<a href="${contextPath}/winventory/software/advancedsearch"
            class="list-group-item"> <span class="glyphicon glyphicon-search"
            aria-hidden="true"> Advanced Search</span>
        </a>
	</div>
</div>