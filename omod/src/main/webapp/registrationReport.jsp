<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="regReportTile"></div>
		<div class="form-content">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard_new_reg } </label> &nbsp;&nbsp; New Registration
              		</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; Old Clients
              		</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
			</div>
		</div>
	<div style="overflow:auto;">
	<br/>
		<table id="reg_report" class="display">
			<thead>
				<tr>
					<th>SL</th>
					<th>Patient Name</th>
					<th>UIC</th>
					<th>Health Id</th>
					<th>Mobile No</th>
					<th>Gender</th>
					<th>Registration Date</th>
					<th>Age</th>
					<th>Union/Municipality/CC</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%int sl_r = 0;%>
				<c:forEach var="report" items="${regReport }">
					<tr>
						<td><%=++sl_r%></td>
						<td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${report.patient_name }</a></td>
						<td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${report.uic }</a></td>
						<td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${report.health_id }</a></td>
						<td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${report.mobile_no }</a></td>
						<td>${report.gender }</td>
						<td>${report.register_date }</td>
						<td>${report.age }</td>
						<td>${report.cc }</td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>