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
	                  		<label for="syncType">Sync Type</label> <br />
	                  		<select name="syncType" id="syncType" required="true" class="form-control selcls">
	                  			<option value="0">Please Select</option>
	                  			<option value="Patient">Patient</option>
	                  			<option value="Money Receipt">Money Receipt</option>
	                  			<option value="Encounter">Encounter</option>
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
							<label>${totalTransferred}</label> &nbsp;&nbsp; Total
							Transferred
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>${syncInProgress}</label> &nbsp;&nbsp; Sync In Progress
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>${totalFailed}</label> &nbsp;&nbsp; Sync Failed
						</div>
					</div>
				</div>
			</div>
			<div style="overflow:auto;">
			<table id="table_id" class="display cell-border compact">
				<thead>
					<tr>
						<th>Sl</th>
						<th>actionType</th>
						<th>identifier</th>
						<th>Error</th>
					</tr>
				</thead>
				<tbody>
				<% int sl_d = 0; %>
					<c:forEach var="item" items="${ report }">
						<tr>
							<td><%=++sl_d%></td>
							<td>${ item.actionType }</td>
							<td>${ item.identifier }</td>
							<td>${ item.error }</td>
						</tr>
					</c:forEach>
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
		                 title: "Global Server Data Sync Report",
		                 text: 'Export as .xlxs'
		             },
		             {
			         		extend: 'pdfHtml5',
			         		title: "Global Server Data Sync Report",
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
	var action_type = $jq("#syncType").val();
	if(action_type.val == "") {
		return;
	}
	var url = "/openmrs/module/PSI/globalServerSyncInfoByFilter.form?action_type="+action_type;
	
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
					                 title: action_type + " Data Sync Report",
					                 text: 'Export as .xlxs'
					             },
					             {
						         		extend: 'pdfHtml5',
						         		title: action_type + " Data Sync Report",
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

</script>