<%@ include file="/WEB-INF/template/include.jsp"%>
<div id = "patientsyncReport">

<div style="overflow:auto;">
<table id="table_id" class="display cell-border compact">
				<thead>
					<tr>
						<th>HID</th>
						<th>Patient Name</th>
						<th>Age</th>
						<th>Contact number</th>
						<th>Visit Type</th>
						<th>Visit Start</th>
						<th>Visit End</th>
						<th>Follow-up For</th>
						<th>Follow-up Date</th>
						<th>Status</th>
						<th>Result</th>
						<th>Action</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="report" items="${ followUpReport }">
						<tr>
							<td>${ report.identifier }</td>
							<td>${ report.patientName }</td>
							<td>${ report.age }</td>
							<td>${ report.contactNumber }</td>
							<td>${ report.visitType }</td>
							<td>${ report.visitStart }</td>
							<td>${ report.visitEnd }</td>
							<td>${ report.followUpFor }</td>
							<td>${ report.followUpDate }</td>
							<td>${ report.followUpStatus }</td>
							<td>${ report.respondResult }</td>
							<td><div style="padding-top: 5px;"><a class="btn btn-primary"  onclick="openFollowUpMOdal('${report.visitUuid}','${report.encounterUuid}',${report.valueCoded},'${report.followUpFor}')"> Follow-Up</a></div></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</div>