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

.dataTables_wrapper .dataTables_filter {
    float: left;
    text-align: right;
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
#productListDiv{
	display:none;
}
</style>
<div id="loader"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<p><a href="${pageContext.request.contextPath}/module/PSI/add-product.form?id=${id}">Add Product</a> 
<a class="" href="${pageContext.request.contextPath}/module/PSI/upload-product.form?id=${id}"  style="margin-left: 10px;">Upload Product</a>
<a class="" href="" onclick="syncServiceFromGlobal(${id},'${psiClinicManagement.clinicId}')" style="margin-left: 10px;">Sync Product</a>
<a class="" href="${pageContext.request.contextPath}/module/PSI/adjust-history.form?id=${id}" style="margin-left: 10px;">Adjust History</a>


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
		<p>Product List of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
		</div>	
	</div>
	<br />			
	<table id="productList" class="display">
	    <thead>
	        <tr>
	            <th>#Id</th>
	            <th>Product Name</th>
	            <th>Product Code</th>
	            <th>Brand Name</th>
	            <th>Category</th>
	            <th>Purchase Price(Per Unit)</th>
	            <th>MRP(Per Unit)</th>
	            <th>Stock</th>
	            <th>Earliest Expiry Date</th>
	            <th>Status</th>
	            <th>Action</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="product" items="${ productList }">
	        <tr>
	        	<td>${ product.sid }</td>
	            <td>${ product.name }</td>
	            <td>${ product.code }</td>
	             <td>${ product.brandName }</td>
	            <td>${ product.category }</td>	           
	            <td>${ product.purchasePrice }</td>
	            <td>${ product.unitCost }</td>
	             <td>${ product.stock }</td>
	             <td>${ product.earliestExpiry }</td>
	            <c:if test="${product.voided}">
	            <td >Inactive</td>
	            </c:if>
	            <c:if test="${!product.voided}">
	            <td >Active</td>
	            </c:if>
	            <td>
	            <div><a class="btn btn-primary" href="<c:url value="/module/PSI/edit-product.form?id=${ product.sid }"/>"> Edit</a></div>
	            <div style="padding-top: 5px;"><a class="btn btn-primary" href="<c:url value="/module/PSI/adjust-stock.form?id=${ product.sid }&clinicid=${id}"/>"> Adjust</a></div>
	            </td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/pdfmake.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.print.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/vfs_fonts.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(window).load(function() {
	$jq("#loader").hide();
	$jq("#productListDiv").show(); 
});
$jq(document).ready( function () {
	var title = "Product List Of " + "${psiClinicManagement.name }" +" - "+ "(${psiClinicManagement.clinicId })";
	$jq('#productList').DataTable({
        language: {
            emptyTable: "no product available", //
            loadingRecords: "Please wait .. ", // default Loading...
            zeroRecords: "No matching product found"
           },
           "order": [[ 1, "asc" ]],
			"searching": true,
			bFilter: false,
			bInfo: false,
			dom: 'Bfrtip',
				   destroy: true,
				   buttons: [
				             {
				                 extend: 'excelHtml5',
				                 title: title,
				                 text: 'Export as .xlxs'
				             },
				             {
				         		extend: 'pdfHtml5',
				         		title: title,
				         		text: 'Export as .pdf',
				         		orientation: 'landscape',
				         		pageSize: 'LEGAL'
				         	  }
				         ]
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