<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="visitReportTitle"></div>
<div class="form-content">
	<div class="row">
		<div class="col-md-3">
			<div class="form-group">
				<label for="Service Code">${dashboard.newPatient }</label>
                	  	&nbsp;&nbsp; New Registration
                	</div>  
		</div>
		<div class="col-md-3">
			<div class="form-group">
                		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; 
                		Old Clients
            		</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
                		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
            		</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
                   <label> ${ dashboard_service_cotact_value } </label> &nbsp;&nbsp; Total Service Contact
               </div>
		</div>
	</div>
	<div class="row">
	
			<div class="col-md-3">
              	<div class="form-group">                							
				<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                	
           	</div>
           	<%-- <div class="col-md-3">
             	<div class="form-group">
                	<label for="Service Code">${dashboard.earned }</label>
				&nbsp;&nbsp; Revenue Earned                  			
		</div>
                	
            </div>
            <div class="col-md-3">
               <div class="form-group">
                   <label> ${ dashbaord_discount_value } </label> &nbsp;&nbsp; Total Discount
               </div>
           </div> --%>
           	
		
	</div>
</div>
<div style="overflow:auto;">
	<br/>
	
	<table id="visit_report" class="display">
		<thead>
		<tr>
			<th>SL</th>
			<th>Name</th>
			<th>HID</th>
			<th>Mobile Number</th>
			<th>Gender</th>
			<th>Age</th>
			<th>Registration Date</th>
			<th>Last Visit Date</th>
			<th>Visit Count</th>
		</tr>
		</thead>
		<tbody>
			<%int sl_v = 0;%>
			<c:forEach var="report" items="${visitReport }">
				<tr>
					<td><%=++sl_v%></td>
					<td>${report.patient_name}</td>
					<td>${report.hid }</td>
					<td>${report.mobile_number }</td>
					<td>${report.gender }</td>
					<td>${report.age }</td>
					<td>${report.reg_date }</td>
					<td>${report.last_visit_date}</td>
					<td>${report.visit_count }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>