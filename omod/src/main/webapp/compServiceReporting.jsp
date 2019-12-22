<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="compServiceReporting"></div>
<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_reg } </label> &nbsp;&nbsp; New Registration
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_service_cotact_value } </label> &nbsp;&nbsp; 
                  		Total Service Contact
              		</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
	                    <label> ${dashboard.servedPatient } </label> &nbsp;&nbsp; Patients Served
	                </div>
				</div>
				 <div class="col-md-3">
	                <div class="form-group">
	                    <label for="Service Code">${dashboard.earned }</label>
	                    &nbsp;&nbsp; Revenue Earned
	                </div>
	            </div>
	             <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ dashbaord_discount_value } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div>
			</div>
</div>
<div style="overflow:auto;">
<br/>

	<table id="comp_service_reporting" class="display">
				<thead>
					<tr>
						<th rowspan="2">Sl</th>
						<th rowspan="2" >Service Code</th>
						<th rowspan="2">Service Name</th>
						<th colspan="4">Service Contact</th>
						<th colspan="4">Revenue</th>
						<th colspan="4">Discount</th>
					</tr>
					<tr>
						<% for(int i = 0; i < 3; i++) {%>
					 	<th>Static</th>
					 	<th>CSP</th>
					 	<th>Satellite</th>
					 	<th>Total</th>
					 	<% } %>
					</tr>
				</thead>
				<tbody>
				 <% int sl_c = 0; %>
			       	<c:if test="${not empty compReport }">
						<c:forEach var="report" items="${ compReport }">
					        <tr>
					        	
					        	 <td><%=++sl_c%></td>
					             <td>${ report.service_code }</td>	             
					        	 <td>${ report.service_name }</td> 
					        	 <td>${ report.service_contact_static }</td>
					             <td>${ report.service_contact_csp }</td>
					             <td>${ report.service_contact_satellite }</td>
					             <td>${ report.service_contact_total }</td>
					             <td>${ report.revenue_static }</td>
					             <td>${ report.revenue_csp }</td>
					             <td>${ report.revenue_satellite }</td>
					             <td>${ report.revnue_total }</td>
					             <td>${ report.discount_static }</td>
					             <td>${ report.discount_csp }</td>
					             <td>${ report.discount_satellite }</td>
					             <td>${ report.discount_total }</td>
					             
					        </tr>
				       </c:forEach>
				    </c:if>
				</tbody>
	</table>
</div>