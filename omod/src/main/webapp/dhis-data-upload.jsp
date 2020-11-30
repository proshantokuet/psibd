<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
    	    
        	<p>Dhis2 Historical Data Upload</p>
        	
       	</div>
		<div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<span class="text-red" id="msg"></span>
		<form id="productForm">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">                							
						File
						<input id="file" style="height: 39px;" accept=".csv"  type="file" name="file" class="form-control" required="required"/>	
						
                  	</div>                  	
             	</div>
                	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
      	</form>
   	</div>
</div>       
	
<script type="text/javascript">
$("#productForm").submit(function(event) { 
			$("#loading").show();
			var url = "/openmrs/ws/rest/v1/dhis-upload/historical-data";			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");			
			
			var formData = new FormData();
			formData.append('file', $('#file')[0].files[0]);
			//formData.append('id', $('input[name=id]').val());
			
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
				$("#file").val(null);
				   console.log(data);
				   $("#msg").html(data);
				   $("#loading").hide();
				   if(data == ""){					   
					   //window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form");
					   
				   }
				   
				},
				error : function(e) {
					$("#file").val(null);
				},
				done : function(e) {				    
				    console.log("DONE");				    
				}
			}); 
		});
		
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>