<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
			
<table id="table_id" class="display">
    <thead>
        <tr>
            <th>Clinic Name</th>
            <th>Clinic ID</th>
            <th>Category</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="clinic" items="${ pSIClinics }">
        <tr>
            <td><a href="<c:url value="/module/PSI/editPSIClinic.form?id=${ clinic.name }"/>"> ${ clinic.name }</a></td>
            <td>${ clinic.clinicId }</td>
            <td>${ clinic.category }</td>
            <td>${ clinic.address }</td>
            <td><a href="<c:url value="/module/PSI/deletePSIClinic.form?id=${ clinic.cid }"/>"> Delete</a> </td>
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