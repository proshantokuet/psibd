<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic Spot List" otherwise="/login.htm" />
<c:url var="saveUrl" value="/module/PSI/uploadPSIClinicSpot.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicSpotList.form?id=${id}" />


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
    	    
        	<p>Upload Community Clinic Spot Via CSV</p>
        	
       	</div>
		<div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<span class="text-red" id="msg"></span>
		<form:form method="POST" id="uploadSpotCSVForm" action="${saveUrl}" modelAttribute="pSIClinicSpot" enctype="multipart/form-data">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">                							
						File
						<input id="file" style="height: 39px;"  type="file" name="file" class="form-control" required="required"/>	
						
                  	</div>                  	
             	</div>
             	<input type="hidden" name="id" value="${id}" />              	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
          	${msg}
      	</div>
      	</form:form>
   	</div>
</div> 
<script type="text/javascript">
$("#uploadSpotCSVForm").submit(function(event) { 
			$("#loading").show();
			var url = "/openmrs/ws/rest/v1/clinic/spot/upload";			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");			
			
			var formData = new FormData();
			formData.append('file', $('#file')[0].files[0]);
			formData.append('id', $('input[name=id]').val());
			
			event.preventDefault();			
			$.ajax({
				contentType : "application/json",
				type: "POST",
		        url: url,
		        data: formData, 
		        dataType : 'json',
		        processData: false,
		        contentType: false,
				timeout : 100000,
				beforeSend: function(xhr) {				    
					 xhr.setRequestHeader(header, token);
				},
				success : function(data) {
				   $("#msg").html(data);
				   $("#loading").hide();
				   if(data == ""){					   
					   //window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form");
					   
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