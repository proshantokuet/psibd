<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
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
  	<div class="form-content">
    <form:form id="patienterrorvisualize">
        	<div class="row">
        	   <div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code">Visit From</label><br />
						<input id="visitstartDate"  name="visitstartDate" type="text" />
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Visit To</label><br />
						<input id="visitendDate" name="visitendDate" type="text" />
					</div>
                  	
              	</div>
        	   <div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code">Follow-Up From</label><br />
						<input id="followupstartDate"  name="followupstartDate" type="text"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Follow-Up To</label><br />
						<input id="followupendDate" name="followupendDate" type="text" />
						<%-- <input id="clnic" type="hidden" value="${clinic}">    --%>                			
					</div>
                  	
              	</div>
              	
          	</div>
          	<div class="row">
        	   <div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code">Mobile No</label><br />
						<input id="patientContact"  name="patientContact" type="text"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Patient HID</label><br />
						<input id="hid" name="hid" type="text"/>
					</div>
                  	
              	</div>
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_patient" style="height: 39px;" required="true" class="form-control selcls">
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
						<th>Patient Name</th>
						<th>Age</th>
						<th>Contact number</th>
						<th>Visit Type</th>
						<th>Visit Start</th>
						<th>Visit End</th>
						<th>Follow-up For</th>
						<th>Follow-up Date</th>
						<th>Status</th>
						<th>Result</th>
						<th>Action</th>
						
					</tr>
				</thead>
<%-- 				<tbody>
					<c:forEach var="report" items="${ followUpReport }">
						<tr>
							<td>${ report.identifier }</td>
							<td>${ report.patientName }</td>
							<td>${ report.age }</td>
							<td>${ report.contactNumber }</td>
							<td>${ report.visitUuid }</td>
							<td>${ report.encounterUuid }</td>
							<td>${ report.visitType }</td>
							<td>${ report.visitStart }</td>
							<td>${ report.visitEnd }</td>
							<td>${ report.followUpFor }</td>
							<td>${ report.followUpDate }</td>
							<td>${ report.valueCoded }</td>
							<td>NA</td>
							<td>NA</td>
							<td><div style="padding-top: 5px;"><a class="btn btn-primary" href="<c:url value="/module/PSI/adjust-stock.form?id=${ product.sid }&clinicid=${id}"/>"> Adjust</a></div></td>
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
							<td>NA</td>
							<td>NA</td>
							<td><div style="padding-top: 5px;"><a class="btn btn-primary"  onclick="openFollowUpMOdal('Visituuid','encounteruuid',12345,'Eye Care')"> Follow-Up</a></div></td>
						</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="modal" id="followupModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-md" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Add Follow-Up Action</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      <div style="display: none;" class="alert alert-success" id="serverResponseMessage" role="alert"></div>
		      <div id="savefollowup" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
				<img width="50px" height="50px" src="/openmrs/moduleResources/PSI/images/ajax-loading.gif">
				</div>
		       <div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">1. Contact Date:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="contactDate" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">2. Response:</label><span class="text-danger"> *</span> 
						<select class="form-control" id="response" style="height: 39px;" name="response">
										<option value="">Select Response</option>
											<option value="1">Responded</option>
											<option value="0">Didn't Respond</option>
								</select>
								<span id="responseselectvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row" style="display:none;" id ="resultDiv">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">3. Result:</label><span class="text-danger"> *</span> 
						<select class="form-control" id="result" style="height: 39px;" name="result">
										<option value="">Select Result</option>
											<option value="Will Visit">Will Visit</option>
											<option value="Will Not Visit">Will Not Visit</option>
											<option value="Went to another service center">Went to another service center</option>
											<option value="Others">Others</option>
								</select>
								<span id="resultselectionvalidation" class="text-danger"></span>
					</div>
				</div>
				<input type="hidden" id="visitUUid" >
				<input type="hidden" id="encounterUUid" >
				<input type="hidden" id="valueCoded" >
				<input type="hidden" id="codedConceptName" >
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
		        <button type="button" onclick="saveFollowUpActionData()" class="btn btn-primary">Confirm</button>
		      </div>
		    </div>
		  </div>
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
$jq( function() {
	$jq("#visitstartDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#visitendDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
$jq( function() {
	$jq("#followupstartDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#followupendDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
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

function openFollowUpMOdal(visituuid,encounteruuid,valueCoded,conceptName) {
	$jq('#followupModal').modal('show');
	$jq('#visitUUid').val(visituuid);
	$jq('#encounterUUid').val(encounteruuid);
	$jq('#valueCoded').val(valueCoded);
	$jq('#codedConceptName').val(conceptName);
	
	$jq("#contactDate").datepicker({dateFormat: 'yy-mm-dd'}).datepicker('setDate', new Date());
	$jq("#contactDate").attr('disabled','disabled');
	console.log($jq('#visitUUid').val());
	console.log($jq('#encounterUUid').val());
	console.log($jq('#valueCoded').val());
	console.log($jq('#codedConceptName').val());
}

jQuery("#response").change(function (event) {
	let responseValue = jQuery('#response').val();
	if(responseValue == "1") {
		jQuery('#resultDiv').show();
	}
	else {
		jQuery('#resultDiv').hide();
	}

});


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


function saveFollowUpActionData() {
	var isresponded = true;
	var response = $jq('#response').val();
	var contactDate = $jq("#contactDate").val();
	var encounterUUid = $jq('#encounterUUid').val();
	var visitUuid = $jq('#visitUUid').val();
	var valueCoded = +$jq('#valueCoded').val();
	var codedConceptName = $jq('#codedConceptName').val();
	if(response == "") {
		$jq("#responseselectvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	$jq("#responseselectvalidation").html("");
	
	var result = $jq('#result').val();
	if($jq('#response').val() == "1") {
		var resultValue = $jq('#result').val();
		if(resultValue == "") {
			$jq("#resultselectionvalidation").html("<strong>Please fill out this field</strong>");
		     return;
		}
	}
	if(response == "0") {
		result = "Didn't Response";
		isresponded = false;
	}
	$jq("#resultselectionvalidation").html("");
	
	
	var url = "/openmrs/ws/rest/v1/followup/save-update";
	var token = $jq("meta[name='_csrf']").attr("content");
	var header = $jq("meta[name='_csrf_header']").attr("content");

	var formData;

		formData = {
			"encounterUuid" : encounterUUid,
			"visitUuid" : visitUuid,
			"valueCoded" : valueCoded,
			"codedConceptName": codedConceptName,
			"contactDate" : contactDate,
			"isResponded": isresponded,
			"respondResult": result
		};
		console.log(formData)
		$jq("#savefollowup").show();
		event.preventDefault();
		$jq.ajax({
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
				$jq("#serverResponseMessage").show();
				$jq("#serverResponseMessage").html(data.message);
				$jq("#savefollowup").hide();
			   	if(data.isSuccess){					   
				   window.location.replace("/openmrs/module/PSI/follow-Up-dashboard.form");
			   }
			   
			},
			error : function(e) {
			   
			},
			done : function(e) {				    
			    console.log("DONE");				    
			}
		}); 
	};


</script>