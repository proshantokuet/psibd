<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
 <div class="form-content" id="serviceProviderReports">
  
 </div>
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


  
<table id="serviceProvider" class="display">
	  <thead>
	        <tr>
	            <th>Category</th>	            
	            <th>Code</th>
	            <th>Item</th>
	            <th>Number of Service</th>	            
	            <th>Total</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="report" items="${ reports }">
	        <tr>
	            <td>${ report.category }</td>	            
	        	<td>${ report.code }</td>
	        	<td>${ report.item }</td>
	            <td>${ report.serviceCount }</td>
	            <td>${ report.total }</td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
</table>
