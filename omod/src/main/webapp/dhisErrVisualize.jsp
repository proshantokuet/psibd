<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />
<style>
table.dataTable tbody th, table.dataTable tbody td {
     padding: 0px 0px; 
}
#loader{
	background-color:#fff;
	 padding: 15px;
  	 position: absolute;
     top: 50%;
     left: 50%;
     opacity:1.2;
   	-ms-transform: translateX(-50%) translateY(-50%);
  	-webkit-transform: translate(-50%,-50%);
  	transform: translate(-50%,-50%);
}
#tabs{
	display:none;
}
</style>
<div id="loader"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Patient Sync Report</a></li>
    <li><a href="#tabs-2">Money Receipt Sync Report</a></li>
  </ul>
  <div id="tabs-1">
    <form:form id="patienterrorvisualize">
  	<div class="form-content">
  	
        	<div class="row">
       <!--      	<div class="col-md-2">
                	<div class="form-group">                							
						<label for="Service Code">From</label><br />
						<input style="width: 160px" id="from"  name="from" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-2">
               		<div class="form-group">
                  	<label for="Service Code">To</label> <br/>
						<input style="width: 160px" id="to" name="to" type="text"  required="true"/>                  			
					</div>
                  	
              	</div> -->
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_patient" required="true" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	<div class="col-md-2">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  <div id="loading_prov" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
		<div id="patientsyncReport">
			<div class="form-content">
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<label>${patientTransferred}</label> &nbsp;&nbsp; Total
							Transferred
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>${patientToSync}</label> &nbsp;&nbsp; Sync In Progress
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>${patientFailedSync}</label> &nbsp;&nbsp; Sync Failed
						</div>
					</div>
					<!-- <div class="col-md-3">
						<div class="form-group">
							<button onclick="dhisPatientSyncReport()">View Failure Report</button>
						</div>
					</div> -->
				</div>
			</div>
			<table id="table_id" class="display cell-border compact">
				<thead>
					<tr>
						<!-- <th>Patient ID</th> -->
						<th>Clinic Name</th>
						<th>HID</th>
						<!-- <th>Clinic Code</th> -->
						<th>Error</th>
						<th>Date Created</th>
						<th>Last Sync Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="report" items="${ patientDhisReport }">
						<tr>
							<%-- <td>${ report.person_id }</td> --%>
							<td>${ report.clinic_name }</td>
							<td>${ report.identifier }</td>
							<%-- <td>${ report.clinic_code }</td> --%>
							<td>${ report.error }</td>
							<td>${ report.date_created }</td>
							<td>${ report.date_changed }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="tabs-2">
    <form:form id="moneyreceipterrorvisualize">
  	<div class="form-content">
  	
        	<div class="row">
<!--             	<div class="col-md-2">
                	<div class="form-group">                							
						<label for="Service Code">From</label><br />
						<input style="width: 160px" id="from"  name="from" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-2">
               		<div class="form-group">
                  	<label for="Service Code">To</label> <br/>
						<input style="width: 160px" id="to" name="to" type="text"  required="true"/>                  			
					</div>
                  	
              	</div> -->
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_receipt" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	<div class="col-md-2">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
   
  	<div id="loading_prov_money_receipt" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
  <div id = "moneyReceiptReport">
      	<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label >${transferredMoneyReceipt}</label>
                  	  	&nbsp;&nbsp; Total Transferred
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label>${money_receipt_to_sync}</label> &nbsp;&nbsp; 
                  		Sync In Progress
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label>${sync_failed}</label> &nbsp;&nbsp; Sync Failed
	                </div>
				</div>
				<!-- <div class="col-md-3">
					<div class="form-group">
	                   <button onclick="dhisMoneyReceiptSyncReport()">View Failure Report</button>
	                </div>
				</div> -->
			</div>
		</div>

	<table id="table_id_moneyreceipt" class="display cell-border compact">
		  <thead>
		        <tr>
		            <!-- <th>Patient ID</th>	 -->
		            <th>Clinic Name</th>
		            <th>HID</th> 
		            <!-- <th>Clinic Code</th>  -->
		            <th>Money Receipt ID</th>
		            <th>Error</th>
		            <th>Date Created</th>		            
		            <th>Last Sync Date</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach var="report" items="${ moneyReceiptDhisReport }">
		        <tr>
		            <%-- <td>${ report.person_id }</td> --%>	
		            <td>${ report.clinic_name }</td>            
		        	<td>${ report.identifier }</td>
		        	<%-- <td>${ report.clinic_code }</td> --%>
		            
		            <td>${ report.mid }</td>
		            <td>${ report.error }</td>
		            <td>${ report.date_created }</td>
		            <td>${ report.date_changed }</td>
		        </tr>
		        </c:forEach>
		    </tbody>
	</table>
		</div>
	</div>
 </div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">
