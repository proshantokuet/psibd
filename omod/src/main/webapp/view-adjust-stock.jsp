<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>

<c:url var="cancelUrl" value="/module/PSI/adjust-history.form?id=${id}" /> 


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Adjust Details of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>
        	
       	</div>
		</div>
				<div class="form-content">
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>1. Adjust ID:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${adjustStock.adjustId }</label>  
					</div>
				</div>
				<div class="row">
					<div class="col-lg-2 form-group">
						<label >2. Product ID:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${adjustStock.productId }</label>  
					</div>
				</div>		
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>3. Product Name:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${adjustStock.productName }</label>  
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>4. Adjust Date:</label> 
					</div>
					<div class="col-lg-3 form-group">
					   	<label for="receiveDate">${adjustStock.adjustDate }</label>  
					   
					</div>
				</div>					
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>5. Previous Stock:</label> 
					</div>
					<div class="col-lg-3 form-group">
					   	<label for="receiveDate">${adjustStock.previousStock }</label>  
					   
					</div>
				</div>	
				
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>6. Adjusted Stock:</label> 
					</div>
					<div class="col-lg-3 form-group">
					   	<label for="receiveDate">${adjustStock.changedStock }</label>  
					   
					</div>
				</div>	
				
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>7. Reason :</label> 
					</div>
					<div class="col-lg-3 form-group">
					   	<label for="receiveDate">${adjustStock.adjustReason }</label>  
					   
					</div>
				</div>	
				
						
				<div class="text-center">
	                <a href="${cancelUrl}">Back</a>
	            </div>
	           </div>
   	</div>     


<%@ include file="/WEB-INF/template/footer.jsp"%>