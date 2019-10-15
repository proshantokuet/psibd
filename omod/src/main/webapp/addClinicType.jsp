<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Add Clinic Type" otherwise="/login.htm" />

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Add Clinic Type</p>
        	
       	</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<form:form id="addClinicTypeForm" method="POST" action="/addClinicType.form" modelAttribute="clinicType">
		
  		<div class="form-content">
        	<div class="row">	
              	<div class="col-md-6">
              		
              		<div class="form-group">
                  	Type Name
                  		<form:input style="height: 39px;" path="typeName" id="typeName" class="form-control" required="required" min="0"/>
                   	 	
                  	</div>      		
                  	
              	</div>
              	
              
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> 
            <a href="${cancelUrl}">Back</a> 
      	</div>
   	</div>
</div>       
	

</form:form>
<script>
var $JQuery = jQuery.noConflict();
$JQuery('#addClinicTypeForm').submit(function(event)
{
	$JQuery("#loading").show();
	event.preventDefault();

	var url = "/openmrs/module/PSI/addClinicType.form";
	var token = $JQuery("meta[name='_csrf']").attr("content");
	var header = $JQuery("meta[name='_csrf_header']").attr("content");

	// var name = document.getElementById('categoryName');
	var formData;
	formData = {
		'typeName' : $JQuery('#typeName').val()
	};
	console.table(formData);
	$JQuery.ajax({
		contentType: "application/json",
		type: "POST",
		url: url,
		data: JSON.stringify(formData),
		dataType: 'json',

		timeout: 100000,
		beforeSend: function(xhr){
			xhr.setRequestHeader(header, token);
		},
		success : function(data){
			$JQuery('#loading').hide();
			if(data == ""){
				window.location.replace("/openmrs/module/PSI/clinicTypeList.form");
			}
		},
		error: function(data){
			$JQuery('#loading').hide();
		}
	});
});
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>