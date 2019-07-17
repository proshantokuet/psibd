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
<c:url var="cancelUrl" value="/module/PSI/PSIClinicList.form" />
<%
//String users = (String)session.getAttribute("users");
//String userIds = (String)session.getAttribute("userIds");
//String message = (String)session.getAttribute("message");
%>

<openmrs:require privilege="Add Clinic" otherwise="/login.htm" />


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Add Community Clinic.</p>
        	
       	</div>
		
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
	    <form:form method="POST"  id="clinicInfo" action="${saveUrl}" modelAttribute="pSIClinic">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name <form:input path="name" style="height: 39px;" class="form-control" required="required" autocomplete="off"/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID:  <form:input path="clinicId" pattern=".{3,}" style="height: 39px;" class="form-control" required="required" autocomplete="off"/>
                   	</div>
                   	<div class="form-group">
                  	Division <form:select path="division" class="form-control selcls" required="required">
                  				   <form:option value=""/>
                  				  <c:forEach items="${locations}" var="location">	                  				 
						              <form:option value="${location.id}">${location.name}</form:option>						             
					              </c:forEach>				             
					         </form:select>
					</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	Category 	<form:select path="category" class="form-control selcls" required="required">
                  				<form:option value=""/>
					              <form:option value="BEmOC"/>
					              <form:option value="CEmOC"/>
					              <form:option value="Vital"/>					             
					         </form:select>
					</div>
                  	<div class="form-group">
                   	Address 	<form:input path="address" style="height: 39px;" class="form-control" required="required" autocomplete="off"/>                	
                   	
                  	</div>
              	</div>
              	<div class="col-md-6">               		
                  	<div class="form-group">
                   	DHIS2 Org ID <form:input path="dhisId" style="height: 39px;" class="form-control" required="required" autocomplete="off"/>
                  	</div>
                  
              	</div>
              	
              <!-- 	<div class="col-md-6"> 
                  	<div class="form-group">
                  		<div id="cm" class="ui-widget">
							Assign User :
							<div id="userIds"></div>							
						</div>
                  	</div>
                  	
              	</div> -->
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript">
$("#clinicInfo").submit(function(event) { 
			$("#loading").show();
			var url = "/openmrs/ws/rest/v1/clinic/save";			
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			//alert(locationMagicSuggest.getValue());
			//alert($('#team').val());
			
			var e = document.getElementById("category");
			var category = e.options[e.selectedIndex].value;
			var formData;
			
				formData = {
			            'name': $('input[name=name]').val(),			           
			            'clinicId': $('input[name=clinicId]').val(),
			            'category': category,
			            'address': $('input[name=address]').val(),
			            'dhisId': $('input[name=dhisId]').val()	            
			           
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
<%-- <script type="text/javascript">  
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
	        maxSelection: 100,
	        maxEntryLength: 10000,
	 		maxEntryRenderer: function(v) {
	 			return '<div style="color:red">Typed Word TOO LONG </div>';
	 		}	       
	  });
  </script>
 --%>
<%@ include file="/WEB-INF/template/footer.jsp"%>