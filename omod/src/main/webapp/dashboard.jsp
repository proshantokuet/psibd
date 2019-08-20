<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />

<%-- <div class="form-content">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                  	
             </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.newPatient }</label>  &nbsp;&nbsp; New Registration                 			
				</div>                  	
              </div>              	
       </div>          	
</div> --%>
     
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Service Point Wise Revenue Report</a></li>
    <li><a href="#tabs-2">Service Provider Wise Revenue Report</a></li>   
  </ul>
  <div id="tabs-1">
  
  <form:form id="ServicePointWise">
  	<div class="form-content">
        	<div class="row">
            	<div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code"> From</label><br />
						<input id="startDate"  name="startDate" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">To</label><br />
						<input id="endDate" name="endDate" type="text"  required="true"/>                  			
					</div>
                  	
              	</div>
              	<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
              	<div class="col-md-3">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button style="width: 120px ;margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  
    <div id="servicePoints">
    
    	<div class="form-content">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                  	
             </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.newPatient }</label>  &nbsp;&nbsp; New Registration                 			
				</div>                  	
              </div>              	
       </div>          	
	</div>

	<div class="form-content" id="servicePointWiseReport">	</div>  
	 <table id="servicePointDefault" class="display">
	 	<thead>
		        <tr>
		            <th>Category</th>
		            <th>Code</th>
		            <th>Item</th>
		            <th>Static</th>
		            <th>Satellite</th>
		            <th>CSP</th>
		            <th>Total</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach var="report" items="${ servicePointWiseReports }">
		        <tr>
		            <td>${ report.category }</td>
		        	<td>${ report.code }</td>
		            <td>${ report.item }</td>
		            <td>${ report.clinic }</td>
		            <td>${ report.satelite }</td>
		            <td>${ report.csp }</td>
		            <td>${ report.total }</td>
		        </tr>
		       </c:forEach>
		        
		    </tbody>
	
	</table>
	    
	</div>
  </div>
  <div id="tabs-2">
    <form:form id="ServiceProviderWise">
  	<div class="form-content">
  	
        	<div class="row">
            	<div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code">From</label><br />
						<input style="width: 160px" id="from"  name="from" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">To</label> <br/>
						<input style="width: 160px" id="to" name="to" type="text"  required="true"/>                  			
					</div>
                  	
              	</div>
              	<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinics" id="clinics" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Provider</label><br />
						<select id="provider" required="true" style="width: 160px" >
						  <option value=""></option>
						  <c:forEach var="user" items="${ psiClinicUsers }">
							  <option value="${user.username }">${user.userRole }</option>							  
						  </c:forEach>
						</select>                 			
					</div>
                  	
              	</div>
              	<div class="col-md-3">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button style="width: 120px" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
 
    <div id="serviceProviders">
	    <div class="form-content">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                  	
             </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.newPatient }</label>  &nbsp;&nbsp; New Registration                 			
				</div>                  	
              </div>              	
       </div>          	
	</div>

 <div class="form-content" id="serviceProviderReports">
  
 </div>
  
	<table id="serviceProviderDefault" class="display">
		  <thead>
		        <tr>
		            <th>Category</th>	            
		            <th>Item</th>
		            <th>Code</th>
		            <th>Number of Service</th>	            
		            <th>Total</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach var="report" items="${ providerWiseReports }">
		        <tr>
		            <td>${ report.category }</td>	            
		        	<td>${ report.code }</td>
		        	<td>${ report.item }</td>
		            <td>${ report.serviceCount }</td>
		            <td>${ report.total }</td>
		        </tr>
		       </c:forEach>
		        
		    </tbody>
	</table>
	</div>
  </div>
  
</div>
 
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>

<script type="text/javascript">

var $jq = jQuery.noConflict();

var $jQuery = jQuery.noConflict();
$jQuery( function() {
	$jQuery( "#tabs" ).tabs();
  } );
  

