<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/magicsuggest-min.css">
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/magicsuggest-min.js"></script>
<c:url var="saveUrl" value="/module/PSI/addPSIClinicUser.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicList.form" />
<%
String users = (String)session.getAttribute("users");
String userIds = (String)session.getAttribute("userIds");
//String message = (String)session.getAttribute("message");
%>

<a href="${pageContext.request.contextPath}/module/PSI/PSIClinicList.form"><spring:message
				code="PSI.psiclinic" /></a>Edit Assinged User
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
                  		<div id="cm" class="ui-widget">
							Assign User :
							<div id="userIds"></div>							
						</div>
                  	</div>
                  	 <input type="hidden" id="clinicId" name="clinicId" value="${psiClinicManagementId}">
                  	<form:hidden path="cuid" />
                   
             	</div>             	
              	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Cancel</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript">  
	var $jq = jQuery.noConflict();
	 $jq('#userIds').magicSuggest({ 
		 	required: true,
			//placeholder: 'Type Locations',
     		data: <%=users%>,
	        valueField: 'username',
	        displayField: 'display',
	        name: 'usernames',
	        inputCfg: {"class":"magicInput"},
	        value: <%=userIds%>,
	        useCommaKey: true,
	        allowFreeEntries: false,
	        maxSelection: 1,
	        maxEntryLength: 100,
	 		maxEntryRenderer: function(v) {
	 			return '<div style="color:red">Typed Word TOO LONG </div>';
	 		}	       
	  });
  </script>

<%@ include file="/WEB-INF/template/footer.jsp"%>