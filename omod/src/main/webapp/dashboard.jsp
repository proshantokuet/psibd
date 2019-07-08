<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />

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
     
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Service Point Wise Revenue Report</a></li>
    <li><a href="#tabs-2">Service Provider Wise Revenue Report</a></li>   
  </ul>
  <div id="tabs-1">
  
  <form:form id="ServicePointWise">
  	<div class="form-content">
        	<div class="row">
            	<div class="col-md-4">
                	<div class="form-group">                							
						From
						<input id="startDate"  name="startDate" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-4">
               		<div class="form-group">
                  	<label for="Service Code">To</label>
						<input id="endDate" name="endDate" type="text"  required="true"/>                  			
					</div>
                  	
              	</div>
              	<div class="col-md-4">
               		<div class="form-group">
                  	<button style="width: 120px" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  <div class="form-content" id="servicePointWiseReport">
  
  </div>
    <table id="servicePoint" class="display">
	    
	</table>
  </div>
  <div id="tabs-2">
    <form:form id="ServiceProviderWise">
  	<div class="form-content">
        	<div class="row">
            	<div class="col-md-3">
                	<div class="form-group">                							
						From
						<input style="width: 160px" id="from"  name="from" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">To</label>
						<input style="width: 160px" id="to" name="to" type="text"  required="true"/>                  			
					</div>
                  	
              	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Provider</label>
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
                  	<button style="width: 120px" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  <div class="form-content" id="userRole">
  
  </div>
    <table id="serviceProvider" class="display">
	    
	</table>
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
var url = "/openmrs/module/PSI/ServiceProviderWise.form?startDate="+$JQuery('input[name=from]').val()+"&endDate="+$JQuery('input[name=to]').val()+"&dataCollector="+provider;
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
		   $JQuery("#serviceProvider").html(data);
		   $JQuery('#serviceProvider').DataTable({
			   bFilter: false,
		       bInfo: false,
			   dom: 'Bfrtip',
			   destroy: true,
			   buttons: [
			             {
			                 extend: 'excelHtml5',
			                 title: 'providerWiseReport',
			                 text: 'Export as .xlxs'
			             }			         
			         ]
			   
		   });
		   $JQuery("#userRole").html(userRole);
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
	var url = "/openmrs/module/PSI/ServicePointWise.form?startDate="+$JQuery('input[name=startDate]').val()+"&endDate="+$JQuery('input[name=endDate]').val();
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
			   $JQuery("#servicePoint").html(data);			  
			   $JQuery('#servicePoint').DataTable({
				   bFilter: false,
			       bInfo: false,
				   dom: 'Bfrtip',
				   destroy: true,
				   buttons: [
				             {
				                 extend: 'excelHtml5',
				                 title: 'pointWiseReport',
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