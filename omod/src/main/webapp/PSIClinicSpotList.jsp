<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic Spot List" otherwise="/login.htm" />

<%--  <a href="${pageContext.request.contextPath}/module/PSI/addPSIClinicSpot.form?id=${id}"><spring:message
				code="PSI.psiClinicSpotAddNew" /></a>
<a style="padding-left: 10px;" href="${pageContext.request.contextPath}/module/PSI/uploadPSIClinicSpot.form?id=${id}"><spring:message
                code="PSI.psiClinicUploadCsv" /></a> --%>
<a class="" href="" onclick="syncSpotsGlobal(${id},'${psiClinicManagement.clinicId}')" style="margin-left: 10px;">Sync Spots</a>
 <div id="loader_clinic_list" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
</div>
	<br /> 
<div id="message" style="font-weight: bold;position: inherit; z-index: 1000;margin-left:37%"></div>			

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Community Clinic Spot List of ${PSIClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
		</div>	
	</div>
	<br />
	<table id="table_id" class="display">
	    <thead>
	        <tr>
	            <th>#Id</th>
	            <th>Clinic Name</th>
	            <th>Spot Name</th>
	            <th>Code</th>	            
	            <th>Address</th>
	            <th>Dhis2 Org Id</th>
	            <th>Actions</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="spot" items="${ pSIClinicSpots }">
	        <tr>
	        	<td>${ spot.ccsid }</td>
	        	<td>${ spot.psiClinicManagement.name }</td>
	            <td>${ spot.name }</td>
	            <td>${ spot.code }</td>
	             <td>${ spot.address }</td>
	            <td>${ spot.dhisId }</td>	           
	            <td> 	            
	            <%-- <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicSpot.form?id=${ spot.ccsid }"/>"> Edit</a> --%>
	             
	            </td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#table_id').DataTable(
	{
		language: {
        emptyTable: "no Clinic available", //
        loadingRecords: "Please wait .. ", // default Loading...
        zeroRecords: "No matching service found"
       },
       bFilter: true,
       bInfo: false,
       "order": [[ 2, "asc" ]]
       }		
	);
} );

function syncSpotsGlobal(clinicId,code) {
	var url = "/openmrs/ws/rest/v1/clinic/spot/sync/" + clinicId + "/" + code;
	var token = $jq("meta[name='_csrf']").attr("content");
	var header = $jq("meta[name='_csrf_header']").attr("content");
	event.preventDefault();
	$jq("#loader_clinic_list").show();
	$jq.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
			$jq("#message").html(data);
			$jq("#loader_clinic_list").hide();
		   if(data == "Success"){					   
			   window.location.replace("/openmrs/module/PSI/PSIClinicSpotList.form?id="+clinicId);
		   }
	    },
	    error:function(data){
	    	$jq("#loader_clinic_list").hide();
	    }
	    
	});
};
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>