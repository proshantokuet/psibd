<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />

<%@ include file="template/localHeader.jsp"%>
<p><a
		href="${pageContext.request.contextPath}/module/PSI/addPSIClinicService.form"><spring:message
				code="PSI.psiservice" /></a> </p>
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Service List</p>        	
		</div>	
	</div>
	<br />			
	<table id="serviceList" class="display">
	    <thead>
	        <tr>
	            <th>#Id</th>
	            <th>Service Name</th>
	            <th>Service Code</th>
	            <th>Clinic Name</th>
	            <th>Service Category</th>
	            <th>Service Provider</th>
	            <th>Unit Cost(BDT)</th>
	            <th>Action</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="service" items="${ pSIServiceManagements }">
	        <tr>
	        	<td>${ service.sid }</td>
	            <td>${ service.name }</td>
	            <td>${ service.code }</td>	           
	            <td>${ service.psiClinicManagement.name }</td>
	            <td>${ service.category }</td>
	            <td>${ service.provider }</td>
	            <td>${ service.unitCost }</td>
	            <td>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicService.form?id=${ service.sid }"/>"> Edit</a>
	            <%-- <a href="<c:url value="/module/PSI/deletePSIClinicService.form?id=${ service.sid }"/>"> Delete</a> --%> 
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
	$jq('#serviceList').DataTable({
        language: {
            emptyTable: "no service available", //
            loadingRecords: "Please wait .. ", // default Loading...
            zeroRecords: "No matching service found"
           },
           "order": [[ 1, "asc" ]]
         });
} );
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>