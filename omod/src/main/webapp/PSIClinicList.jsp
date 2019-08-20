<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic List" otherwise="/login.htm" />
<style>
.btn-primary {
    color: #fff;
    background-color: #387c7c;
    border-color: #387c7c;
}
</style>
<a href="${pageContext.request.contextPath}/module/PSI/addPSIClinic.form"><spring:message
				code="PSI.psiClinicAddNew" /></a> 
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Community Clinic List</p>        	
		</div>	
	</div>
	<br />
	<table id="table_id" class="display">
	    <thead>
	        <tr>
	            <th>#Id</th>
	            <th>Clinic Name</th>
	            <th>Clinic ID</th>
	            <th>Category</th>
	            <th>Upazila</th>
	            <th>Actions</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="clinic" items="${ pSIClinics }">
	        <tr>
	        	<td>${ clinic.cid }</td>
	            <td>${ clinic.name }</td>
	            <td>${ clinic.clinicId }</td>
	            <td>${ clinic.category }</td>
	            <td>${ clinic.upazila }</td>
	            <td> 
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/uploadPSIClinicService.form?id=${clinic.cid}"/>"> Upload Services</a>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicServiceList.form?id=${clinic.cid}"/>"> Services</a> 
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicUserList.form?id=${clinic.cid}"/>"> User List</a>  
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicSpotList.form?id=${ clinic.cid }"/>">Spots</a>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinic.form?id=${ clinic.cid }"/>"> Edit</a>
	             
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
       "order": [[ 1, "asc" ]]
       }		
	);
} );
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>