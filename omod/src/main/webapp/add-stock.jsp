<%-- <%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
 <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Add Clinic Service" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/font-awesome.min.css">
<c:url var="saveUrl" value="/module/PSI/addPPSIClinicService.form" />
<c:url var="cancelUrl" value="/module/PSI/product-list.form?id=${id}" /> --%>

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>

<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/font-awesome.min.css">
<c:url var="cancelUrl" value="/module/PSI/stock-invoice-list.form?id=${id}" /> 

<style>
.select2-container--default .select2-selection--single {
    width: 190px !important;
    height: 39px !important;
}
.select2-container--default .select2-selection--single .select2-selection__arrow {
    left: 165px !important;
}
</style>

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Add Stock</p>
        	
       	</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<div class="form-content">
				<div style="display: none;" class="alert alert-success" id="serverResponseMessage" role="alert"></div>
				<div style="display: none;" class="alert alert-danger" id="validationFailedMessage" role="alert"></div>
					<p><span class="text-danger"> * Required Fields</span></p>
				<div class="row">
					<div class="col-lg-3 form-group">
						<label for="invoiceNo">Invoice Number:</label><span class="text-danger"> *</span> 
						<input type="text" style="height: 39px;" class="form-control" id="invoiceNo" >
							<span id="invoiceNoValidation" class="text-danger"></span>
					</div>
					<div class="col-lg-3 form-group">
						<label for="receiveDate">Receive Date:</label><span class="text-danger"> *</span>  
						<input type="text" name="receiveDate" class="form-control" style="height: 39px;" id="receiveDate">
							<span id="receiveDateValidation" class="text-danger"></span>
					</div>
				</div>
				<div><p><strong>Product Information:</strong></p></div>
				
				<div class="row">
					<div class="col-md-2 form-group">
						<label for="productName">Product Name:</label><span class="text-danger"> *</span> 
						<select class="form-control role-multiple" id="product" name="product">
										<option value="">Select Product</option>
										<c:forEach items="${productList}" var="product">
											<option value="${product.sid}_${product.name}">${product.name}</option>
										</c:forEach>
								</select>
								<span id="productselectvalidation" class="text-danger"></span>
					</div>
					<div class="col-md-2 form-group">
						<label for="productId">Product ID:</label>  
						<input type="text" class="form-control" style="height: 39px;" name="productId" id="productId" readonly>
					</div>
					<div class="col-md-2 form-group">
						<label for="currentStock">Current Stock:</label>  
						<input type="text" class="form-control" style="height: 39px;" name="currentStock" id="currentStock" readonly>
						
					</div>
					<div class="col-md-2 form-group">
						<label for="quantity">Stock Added:</label><span class="text-danger"> *</span> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="quantity" >
							<span id="QuantityNoValidation" class="text-danger"></span>
					</div>
					<div class="col-md-2 form-group">
						<label for="expiryDate">Expiry Date:</label><span class="text-danger"> *</span>  
						<input type="text" class="form-control" style="height: 39px;" name="expiryDate" id="expiryDate">
							<span id="expiryDateValidation" class="text-danger"></span>
					</div>
					<div class="col-md-2 form-group">
						<div style="padding-top: 30px;">
						<button type="button" onclick="appendRowInTable()" class="btn btn-info" value="confirm">Add Product</button>
						</div>
					</div>
				</div>
				<!-- <div class="row">
					<div class="col-lg-3 form-group">
						<label for="invoiceNo">Stock Added:</label><span class="text-danger"> *</span> 
						<input type="number" min="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="quantity" >
							<span id="invoiceNoValidation" class="text-danger"></span>
					</div>
					<div class="col-lg-3 form-group">
						<label for="receiveDate">Stock After Addition:</label> 
						<input type="text" name="stockadded" id="stockadded" readonly>
					</div>
					<div class="col-lg-3 form-group">
						<div style="padding-top: 30px;">
						<a class="btn btn-info add-record" id="newInvoice"><i class="glyphicon glyphicon-plus"></i>
						<strong>Add Product </strong>
					</a>
						</div>
					</div>
				</div> -->
				
				<!-- <div class="col-lg-12 form-group" style="text-align: right;">
					<a class="btn btn-info add-record"   id="newInvoice"><i class="glyphicon glyphicon-plus"></i>
						<strong>Add Product </strong>
					</a>
				</div> -->
				
					<div>
					<p class="text-danger" id="validationMessage"><strong></strong></p>
				</div>
				<div class="table-scrollable ">
					<table class="table table-striped table-bordered " id="trainingList">
							<thead>
						<tr>
							<th>Serial No</th>
							<th>Product Name</th>
							<th>Product ID</th>
							<th>Current Stock</th>
							<th>Stock Added</th>
							<th>After Added</th>
							<th>Expiry Date</th>
							<th>Action</th>
						</tr>
					</thead>
							<tbody id="tbl_posts_body">
							</tbody>
						</table>
						
						</div>
				
				<div class="text-center">
	                <button type="submit" onclick="saveStockData()" class="btn btn-primary" value="confirm">Confirm All</button>&nbsp;<a href="${cancelUrl}">Back</a>
	            </div>
            </div>
   	</div>
       
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/select2.js"></script>
<!-- <script>
var $jq = jQuery.noConflict();
$jq( function() {
	$jq("#receiveDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#expiryDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
</script> -->
<script>
jQuery(document).ready(function($) {   
	$("#receiveDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$("#expiryDate").datepicker({ dateFormat: 'yy-mm-dd', minDate: new Date,changeYear: true,changeMonth: true });
	$('#product').select2({dropdownAutoWidth : true});
}); 

function saveStockData() {
	if(jQuery("#invoiceNo").val() == "") {
		jQuery("#invoiceNoValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#invoiceNoValidation").html("");
	if(jQuery("#receiveDate").val() == 0) {
		jQuery("#receiveDateValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#receiveDateValidation").html("");
	
	
	var url = "/openmrs/ws/rest/v1/stock/save-update";
	var token = jQuery("meta[name='_csrf']").attr("content");
	var header = jQuery("meta[name='_csrf_header']").attr("content");


	var stockArray = [];

	jQuery('#trainingList > tbody > tr').each(function(index, tr) {
				var stockObject = {};
				var $row = jQuery(this).closest('tr'); //get the current row
				var productName = $row.find('td').eq(1).text();
				var productId = $row.find('td').eq(2).text();
				var stockAdded = $row.find('td').eq(4).text();
				var expiryDate = $row.find('td').eq(6).text();
				stockObject["productID"] = parseInt(productId);
				stockObject["productName"] = productName;
				stockObject["debit"] = parseInt(stockAdded);
				stockObject["credit"] = 0;
				stockObject["expiryDate"] = expiryDate;
				stockArray.push(stockObject);
			});
	if(stockArray.length < 1) {
		jQuery("#validationFailedMessage").show();
		jQuery("#validationFailedMessage").html("<strong>* Please fill out the required fields</strong>");
		jQuery(window).scrollTop(0);
		 return;
	}
	jQuery("#validationFailedMessage").hide();
	var formData;

		formData = {
			"stockId" : 0,
			"clinicName" : "${psiClinicManagement.name }",
			"clinicCode" : "${psiClinicManagement.clinicId }",
			"clinicId":${id},
			"invoiceNumber" : jQuery("#invoiceNo").val(),
			"receiveDate" : jQuery("#receiveDate").val(),
			"stockDetails" : stockArray
		};
		console.log(formData)
		jQuery("#loading").show();
		jQuery(window).scrollTop(0);
		event.preventDefault();
		jQuery.ajax({
			contentType : "application/json",
			type: "POST",
	        url: url,
	        data: JSON.stringify(formData), 
	        dataType : 'json',
	        
			timeout : 100000,
			beforeSend: function(xhr) {				    
				 xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				jQuery("#serverResponseMessage").show();
				jQuery("#serverResponseMessage").html(data.message);
				jQuery("#loading").hide();
			   	if(data.stockId){					   
				   window.location.replace("/openmrs/module/PSI/stock-invoice-list.form?id=${id}");
				   
			   }
			   
			},
			error : function(e) {
			   
			},
			done : function(e) {				    
			    console.log("DONE");				    
			}
		}); 
	};

jQuery("#product").change(function (event) {
	if(jQuery("#product").val() == "") {
		return;
	}
	var productId = jQuery("#product").val().split("_")[0];
	jQuery("#productId").val(productId);
	var clinicId = parseInt("${id}");
	var url = "/openmrs/ws/rest/v1/stock/get-product-stock/"+clinicId+ "/" +productId;
	event.preventDefault();
	jQuery.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'json',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	//var response = JSON.parse(data);
	    	jQuery("#currentStock").val(data.stock);
	    },
	    error:function(data){
	    	console.log(data);
	    }
	    
	});
	
	//$(this).parents('td').next().text(selectedText);
});
	
function appendRowInTable() {
	if(jQuery("#product").val() == "") {
		jQuery("#productselectvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#productselectvalidation").html("");
	var quantity = parseInt(jQuery("#quantity").val());
	var stock = parseInt(jQuery("#currentStock").val());
	if(quantity == 0) {
		jQuery("#QuantityNoValidation").html("<strong>Stock can not be zero</strong>");
		return;
	}
	jQuery("#QuantityNoValidation").html("");
	if(isNaN(quantity)) {
		jQuery("#QuantityNoValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#QuantityNoValidation").html("");
	if(jQuery("#expiryDate").val() == "") {
		jQuery("#expiryDateValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#expiryDateValidation").html("");
	
	var productName = jQuery("#product").val().split("_")[1];
	var productId = jQuery("#productId").val();
	var currentStock = jQuery("#currentStock").val();
	var stockAdded = jQuery("#quantity").val();
	var afteAdded = parseInt(currentStock) + parseInt(stockAdded);
	var expiryDate = jQuery("#expiryDate").val();
	var size = jQuery('#trainingList >tbody >tr').length + 1;
	jQuery("#trainingList tbody").append("<tr id='rec-"+size+"'><td><span class=\"sn\">"+size+"</span>.</td><td>"+productName+"</td><td>"+productId+"</td><td>"+currentStock+"</td><td>"+stockAdded+"</td><td>"+afteAdded+"</td><td>"+expiryDate+"</td><td><a class=\"btn btn-xs delete-record\" data-id="+size+"><i class=\"fa fa-trash\"></i></a></td></tr>");

	jQuery('#product').val('').trigger('change');
	jQuery("#productId").val("");
	jQuery("#currentStock").val("");
	jQuery("#quantity").val("");
	jQuery("#expiryDate").val("");
	

}
	
jQuery(document).delegate('a.delete-record', 'click', function(e) {
	e.preventDefault();
	var didConfirm = confirm("Are you sure You want to delete");
	if (didConfirm == true) {
		var id = jQuery(this).attr('data-id');
		//var targetDiv = jQuery(this).attr('targetDiv');
		jQuery('#rec-' + id).remove();

		//regnerate index number on table
		jQuery('#tbl_posts_body tr').each(function(index) {
			jQuery(this).find('span.sn').html(index + 1);
		});
		return true;
	} else {
		return false;
	}
});
</script>


<%@ include file="/WEB-INF/template/footer.jsp"%>