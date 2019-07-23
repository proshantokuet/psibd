<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic List" otherwise="/login.htm" />

<a href="${pageContext.request.contextPath}/module/PSI/addPSIClinicSpot.form?id=${id}"><spring:message
				code="PSI.psiClinicSpotAddNew" /></a> 
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Community Clinic Spot List of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
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
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicSpot.form?id=${ spot.ccsid }"/>"> Edit</a>
	             
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
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>