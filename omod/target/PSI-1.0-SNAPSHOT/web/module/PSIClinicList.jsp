<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic List" otherwise="/login.htm" />

<a href="${pageContext.request.contextPath}/module/PSI/addPSIClinic.form"><spring:message
				code="PSI.psiClinicAddNew" /></a> 	
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>#Id</th>
            <th>Clinic Name</th>
            <th>Clinic ID</th>
            <th>Category</th>
            <th>Address</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="clinic" items="${ pSIClinics }">
        <tr>
        	<td>${ clinic.cid }</td>
            <td>${ clinic.name }</td>
            <td>${ clinic.clinicId }</td>
            <td>${ clinic.category }</td>
            <td>${ clinic.address }</td>
            <td> <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicUserList.form?id=${clinic.cid}"/>"> User List</a>  <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinic.form?id=${ clinic.cid }"/>"> Edit</a> </td>
        </tr>
       </c:forEach>
        
    </tbody>
</table>

<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#table_id').DataTable();
} );
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>