var $jq = jQuery.noConflict();
$jq(window).load(function() {
	$jq("#loader").hide();
	$jq( "#tabs" ).tabs();
	$jq("#tabs").show();
});
$jq(document).ready( function () {
	$jq('#table_id').DataTable();
} );
$jq(document).ready( function () {
	$jq('#table_id_moneyreceipt').DataTable();
} );

$jq("#patienterrorvisualize").on("submit",function(event){
	event.preventDefault();
	$jq("#loading_prov").show();
	var clinic = document.getElementById("clinic_patient");
	var clinicCode;
	var url;
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
	}
	if(clinicCode == "0") {
		 url = "/openmrs/module/PSI/dhisPatientReportSyncData.form";
	}
	else {
		url = "/openmrs/module/PSI/dhisPatientReportSyncData.form?clinicCode="+clinicCode;
	}
	
	$jq.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
			   $jq("#patientsyncReport").html(data);
			   $jq('#table_id').DataTable();
			   $jq("#loading_prov").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov").hide();
	    }
	    
	});
});

function dhisPatientSyncReport() {
	
	var url = "/openmrs/module/PSI/dhisPatientReportSyncData.form";
	event.preventDefault();
	$jq("#loading_prov").show();
	$jq.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType: 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   
		   },
		   success : function(data) {		   
			   $jq("#patientsyncReport").html(data);
			   $jq('#table_id').DataTable();
			   $jq("#loading_prov").hide();
		   },
		   error : function(e) {
		    console.log("ERROR: ", e);
		    $jq("#loading_prov").hide();
		   },
		   done : function(e) {	    
			 $jq("#loading_prov").hide();
		    console.log("DONE");
		   }
		  }); 
}

function dhisMoneyReceiptSyncReport() {
	
	var url = "/openmrs/module/PSI/dhisMoneyReceiptReportSyncData.form";
	event.preventDefault();
	$jq("#loading_prov_money_receipt").show();
	$jq.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType: 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   
		   },
		   success : function(data) {		   
			   $jq("#moneyReceiptReport").html(data);
			   $jq('#table_id_moneyreceipt').DataTable();
			   $jq("#loading_prov_money_receipt").hide();
		   },
		   error : function(e) {
		    console.log("ERROR: ", e);
		    $jq("#loading_prov_money_receipt").hide();
		   },
		   done : function(e) {	    
			 $jq("#loading_prov_money_receipt").hide();
		    console.log("DONE");
		   }
		  }); 
}

$jq("#moneyreceipterrorvisualize").on("submit",function(event){
	event.preventDefault();
	$jq("#loading_prov_money_receipt").show();
	var clinic = document.getElementById("clinic_receipt");
	var clinicCode;
	var url;
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
	}
	if(clinicCode == "0") {
		 url = "/openmrs/module/PSI/dhisMoneyReceiptReportSyncData.form";
	}
	else {
		url = "/openmrs/module/PSI/dhisMoneyReceiptReportSyncData.form?clinicCode="+clinicCode;
	}
	
	$jq.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
			   $jq("#moneyReceiptReport").html(data);
			   $jq('#table_id_moneyreceipt').DataTable();
			   $jq("#loading_prov_money_receipt").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov_money_receipt").hide();
	    }
	    
	});
});

</script>