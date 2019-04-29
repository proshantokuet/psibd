<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />

<%@ include file="template/localHeader.jsp"%>
<p><a
		href="${pageContext.request.contextPath}/module/PSI/addPSIClinicService.form"><spring:message
				code="PSI.psiservice" /></a> </p>			
<table id="serviceList" class="display">
    <thead>
        <tr>
            <th>#Id</th>
            <th>Clinic Name</th>
            <th>Service Code</th>
            <th>Item Name</th>
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
            <td>${ service.psiClinicManagement.name }</td>
            <td>${ service.code }</td>
            <td>${ service.name }</td>
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

<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#serviceList').DataTable();
} );
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>