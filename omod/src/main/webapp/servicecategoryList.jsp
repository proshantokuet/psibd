<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Service Category List" otherwise="/login.htm" />
<style>
table.dataTable tbody th, table.dataTable tbody td {
     padding: 0px 0px; 
}

</style>
 <a href="${pageContext.request.contextPath}/module/PSI/addServiceCategory.form"><spring:message
				code="PSI.addNewserviceCategory" /></a> 	
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Service Category List</p>
       	</div>					
	<table id="table_id" class="display cell-border compact">
	    <thead>
	        <tr>
	        	<th>Id</th> 
	            <th>Category Name</th> 
	            <th>Actions</th>         
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="cat" items="${ serviceCategories }">
	        <%-- <tr>
	            <td>${ user.firstName } ${ user.lastName }</td>
	            <td>${ user.userName }</td>
	            <td>${ user.email }</td>
	            <td>${ user.mobile }</td>
	            <td>${ user.role }</td>
	           	<c:if test="${user.retired}">
	            <td >Inactive</td>
	            </c:if>
	            <c:if test="${!user.retired}">
	            <td >Active</td>
	            </c:if>
	            <td ><a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicUser.form?id=${ user.cuid }"/>"> Edit</a> </td>
	        </tr> --%>
	        <tr>
	        	<td>${cat.sctid }</td>
	        	<td>${cat.categoryName }</td>
	        	<td><a class="btn btn-primary" href="<c:url value="/module/PSI/editServiceCategory.form?sctid=${ cat.sctid }"/>"> Edit</a></td>
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