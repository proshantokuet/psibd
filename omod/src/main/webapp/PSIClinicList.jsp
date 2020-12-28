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
<%-- <a href="${pageContext.request.contextPath}/module/PSI/addPSIClinic.form"><spring:message
				code="PSI.psiClinicAddNew" /></a>  --%>
<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Clinic List</p>        	
		</div>	
	</div>
	  	<div id="loader_clinic_list" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	<br />
	<div id="message" style="font-weight: bold;position: absolute; z-index: 1000;margin-left:30%"></div>
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
	            <div style="padding-top: 5px;">
	             <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicSpotList.form?id=${ clinic.cid }"/>">Spots</a>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/product-list.form?id=${ clinic.cid }"/>">Products</a>
				<a class="btn btn-primary" href="<c:url value="/module/PSI/stock-invoice-list.form?id=${ clinic.cid }"/>">Stock</a>	            
				<a class="btn btn-primary" href="<c:url value="/module/PSI/package-list.form?id=${ clinic.cid }"/>">Packages</a>
	            
	            </div>
	            <div style="padding-top: 5px;">
	            <a class="btn btn-primary" onclick="syncClinicFromGlobal('${ clinic.clinicId }')">Sync</a>
	            <%-- <a class="btn btn-primary" href="<c:url value="/module/PSI/dhis-data-upload.form?id=${clinic.cid}"/>">Data Upload Dhis2</a> --%>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinic.form?id=${ clinic.cid }"/>"> Edit</a>
	            </div>
	             
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


function syncClinicFromGlobal(clinicCode) {
	var url = "/openmrs/ws/rest/v1/clinic/sync/byClinicCode/" + clinicCode;
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
			   window.location.replace("/openmrs/module/PSI/PSIClinicList.form");
		   }
	    },
	    error:function(data){
	    	$jq("#loader_clinic_list").hide();
	    }
	    
	});
};
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>