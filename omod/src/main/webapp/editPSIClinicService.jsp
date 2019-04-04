<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:url var="saveUrl" value="/module/PSI/addPPSIClinicService.form" />

<form:form method="POST" action="${saveUrl}" modelAttribute="pSIServiceManagement">


<div class="container register-form">
	<div class="form">
    	<div class="note">
    	    
        	<p>Edit Service</p>
        	<p>${msg}</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                							
						Service Code
						<form:input path="code" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  	Service Category: 
                  			<form:select path="category" class="form-control selcls" required="required">
                  				<form:option value=""/>
					              <form:option value="Child Health"/>
					              <form:option value="ANC"/>
					              <form:option value="Delevery"/>
					              <form:option value="PNC"/>
					         </form:select>	
                   	</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	<label for="Service Code">Item Name</label>
						<form:input path="name" class="form-control" required="required"/>                  			
					</div>
                  	<div class="form-group">
                  	Service Provider: 
                  			<form:select path="provider" class="form-control selcls" required="required">                  			 
                  	 			  <form:option value=""/>
					              <form:option value="Doctor"/>
					              <form:option value="Consultant"/>
					              <form:option value="Delevery"/>
					              <form:option value="Nurse"/>     
							</form:select>	
					</div>
              	</div>
              	
              	<form:hidden path="sid" />
              	<div class="col-md-6">               		
                  	<div class="form-group">
                  	Unit Cost:
                  		<form:input path="unitCost" class="form-control" required="required" min="0" max="500"/>
                   	 	
                  	</div>
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
   	</div>
</div>       
	

</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>