<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic User List" otherwise="/login.htm" />

 <a href="${pageContext.request.contextPath}/module/PSI/addPSIClinicUser.form?id=${id}"><spring:message
				code="PSI.psiClinicUserAssing" /></a> 	
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Assign User List of ${ name }</p>
       	</div>					
	<table id="table_id" class="display">
	    <thead>
	        <tr>
	            <th>User Name</th> 
	            <th>Action</th>           
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="user" items="${ pSIClinicUsers }">
	        <tr>
	            <td>${ user.userName }</td>
	            <td><a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicUser.form?id=${ user.cuid }"/>"> Edit</a> </td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#table_id').DataTable();
} );
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>