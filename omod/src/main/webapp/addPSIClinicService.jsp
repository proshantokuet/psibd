<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Add Clinic Service" otherwise="/login.htm" />

<c:url var="saveUrl" value="/module/PSI/addPPSIClinicService.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicServiceList.form" />



<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Add Service</p>
        	
       	</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<form:form id="serviceForm" method="POST" action="${saveUrl}" modelAttribute="pSIServiceManagement">
		
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">                							
						Service Code
						<form:input  style="height: 39px;" path="code" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  	Service Category: 
                  			<form:select path="category" class="form-control selcls" required="required">
                  				<form:option value=""/>
					              <form:option value="Child health"/>
					              <form:option value="Maternal health"/>
					              <form:option value="NID-Immunization"/>
					              <form:option value="Family Planning"/>
					              <form:option value="Others"/>
					              <form:option value="Referral"/>
					              <form:option value="Lab"/>
					              <form:option value="Pharmacy"/>
					              <form:option value="Non-ESD"/>
					              <form:option value="Medicine"/>
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
              	
              	
              	<div class="col-md-6"> 
              		<div class="form-group">
                  	Clinic: 
                  		<form:select path="psiClinicManagement" class="form-control selcls" required="required">                  			 
                  	 			  <form:option value=""/>
                  	 			  
                  	 			  <form:options items="${clinics}" itemValue="cid" itemLabel="name"/>
					             <%--  <c:forEach items="${clinics}" var="clinic"> 
					              <form:option value="clinic.cid" label="clinic.name"/>					              
					              </c:forEach> --%>
						</form:select>	
                  	</div>              		
                  	
              	</div>
              	
              	<div class="col-md-6">               		
                  	<div class="form-group">
                  	Unit Cost:
                  		<form:input style="height: 39px;" path="unitCost" class="form-control" required="required" min="0"/>
                   	 	
                  	</div>
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>  <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript">
$("#serviceForm").submit(function(event) { 
			$("#loading").show();
			var url = "/openmrs/ws/rest/v1/service-management/save";			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");			
			
			var e = document.getElementById("category");
			var category = e.options[e.selectedIndex].value;
			var e1 = document.getElementById("provider");
			var provider = e1.options[e1.selectedIndex].value;
			var clinic = document.getElementById("psiClinicManagement");
			var clinicValue = clinic.options[clinic.selectedIndex].value;
			var formData;			
				formData = {
			            'name': $('input[name=name]').val(),			           
			            'code': $('input[name=code]').val(),
			            'category': category,
			            'provider': provider,
			            'unitCost': $('input[name=unitCost]').val(),
			            'psiClinicManagement': clinicValue	
			           
			        };			
			
			event.preventDefault();			
			$.ajax({
				contentType : "application/json",
				type: "POST",
		        url: url,
		        data: JSON.stringify(formData), 
		        dataType : 'json',
		        
				timeout : 100000,
				beforeSend: function(xhr) {				    
					 xhr.setRequestHeader(header, token);
				},
				success : function(data) {
				   $("#usernameUniqueErrorMessage").html(data);
				   $("#loading").hide();
				   if(data == ""){					   
					   window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form");
					   
				   }
				   
				},
				error : function(e) {
				   
				},
				done : function(e) {				    
				    console.log("DONE");				    
				}
			}); 
		});
		
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>