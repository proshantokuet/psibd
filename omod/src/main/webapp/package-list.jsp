<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />

<%@ include file="template/localHeader.jsp"%>

<style>
.dataTables_wrapper .dt-buttons {
  float:none;  
  text-align:right;
  position: absolute;
  top: -26px;
  margin-left: 1036px
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
    border: 1px solid #1aad96;
    /* background: #1aac9b url(images/ui-bg_inset-soft_30_ffffff_1x100.png) 50% 50% repeat-x; */
    font-weight: bold;
    background: #4aad9b;
    color: #0e5c52;
}

#loader{
	background-color:#fff;
	 padding: 15px;
  	 position: absolute;
     top: 50%;
     left: 50%;
     opacity:1.2;
   	-ms-transform: translateX(-50%) translateY(-50%);
  	-webkit-transform: translate(-50%,-50%);
  	transform: translate(-50%,-50%);
}

</style>
<div id="loader" style="display: none;"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<p><a href="${pageContext.request.contextPath}/module/PSI/add-package.form?id=${id}">Add Package</a>
<a class="" href="" onclick="syncServiceFromGlobal(${id},'${psiClinicManagement.clinicId}')" style="margin-left: 10px;">Sync Package</a>
 </p>
<div id="message" style="font-weight: bold;position: absolute; z-index: 1000;margin-left:36%"></div>			
<div id="loader_clinic_list" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
</div>
<br />
<!-- <div id="message" style="font-weight: bold;position: absolute; z-index: 1000;margin-left:38%"></div>-->
<div id="productListDiv" class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Package List of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
		</div>	
	</div>
	<br />			
	<table id="packageList" class="display">
	    <thead>
	        <tr>
	            <th>#Sl</th>
	            <th>Package Name</th>
	            <th>Package Code</th>
	            <th>Accumulated Price</th>
	            <th>Package Price</th>	 
	            <th>Status</th>
	            <th>Action</th>           
	        </tr>
	    </thead>
	    	    <tbody>
	    	<c:forEach var="packageProduct" items="${ packageList }">
	        <tr>
	        	<td>${ packageProduct.packageId }</td>
	            <td>${ packageProduct.packageName }</td>
	            <td>${ packageProduct.packageCode }</td>
	            <td>${ packageProduct.accumulatedPrice }</td>
	            <td>${ packageProduct.packagePrice }</td>
	            <c:if test="${packageProduct.voided}">
	            <td >Inactive</td>
	            </c:if>
	            <c:if test="${!packageProduct.voided}">
	            <td >Active</td>
	            </c:if>
	            <td>
<%-- 	            <a class="btn btn-primary" href="<c:url value="/module/PSI/edit-package.form?id=${ packageProduct.packageId }&clinicid=${id}"/>"> Edit</a>
 --%>	            </td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();

$jq(document).ready( function () {
	$jq('#packageList').DataTable({
        language: {
            emptyTable: "no stock available", //
            loadingRecords: "Please wait .. ", // default Loading...
            zeroRecords: "No matching stock found"
           },
           "order": [[ 1, "asc" ]],
			"searching": true,
			bFilter: false,
			bInfo: false
         });
} );

function syncServiceFromGlobal(clinicId, code) {
	var url = "/openmrs/ws/rest/v1/package/package-sync/" + clinicId + "/" + code;
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
		   if(data == "Sync Success"){					   
			   window.location.replace("/openmrs/module/PSI/package-list.form?id="+clinicId);
		   }
	    },
	    error:function(data){
	    	$jq("#loader_clinic_list").hide();
	    }
	    
	});
};

</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>