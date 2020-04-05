<%@ include file="/WEB-INF/template/include.jsp"%>
<div id = "moneyReceiptReport">
      	<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label >${transferredMoneyReceipt}</label>
                  	  	&nbsp;&nbsp;Money Receipt Transferred
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<label >${transferredServiceContact}</label>
                  	  	&nbsp;&nbsp; Service Contact Transferred
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label>${money_receipt_to_sync}</label> &nbsp;&nbsp; 
                  		Sync In Progress
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label>${sync_failed}</label> &nbsp;&nbsp; Sync Failed
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
	<table id="table_id_moneyreceipt" class="display cell-border compact">
		  <thead>
		        <tr>
		            <!-- <th>Patient ID</th>	 -->
		            <th>Clinic Name</th> 
		            <th>HID</th> 
		            <!-- <th>Clinic Code</th>  -->
		                          
		            <th>Money Receipt ID</th>
		            <th>Error</th>
		            <th>Sync Started</th>		            
		            <th>Last Sync Date</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach var="report" items="${ moneyReceiptDhisReport }">
		        <tr>
		            <%-- <td>${ report.person_id }</td>	 --%>
		            <td>${ report.clinic_name }</td>            
		        	<td>${ report.identifier }</td>
		        	<%-- <td>${ report.clinic_code }</td> --%>
		            
		            <td>${ report.mid }</td>
		            <td>${ report.error }</td>
		            <td>${ report.date_created }</td>
		            <td>${ report.date_changed }</td>
		        </tr>
		        </c:forEach>
		    </tbody>
	</table>
	</div>
		</div>