<jsp:include page="/base.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<jsp:include page="/WEB-INF/flows/events/eventBase.jsp"></jsp:include>
		<div class="col-xs-8">
			<jsp:include page="/WEB-INF/includes/error.jsp" />
			<div class="main">
				<jsp:include page="/WEB-INF/includes/events.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
