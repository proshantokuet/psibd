<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/magicsuggest-min.css">
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/magicsuggest-min.js"></script>
<c:url var="saveUrl" value="/module/PSI/addPsiClinic.form" />




<div class="container register-form">
	<div class="form">
    	<div class="note">
        	<p>Edit Community Clinic.</p>
       	</div>
		<p>${message}</p>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
		 	<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
		 </div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
        <form:form method="POST"  id="clinicInfo" action="${saveUrl}" modelAttribute="pSIClinic">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name : <form:input path="name" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID: <form:input path="clinicId" class="form-control" required="required"/>
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

<script type="text/javascript">
$("#clinicInfo").submit(function(event) { 
			$("#loading").show();
			var url = "/openmrs/ws/rest/v1/clinic/edit";			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");			
			
			var e = document.getElementById("category");
			var category = e.options[e.selectedIndex].value;
			var formData;
			
				formData = {
			            'name': $('input[name=name]').val(),			           
			            'clinicId': $('input[name=clinicId]').val(),
			            'category': category,
			            'address': $('input[name=address]').val(),
			            'dhisId': $('input[name=dhisId]').val(),
			            'cid': $('input[name=cid]').val()
			           
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
				   if(data == "ok"){					   
					   window.location.replace("/openmrs/module/PSI/PSIClinicList.form");					   
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