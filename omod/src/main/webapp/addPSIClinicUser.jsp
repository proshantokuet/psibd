<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:url var="saveUrl" value="/module/PSI/addPSIClinicUser.form" />

<form:form method="POST" action="${saveUrl}" modelAttribute="pSIClinicUser">


<div class="container register-form">
	<div class="form">
    	<div class="note">
        	<p>Assign User.</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name : <form:input path="userName" class="form-control" required="required"/>
                  	</div>
                  	
                   	<form:hidden path="psiClinicManagementId" value="${psiClinicManagementId}" />
             	</div>             	
              	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
   	</div>
</div>       
	

</form:form>



<%@ include file="/WEB-INF/template/footer.jsp"%>