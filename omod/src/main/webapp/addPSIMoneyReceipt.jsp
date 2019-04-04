<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>


<c:url var="saveUrl" value="/module/PSI/addPSIMoneyReceipt.form" />


<form:form method="POST" action="${saveUrl}" modelAttribute="pSIMoneyReceipt">

	<div class="form-group">
		<div class="row">
			<div class="col-3">
				<label for="name">Name</label>
				<form:input path="patientName"/>
			</div>
		</div>
	</div>
	

	<div class="form-group">
		<div class="row">
			<div class="col-3">
				<div class="form-group">
					<input type="submit" value="Save" />
				</div>
			</div>
		</div>
	</div>

</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>