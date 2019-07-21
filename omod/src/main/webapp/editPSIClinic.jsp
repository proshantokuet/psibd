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
<openmrs:require privilege="Edit Clinic" otherwise="/login.htm" />

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Edit Community Clinic.</p>
       	</div>
		<p>${message}</p>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
		 	<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
		 </div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
        <form:form method="POST"  id="clinicInfo" action="ff" modelAttribute="pSIClinic">
  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name : <form:input style="height: 39px;" path="name" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID: <form:input style="height: 39px;" pattern=".{3,}" path="clinicId" class="form-control" required="required"/>
                   	</div>
                   	<div class="form-group">
                  	Division <form:select path="divisionId" class="form-control selcls" required="required">
                  			<form:option value="">Please Select</form:option>
                  				  <c:forEach items="${divisions}" var="division">	                  				 
						              <form:option value="${division.id}">${division.name}</form:option>						             
					              </c:forEach>				             
					         </form:select>
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
                   	Address: <form:input style="height: 39px;" path="address" class="form-control" required="required"/>                	
                   	
                  	</div>
                  	<div class="form-group">
                  	District 
 					<form:select path="districtId" class="form-control selcls"  required="required" >
 						<form:option value=""/>
                  		<c:forEach items="${districts}" var="district">	                  				 
							<form:option value="${district.id}">${district.name}</form:option>						             
					        </c:forEach>
                  	</form:select>
					</div>
              	</div>
              	<form:hidden path="cid" />
              	
              	<div class="col-md-6">  
              		<div class="form-group">
                  	Upazila 
 					<form:select path="upazilaId" class="form-control selcls" required="required">
 					    <form:option value=""/>
                  		<c:forEach items="${upazilas}" var="upazila">	                  				 
							<form:option value="${upazila.id}">${upazila.name}</form:option>						             
					    </c:forEach>
                  	</form:select>
					</div> 
              	</div>
              	
              	<div class="col-md-6"> 
                  	<div class="form-group">
                   	DHIS2 Org ID <form:input path="dhisId" style="height: 39px;" class="form-control" required="required" autocomplete="off"/>
                  	</div>
                  	
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript">
var $JQuery = jQuery.noConflict();
$JQuery("#clinicInfo").submit(function(event) { 
	$JQuery("#loading").show();
			alert("OK");
			event.preventDefault();
			alert("OK");
			var url = "/openmrs/ws/rest/v1/clinic/save";			
			var token = $JQuery("meta[name='_csrf']").attr("content");
			var header = $JQuery("meta[name='_csrf_header']").attr("content");
			
			var e = document.getElementById("category");
			var category = e.options[e.selectedIndex].value;
			
			var division = document.getElementById("divisionId");
			var divisionId = division.options[division.selectedIndex].value;
			
			var district = document.getElementById("districtId");
			var districtId = district.options[district.selectedIndex].value;
			
			var upazila = document.getElementById("upazilaId");
			var upazilaId = upazila.options[upazila.selectedIndex].value;
			
			var formData;			
			formData = {
			            'name': $JQuery('input[name=name]').val(),			           
			            'clinicId': $JQuery('input[name=clinicId]').val(),
			            'category': category,
			            'address': $JQuery('input[name=address]').val(),
			            'dhisId': $JQuery('input[name=dhisId]').val(),
			            'cid': $JQuery('input[name=cid]').val(),
			            'division': divisionId,
			            'district': districtId,
			            'upazila': upazilaId
			           
			 };
			
			$JQuery.ajax({
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
					$JQuery("#usernameUniqueErrorMessage").html(data);
					$JQuery("#loading").hide();
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


<script>
var $JQuery = jQuery.noConflict();
$JQuery("#divisionId").change(function(event) { 
var e = document.getElementById("divisionId");
var divisionId = e.options[e.selectedIndex].value;	
var url = "/openmrs/module/PSI/divisions.form?divisionId="+divisionId;

alert(divisionId);
console.log(divisionId);
if(divisionId ==""){
	 $JQuery("#districtId").html("");
	 $JQuery("#upazilaId").html("");			
	
} else {
	event.preventDefault();
	$JQuery.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',		   
		   beforeSend: function() {    
		   
		   },
		   success : function(data) {		   
			   $JQuery("#districtId").html(data);			  
		   },
		   error : function(e) {
		    console.log("ERROR: ", e);
		    display(e);
		   },
		   done : function(e) {	    
		    console.log("DONE");
		    //enableSearchButton(true);
		   }
		}); 
 	}

});

$JQuery("#districtId").change(function(event) { 
	var e = document.getElementById("districtId");
	var districtId = e.options[e.selectedIndex].value;	
	var url = "/openmrs/module/PSI/districts.form?districtId="+districtId;			
	if(districtId ==""){			 
		 $JQuery("#upazilaId").html("");
	} else {
		event.preventDefault();	
		$JQuery.ajax({
			   type : "GET",
			   contentType : "application/json",
			   url : url,	 
			   dataType : 'html',		   
			   beforeSend: function() {	    
			   
			   },
			   success : function(data) {		   
				   $JQuery("#upazilaId").html(data);			  
			   },
			   error : function(e) {
			    console.log("ERROR: ", e);
			    display(e);
			   },
			   done : function(e) {	    
			    console.log("DONE");
			    //enableSearchButton(true);
			   }
			}); 
		}
	}); 
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>