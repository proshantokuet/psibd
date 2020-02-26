<%@ include file="/WEB-INF/template/include.jsp"%>
<div id = "patientsyncReport">
<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
                  		<label>${patientTransferred}</label> &nbsp;&nbsp; 
                  		Total Transferred
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<label >${patientToSync}</label>
                  	  	&nbsp;&nbsp; Sync In Progress
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label>${patientFailedSync}</label> &nbsp;&nbsp; Sync Failed
	                </div>
				</div>
				<!-- <div class="col-md-3">
					<div class="form-group">
	                   <button onclick="dhisPatientSyncReport()">View Failure Report</button>
	                </div>
				</div> -->
			</div>
</div>
<div style="overflow:auto;">
<table id="table_id" class="display cell-border compact">
		  <thead>
		        <tr>
		            <!-- <th>Patient ID</th>	 -->
		            <th>Clinic Name</th>   
		            <th>HID</th>
		            <!-- <th>Clinic Code</th> -->
		            <th>Error</th>
		            <th>Sync Started</th>	            
		            <th>Last Sync Date</th>
		        </tr>
		    </thead>
		    <tbody>
		    <c:forEach var="report" items="${ patientDhisReport }">
		        <tr>
		           <%--  <td>${ report.person_id }</td>	  --%> 
		           <td>${ report.clinic_name }</td>          
		        	<td>${ report.identifier }</td>
		        	<%-- <td>${ report.clinic_code }</td> --%>
		            
		            <td>${ report.error }</td>
		            <td>${ report.date_created }</td>
		            <td>${ report.date_changed }</td>
		        </tr>
		        </c:forEach>
		    </tbody>
	</table>
	</div>
</div>