$jq( function() {
	$jq("#startDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#endDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
$jq( function() {
	$jq("#from").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#to").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
/* $jq('#startDate').attr('max', maxDate);
$jq('#endDate').attr('max', maxDate); */

</script>

<script type="text/javascript">
var $JQuery = jQuery.noConflict();
$JQuery("#ServiceProviderWise").submit(function(event) { 
var e = document.getElementById("provider");
var provider = e.options[e.selectedIndex].value;
var userRole = "Service Provider Wise Revenue Report( "+$JQuery("#provider option:selected").html()+" )";
var startDate = $JQuery('input[name=from]').val();
var endDate = $JQuery('input[name=to]').val();
var title = "Service Provider Wise Revenue Report_"+startDate+"_"+endDate;
var clinic = document.getElementById("clinics");
var clinicCode = "";
if(clinic !== null){		
	clinicCode = clinic.options[clinic.selectedIndex].value;
}else {
	clinicCode = -1;
}

var url = "/openmrs/module/PSI/ServiceProviderWise.form?startDate="+startDate+"&endDate="+endDate+"&dataCollector="+provider+"&code="+clinicCode;
event.preventDefault();
$JQuery.ajax({
	   type : "GET",
	   contentType : "application/json",
	   url : url,	 
	   dataType : 'html',
	   timeout : 100000,
	   beforeSend: function() {	    
	   
	   },
	   success : function(data) {		   
		   $JQuery("#serviceProviders").html(data);
		   $JQuery('#serviceProvider').DataTable({
			   bFilter: false,
		       bInfo: false,
			   dom: 'Bfrtip',
			   destroy: true,
			   buttons: [
			             {
			                 extend: 'excelHtml5',
			                 title: title,
			                 text: 'Export as .xlxs'
			             }			         
			         ]
			   
		   });
		   $JQuery("#serviceProviderReports").html(userRole);
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
});

$JQuery("#ServicePointWise").submit(function(event) {	
	
	var clinic = document.getElementById("clinic");
	var clinicCode = "";
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
	}else {
		clinicCode = -1;
	}
	
	var startDate = $JQuery('input[name=startDate]').val();
	var endDate = $JQuery('input[name=endDate]').val();
	
	var url = "/openmrs/module/PSI/ServicePointWise.form?startDate="+startDate+"&endDate="+endDate+"&clinic_code="+clinicCode;
	var title = "Service Point Wise Revenue Report_"+startDate+"_"+endDate;
	event.preventDefault();	
	
	$JQuery.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   
		   },
		   success : function(data) {	   
			   $JQuery("#servicePoints").html(data);			  
			   $JQuery('#servicePoint').DataTable({
				   bFilter: false,
			       bInfo: false,
				   dom: 'Bfrtip',
				   destroy: true,
				   buttons: [
				             {
				                 extend: 'excelHtml5',
				                 title: title,
				                 text: 'Export as .xlxs'
				             }			         
				         ]
			   });
			   $JQuery("#servicePointWiseReport").html("Service Point Wise Revenue Report");
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
	});
	  
$JQuery("#clinics").change(function(event) { 
	var clinicForProvider = document.getElementById("clinics");
	var clinicForProviderValue = clinicForProvider.options[clinicForProvider.selectedIndex].value;	
	var url = "/openmrs/module/PSI/providerByClinic.form?code="+clinicForProviderValue;

	if(clinicForProviderValue ==""){
		 $JQuery("#provider").html("");	 			
		
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
				   $JQuery("#provider").html(data);			  
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
	
	
	
$JQuery('#servicePointDefault').DataTable({
	   bFilter: false,
       bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Service Point Wise Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             }			         
	         ]
});
$JQuery("#servicePointWiseReport").html("Service Point Wise Revenue Report of Today");

$JQuery('#serviceProviderDefault').DataTable({
	   bFilter: false,
    bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Service Provider Wise Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             }			         
	         ]
});
$JQuery("#serviceProviderReports").html("Service Provider Wise Revenue Report of Today");
</script>
 



<%@ include file="/WEB-INF/template/footer.jsp"%>
<style>
.dataTables_wrapper .dt-buttons {
  float:none;  
  text-align:right;
  position: absolute;
  top: -55px;
  margin-left: 1036px
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
    border: 1px solid #1aad96;
    /* background: #1aac9b url(images/ui-bg_inset-soft_30_ffffff_1x100.png) 50% 50% repeat-x; */
    font-weight: bold;
    background: #4aad9b;
    color: #0e5c52;
}
</style>