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
#productListDiv{
	display:none;
}
</style>
<div id="loader"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<p><a href="${pageContext.request.contextPath}/module/PSI/add-stock.form?id=${id}">Add Stock</a> 
<%-- <a class="" href="" onclick="syncServiceFromGlobal(${id},'${psiClinicManagement.clinicId}')" style="margin-left: 10px;">Sync Services</a> --%>
<a class="" href="${pageContext.request.contextPath}/module/PSI/upload-stock.form?id=${id}"  style="margin-left: 10px;">Upload Stock</a>
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
		<p>Stock List of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>        	
		</div>	
	</div>
	<br />			
	<table id="stockList" class="display">
	    <thead>
	        <tr>
	            <th>#Sl</th>
	            <th>Recieve Date</th>
	            <th>Invoice Number</th>
	            <th>Stock In ID</th>
	            <th>Action</th>	            
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="stock" items="${ stockList }">
	        <tr>
	        	<td>${ stock.stkid }</td>
	            <td>${ stock.receiveDate }</td>
	            <td>${ stock.invoiceNumber }</td>
	             <td>${ stock.stockInId }</td>
	            <td>
	            <a class="btn btn-primary" href="<c:url value="/module/PSI/view-stock.form?id=${ stock.stkid }&clinicid=${id}"/>"> View Details</a>
	            </td>
	        </tr>
	       </c:forEach>
	        
	    </tbody> 
	</table>
</div>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(window).load(function() {
	$jq("#loader").hide();
	$jq("#productListDiv").show(); 
});
$jq(document).ready( function () {
	var title = "Invoice List Of " + "${psiClinicManagement.name }" +" - "+ "(${psiClinicManagement.clinicId })";
	$jq('#stockList').DataTable({
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

</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>