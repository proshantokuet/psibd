<%@ include file="/WEB-INF/template/include.jsp"%>	
<div id="patientsyncReport">
	<div class="form-content">
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label>${totalTransferred}</label> &nbsp;&nbsp; Total
					Transferred
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label>${syncInProgress}</label> &nbsp;&nbsp; Sync In Progress
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label>${totalFailed}</label> &nbsp;&nbsp; Sync Failed
				</div>
			</div>
		</div>
	</div>
	<div style="overflow:auto;">
	<table id="table_id" class="display cell-border compact">
		<thead>
			<tr>
				<th>Sl</th>
				<th>actionType</th>
				<th>identifier</th>
				<th>Error</th>
			</tr>
		</thead>
		<tbody>
		<% int sl_d = 0; %>
			<c:forEach var="item" items="${ report }">
				<tr>
					<td><%=++sl_d%></td>
					<td>${ item.actionType }</td>
					<td>${ item.identifier }</td>
					<td>${ item.error }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>