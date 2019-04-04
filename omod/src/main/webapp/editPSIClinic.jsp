<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:url var="saveUrl" value="/module/PSI/addPsiClinic.form" />

<form:form method="POST" action="${saveUrl}" modelAttribute="pSIClinic">


<div class="container register-form">
	<div class="form">
    	<div class="note">
        	<p>Edit Community Clinic.</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name : <form:input path="name" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID: 	: <form:input path="clinicId" class="form-control" required="required"/>
                   	</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	Category: 	<form:select path="category" class="form-control selcls" required="required">
                  				<form:option value=""/>
					              <form:option value="BEmOC"/>
					              <form:option value="CEmOC"/>
					              <form:option value="Vital"/>					             
					         </form:select>
					</div>
                  	<div class="form-group">
                   	Address: 	<form:input path="address" class="form-control" required="required"/>                	
                   	
                  	</div>
              	</div>
              	<form:hidden path="cid" />
              	<div class="col-md-6">               		
                  	<div class="form-group">
                   	DHIS2 ID: <form:input path="dhisId" class="form-control" required="required"/>
                  	</div>
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
   	</div>
</div>       
	

</form:form>



<%@ include file="/WEB-INF/template/footer.jsp"%>