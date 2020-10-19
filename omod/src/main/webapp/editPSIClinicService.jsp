<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Edit Clinic Service" otherwise="/login.htm" />

<c:url var="saveUrl" value="/module/PSI/addPPSIClinicService.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicServiceList.form?id=${pSIServiceManagement.psiClinicManagement.cid}" />




<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
    	    
        	<p>Edit Service</p>
        	
       	</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<form:form method="POST"  id="serviceForm" action="${saveUrl}" modelAttribute="pSIServiceManagement">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                							
						Service Code
						<form:input style="height: 39px;" path="code" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  	Service Category: 
                  			<form:select path="category" class="form-control selcls" required="required">
                  				 <form:option value=""/>
                  				<!--    <form:option value="Child Health"/>					              
					              <form:option value="Family Planning"/>
					              <form:option value="Lab Services"/>
					              <form:option value="Maternal Health"/>
					              <form:option value="NID-Immunization"/>
					              <form:option value="Non ESD Services"/>
					              <form:option value="Other Health"/>
					              <form:option value="Pharmacy Service"/>
					              <form:option value="Referral Cases"/> -->
					              <c:forEach items="${services}" var="service"> 
					              	 <form:option value="${service.categoryName}" label="${service.categoryName}"/>					              
					              </c:forEach>	
					         </form:select>	
                   	</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	<label for="Service Code">Item Name</label>
						<form:input style="height: 39px;" path="name" class="form-control" required="required"/>                  			
					</div>
                  	<div class="form-group">
                  	Service Provider: 
                  			<form:select path="provider" class="form-control selcls" required="required">                  			 
                  	 			 
					             <form:option value=""/>
					              <form:option value="Doctor"/>
					              <form:option value="Counselor"/>
					              <form:option value="Paramedic(Static)"/>
					              <form:option value="Lab Technician"/>
					              <form:option value="Paramedic(Satellite)"/>					              
					              <form:option value="CSP"/>
					              <form:option value="Pharmacist"/>   
							</form:select>	
					</div>
              	</div>
              	
              	<form:hidden path="sid" />
              	<form:hidden path="psiClinicManagement" value="${pSIServiceManagement.psiClinicManagement.cid}"/>
              	
              	 <div class="col-md-6">
              		
              		<div class="form-group">
                  	Unit Cost:
                  		<form:input style="height: 39px;" path="unitCost" class="form-control" required="required" min="0"/>
                   	 	
                  	</div> 

                  	
                  	<div class="form-group">
                  	Discount (%) for Poor:
                  		<form:input style="height: 39px;" path="discountPoor" class="form-control" required="required" min="0"/>
                   	 	
                  	</div>           		
                  	
              	</div>
              	<div class="col-md-6">
              		
              		<div class="form-group">
                  	Discount (%) for PoP:
                  		<form:input style="height: 39px;" path="discountPop" class="form-control" required="required" min="0"/>
                   	 	
                  	</div> 

                  	
                  	<div class="form-group">
                  	Discount (%) for Able to Pay:
                  		<form:input style="height: 39px;" path="discountAblePay" class="form-control" required="required" min="0"/>
                   	 	
                  	</div>            		
                  	
              	</div>
              	
              	<div class="col-md-6">               		
                  	<div class="form-group">
                  	Gender 
                  			<form:select path="gender" class="form-control selcls">                  			 
                  	 			  <form:option value=""> Please Select </form:option>
					              <form:option value="M">Male</form:option>
					              <form:option value="F">Female</form:option>
					              <form:option value="O">Others</form:option>
							</form:select>	
					</div>
					
					<div class="form-inline form-group">
                  		<label for="email">Year's</label>
						 <form:input path="yearFrom" value="0" class="input-size" min="0"  placeholder="Year's"/>
						 <label for="email">Month's</label>
						 <form:input path="monthFrom" value="0" class="input-size" min="0"  placeholder="Month's"/>
						 <label for="email">Day's</label>
						<form:input path="daysFrom" value="0" class="input-size" min="0"  placeholder="Day's"/>
                  	</div> 
              	</div>
              	<div class="col-md-6">
                  	<div class="form-inline form-group">
                  		<label for="email">Year's</label>
						 <form:input path="yearTo" value="0" class="input-size"  min="0" placeholder="Year's"/>
						 <label for="email">Month's</label>
						 <form:input path="monthTo" value="0" class="input-size" min="0"  placeholder="Month's"/>
						 <label for="email">Day's</label>
						<form:input path="daysTo" value="0" class="input-size" min="0"  placeholder="Day's"/>
                  	</div>             		
                  	
              	</div>
              	<c:if test="${!pSIServiceManagement.voided}">
				<div class="col-md-6">
					<div class="form-inline form-group">
						<label>Disable Service</label>
						<div>
							<input type="radio" name="disableService" id="yesDisable" value="yes"> Yes
						</div>
						<div style ="margin-left: 10px">
							<input type="radio" name="disableService" id="noDisable" value="no"> No
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${pSIServiceManagement.voided}">
				<div class="col-md-6">
					<div class="form-inline form-group">
					<label>Enable Service</label>
						<input type="radio" name="enableService" id="enableService" value="yes"> Yes </td>
					</div>
				</div>
				</c:if>
			</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/service.js"></script>
<%@ include file="/WEB-INF/template/footer.jsp"%>