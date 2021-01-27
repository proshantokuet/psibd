<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />

<%@ include file="template/localHeader.jsp"%>
<p>
<%
	if (isDeployInGlobal.equalsIgnoreCase("1")) {
%>
<a href="${pageContext.request.contextPath}/module/PSI/addPSIClinicService.form?id=${id}"><spring:message
				code="PSI.psiservice" /></a>
<% } %>
<%
	if (isDeployInGlobal.equalsIgnoreCase("0")) {
%>
<a class="" href="" onclick="syncServiceFromGlobal(${id},'${psiClinicManagement.clinicId}')" style="margin-left: 10px;">Sync Services</a>
<% } %>
</p>
<div id="message" style="font-weight: bold;position: absolute; z-index: 1000;margin-left:36%"></div>			
<div id="loader_clinic_list" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
</div>
<br />
<!-- <div id="message" style="font-weight: bold;position: absolute; z-index: 1000;margin-left:38%"></div>-->
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Service List of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
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
	            <th>Status</th>
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
	            <c:if test="${service.voided}">
	            <td >Inactive</td>
	            </c:if>
	            <c:if test="${!service.voided}">
	            <td >Active</td>
	            </c:if>
	            <td>
	            <%
					if (isDeployInGlobal.equalsIgnoreCase("1")) {
				%>
	             <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinicService.form?id=${ service.sid }"/>"> Edit</a>
	             <% } %>
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

function syncServiceFromGlobal(clinicId, code) {
	var url = "/openmrs/ws/rest/v1/clinic/service/sync/" + clinicId + "/" + code;
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
			   window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form?id="+clinicId);
		   }
	    },
	    error:function(data){
	    	$jq("#loader_clinic_list").hide();
	    }
	    
	});
};
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>