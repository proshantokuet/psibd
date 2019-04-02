<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:url var="saveUrl" value="/module/PSI/addPsiClinic.form" />

<form:form method="POST" action="${saveUrl}" modelAttribute="pSIClinic">


<div class="container register-form">
	<div class="form">
    	<div class="note">
        	<p>Add Community Clinic.</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">                	
                   		Clinic Name : <input name="name" type="text" class="form-control" required placeholder="Clinic Name" value=""/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID: 	<input name="clinicId" type="text" class="form-control" required placeholder="Clinic Id" value=""/>
                   	</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	Category: 	<select name="category" required>
                  	 				<option value="">Select Category</option>
								    <option value="BEmOC">BEmOC</option>
								    <option value="CEmOC">CEmOC</option>
								    <option value="Vital">Vital</option>							    
							  </select>
					</div>
                  	<div class="form-group">
                   	Address: 	<input name="address" type="text" class="form-control" placeholder="Address" value=""/>
                  	</div>
              	</div>
              	<div class="col-md-6">               		
                  	<div class="form-group">
                   	DHIS2 ID: 	<input name="dhisId" required type="text" class="form-control" placeholder="DHIS2 Id" value=""/>
                  	</div>
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
   	</div>
</div>       
	

</form:form>



<%@ include file="/WEB-INF/template/footer.jsp"%>