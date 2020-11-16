<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />
<style>
.dataTables_wrapper .dt-buttons {
  float:none;  
  text-align:right;
  position: static;
  top: -26px;
  margin-left: 1036px
}
.dataTables_wrapper .dataTables_filter {
    float: left;
    text-align: right;
}
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

    <form:form id="patienterrorvisualize">
  	<div class="form-content">
  	
        	<div class="row">
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

			<div style="overflow:auto;">
			<table id="table_id" class="display cell-border compact">
				<thead>
					<tr>
						<th>HID</th>
						<th>patientName</th>
						<th>age</th>
						<th>contactNumber</th>
						<th>clinicCode</th>
						<th>visitUuid</th>
						<th>encounterUuid</th>
						<th>visitType</th>
						<th>visitStart</th>
						<th>visitEnd</th>
						<th>followUpFor</th>
						<th>followUpDate</th>
						<th>valueCoded</th>
						
					</tr>
				</thead>
				<%-- <tbody>
					<c:forEach var="report" items="${ patientDhisReport }">
						<tr>
							<td>${ report.person_id }</td>
							<td>${ report.clinic_name }</td>
							<td>${ report.identifier }</td>
							<td>${ report.clinic_code }</td>
							<td>${ report.error }</td>
							<td>${ report.date_created }</td>
							<td>${ report.date_changed }</td>
						</tr>
					</c:forEach>
				</tbody> --%>
				<tbody>
				<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				</tr>
				</tbody>
			</table>
			</div>
		</div>


<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/pdfmake.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.print.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/vfs_fonts.js"></script>

<script type="text/javascript">
var $jq = jQuery.noConflict();
$jq(window).load(function() {
	$jq("#loader").hide();

});
$jq(document).ready( function () {
	//$jq('#table_id').DataTable();
	$jq('#table_id').DataTable({
		   bFilter: false,
	       bInfo: false,
	       "searching": true,
		   dom: 'Bfrtip',
		   destroy: true,
		   buttons: [
		             {
		                 extend: 'excelHtml5',
		                 title: "Patient DHIS2 Sync Report",
		                 text: 'Export as .xlxs'
		             },
		             {
			         		extend: 'pdfHtml5',
			         		title: "Patient DHIS2 Sync Report",
			         		text: 'Export as .pdf',
			         		orientation: 'landscape',
			         		pageSize: 'LEGAL'
				     }
		         ]
	});
} );
$jq(document).ready( function () {
	//$jq('#table_id_moneyreceipt').DataTable();
	$jq('#table_id_moneyreceipt').DataTable({
		   bFilter: false,
	       bInfo: false,
	       "searching": true,
		   dom: 'Bfrtip',
		   destroy: true,
		   buttons: [
		             {
		                 extend: 'excelHtml5',
		                 title: "Money Receipt DHIS2 Sync Report",
		                 text: 'Export as .xlxs'
		             },
		             {
			         		extend: 'pdfHtml5',
			         		title: "Money Receipt DHIS2 Sync Report",
			         		text: 'Export as .pdf',
			         		orientation: 'landscape',
			         		pageSize: 'LEGAL'
				     }
		         ]
	});
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
			   //$jq('#table_id').DataTable();
				$jq('#table_id').DataTable({
					   bFilter: false,
				       bInfo: false,
				       "searching": true,
					   dom: 'Bfrtip',
					   destroy: true,
					   buttons: [
					             {
					                 extend: 'excelHtml5',
					                 title: "Money Receipt DHIS2 Sync Report",
					                 text: 'Export as .xlxs'
					             },
					             {
						         		extend: 'pdfHtml5',
						         		title: "Money Receipt DHIS2 Sync Report",
						         		text: 'Export as .pdf',
						         		orientation: 'landscape',
						         		pageSize: 'LEGAL'
							     }
					         ]
				});
			   
			   $jq("#loading_prov").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov").hide();
	    }
	    
	});
});


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
			   //$jq('#table_id_moneyreceipt').DataTable();
			   $jq('#table_id_moneyreceipt').DataTable({
					   bFilter: false,
				       bInfo: false,
				       "searching": true,
					   dom: 'Bfrtip',
					   destroy: true,
					   buttons: [
					             {
					                 extend: 'excelHtml5',
					                 title: "Money Receipt DHIS2 Sync Report",
					                 text: 'Export as .xlxs'
					             },
					             {
						         		extend: 'pdfHtml5',
						         		title: "Money Receipt DHIS2 Sync Report",
						         		text: 'Export as .pdf',
						         		orientation: 'landscape',
						         		pageSize: 'LEGAL'
							     }
					         ]
				});
			   $jq("#loading_prov_money_receipt").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov_money_receipt").hide();
	    }
	    
	});
});

</script>