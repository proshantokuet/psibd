<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>

<c:url var="saveUrl" value="/module/PSI/addPsiClinic.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicSpotList.form?id=${id}" />
<%
//String users = (String)session.getAttribute("users");
//String userIds = (String)session.getAttribute("userIds");
//String message = (String)session.getAttribute("message");
%>

<openmrs:require privilege="Add Clinic Spot" otherwise="/login.htm" />


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Add Community Clinic Spot</p>
        	
       	</div>
		
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
	    <form:form method="POST"  id="spotInfo" action="ff" modelAttribute="pSIClinicSpot">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Spot Name <form:input path="name" style="height: 39px;" class="form-control" required="required" autocomplete="off" tabindex="1"/>
                  	</div>
                  	<div class="form-group">
                  		Code:  <form:input path="code" pattern=".{1,}" style="height: 39px;" class="form-control" required="required" autocomplete="off" tabindex="2"/>
                   	</div>
                   	
             	</div>
              	<div class="col-md-6">               		
                  	<div class="form-group">
                   	Address <form:input path="address" style="height: 39px;" class="form-control" required="required" autocomplete="off" tabindex="3"/>                	
                   	</div>
                  	<form:hidden path="ccsid" value="0" />
                  	<form:hidden path="psiClinicManagement" value="${id}" />
                  	<div class="form-group">
                  	DHIS2 Org ID
                  		<form:input path="dhisId" style="height: 39px;" class="form-control" required="required" autocomplete="off" tabindex="4"/>
					</div>
              	</div>
              	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/spot.js"></script>

<%@ include file="/WEB-INF/template/footer.jsp"%>