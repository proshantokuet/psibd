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

</style>

<p><a href="${pageContext.request.contextPath}/module/PSI/product-list.form?id=${id}">Back to Product</a>
</p>

<br />

<div id="productListDiv" class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
		<div class="note">
		<p>Adjust History of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
		</div>	
	</div>
	<br />			
	<table id="stockList" class="display">
	    <thead>
	        <tr>
	            <th>Sl</th>
	            <th>Date</th>
	            <th>Product ID</th>
	            <th>Product Name</th>    
	            <th>Previous Stock</th>
	            <th>Changed Stock</th>
	            <th>Reason</th>
	            <th>Action</th>     
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="stock" items="${ adjustStockList }">
	        <tr>
	        	<td>${ stock.adjustId }</td>
	            <td>${ stock.adjustDate }</td>
	            <td>${ stock.productId }</td>
	             <td>${ stock.productName }</td>
	             <td>${ stock.previousStock }</td>
	             <td>${ stock.changedStock }</td>
	             <td>${ stock.adjustReason }</td>
	             <td><a class="btn btn-primary" href="<c:url value="/module/PSI/view-adjust-stock.form?id=${ stock.adjustId }&clinicid=${id}"/>"> View Details</a></td>
	        </tr>
	       </c:forEach>
	        
	    </tbody> 
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#stockList').DataTable({
        language: {
            emptyTable: "No adjust history available", //
            loadingRecords: "Please wait .. ", // default Loading...
            zeroRecords: "No matching adjust history found"
           },
           "order": [[ 1, "desc" ]],
			"searching": true,
			bFilter: false,
			bInfo: false
         });
} );

</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>