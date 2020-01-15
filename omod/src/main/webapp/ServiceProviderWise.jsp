<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
 <div class="form-content" id="serviceProviderReports">
  
 </div>
 <div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label for="Service Code">${provider_dashbaord.newRegistration }</label>
                  	  	&nbsp;&nbsp; New Registration
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${provider_dashbaord.oldClients } </label> &nbsp;&nbsp; 
                  		Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${provider_dashbaord.newClients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label> ${ provider_dashbaord.totalServiceContact } </label> &nbsp;&nbsp; Total Service Contact
	                </div>
				</div>
			</div>
			<div class="row">
			
 				<div class="col-md-3">
                	<div class="form-group">                							
						<label> ${provider_dashbaord.patientServed } </label>  &nbsp;&nbsp; Patients Served						
                   </div>
                  	
             	</div>
             	<div class="col-md-3">
               	<div class="form-group">
                  	<label for="Service Code">${provider_dashbaord.revenueEarned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ provider_dashbaord.totalDiscount } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div>
             	
 			
			</div>
		</div>


<div id="loading_prov" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<table id="serviceProvider" class="display">
	  <thead>
	        <tr>
	            <th>Category</th>	            
	            <th>Code</th>
	            <th>Item</th>
	            <th>Number of Service</th>	            
	            <th>Total Revenue</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="report" items="${ reports }">
	        <tr>
	            <td>${ report.category }</td>	            
	        	<td>${ report.code }</td>
	        	<td>${ report.item }</td>
	            <td>${ report.serviceCount }</td>
	            <td>${ report.total_ }</td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
</table>
