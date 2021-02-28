<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>

<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/font-awesome.min.css">
<c:url var="cancelUrl" value="/module/PSI/package-list.form?id=${id}" /> 

<style>
.select2-container--default .select2-selection--single {
    height: 39px !important;
}
</style>

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Add Package</p>
        	
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
						<label for="invoiceNo">1.Package Name:</label><span class="text-danger"> *</span> 
						<input type="text" style="height: 39px;" class="form-control" id="packageName">
						<span class="text-danger" id="packagenameValidation"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3 form-group">
						<label for="invoiceNo">2.Package Code:</label><span class="text-danger"> *</span> 
						<input type="text" style="height: 39px;" class="form-control" id="packageCode">
						<span class="text-danger" id="packageCodeValidation"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3 form-group">
					<label for="invoiceNo">3. Product :</label>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addproductmodal">
					  Add Product
					</button>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3 form-group">
					<label for="invoiceNo">4. Service :</label>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addservicemodal">
					  Add Service
					</button>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3 form-group">
						<label for="invoiceNo">5. Package Price:</label><span class="text-danger"> *</span> 
						<input type="text" style="height: 39px;" class="form-control" id="totalPackagePrice" readonly>
					</div>
				</div>
				<div><p><strong>Product/Service Information:</strong></p></div>
					<div>
					<p class="text-danger" id="validationMessage"><strong></strong></p>
				</div>
				<div class="table-scrollable ">
					<table class="table table-striped table-bordered " id="trainingList">
							<thead>
						<tr>
							<th>Serial No</th>
							<th>Name</th>
							<th>ID</th>
							<th>Code</th>
							<th>Quantity</th>
							<th>Unit Price</th>
							<th>Amount</th>
							<th>Unit Price(Package)
							<th>Amount</th>
							<th>Action</th>
						</tr>
					</thead>
							<tbody id="tbl_posts_body">
							</tbody>
						</table>
						
						</div>
				
				<div class="text-center">
	                <button type="submit" onclick="savePackageData()" class="btn btn-primary" value="confirm">Confirm All</button>&nbsp;<a href="${cancelUrl}">Back</a>
	            </div>
            </div>
            
        <!-- Modal -->
		<div class="modal" id="addproductmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-md" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Add Product</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		       <div class="row">
					<div class="col-lg-8 form-group">
						<label for="invoiceNo">1. Product:</label><span class="text-danger"> *</span> 
						<select class="form-control role-multiple" id="product" style="height: 39px;" name="product">
										<option value="">Select Product</option>
										<c:forEach items="${productList}" var="product">
											<option value="${product.sid}_${product.name}_${product.code}_${product.unitCost}">${product.name}</option>
										</c:forEach>
								</select>
								<span id="productselectvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">2. Product ID:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="productId" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">3. Product Code:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="productCode" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">4. Unit Price:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="unitPrice" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">5. Quantity:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="productQuantity">
						<span id="productQuantityvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">6. Total Amount:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="productAmount" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">7. Unit Price in Package:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="unitpriceinPackage">
						<span id="unitPriceproductvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">8. Price in Package:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="priceinPackage" readonly>
					</div>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" onclick="appendRowForProductInTable()" class="btn btn-primary">Add</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<div class="modal" id="addservicemodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-md" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Add Service</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		       <div class="row">
					<div class="col-lg-8 form-group">
						<label for="invoiceNo">1. Service:</label><span class="text-danger"> *</span> 
						<select class="form-control role-multiple" id="service" style="height: 39px;" name="product">
										<option value="">Select Service</option>
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.sid}_${service.name}_${service.code}_${service.unitCost}">${service.name}</option>
										</c:forEach>
								</select>
								<span id="serviceselectvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">2. Service ID:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="serviceId" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">3. Service Code:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="serviceCode" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">4. Unit Price:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="serviceUnitPrice" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">5. Quantity:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="serviceQuantity">
						<span id="serviceQuantityvalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">6. Total Amount:</label> 
						<input type="text" style="height: 39px;" class="form-control" id="serviceAmount" readonly>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">7. Unit Price in Package:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="serviceUnitPriceinPackage">
						<span id="serviceUnitPriceinPackagevalidation" class="text-danger"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 form-group">
						<label for="invoiceNo">8. Price in Package:</label> 
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="servicePriceinPackage" readonly>
					</div>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" onclick="appendRowForServiceInTable()" class="btn btn-primary">Add</button>
		      </div>
		    </div>
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
	$('#service').select2({dropdownAutoWidth : true});
	$('#product').select2({dropdownAutoWidth : true});
}); 

function closemodal() {
	jQuery('#exampleModalCenter').modal('hide');

}

jQuery("#product").change(function (event) {
	if(jQuery("#product").val() == "") {
		return;
	}
	var productId = jQuery("#product").val().split("_")[0];
	var productCode = jQuery("#product").val().split("_")[2];
	var unitPrice = jQuery("#product").val().split("_")[3];
	jQuery("#productCode").val(productCode);
	jQuery("#unitPrice").val(unitPrice);
	jQuery("#productId").val(productId);
	//$(this).parents('td').next().text(selectedText);
});

jQuery('#addproductmodal').on('hidden.bs.modal', function (e) {
	jQuery("#productCode").val("");
	jQuery("#unitPrice").val("");
	jQuery("#productId").val("");
	//jQuery("#product").val("");
	jQuery('#product').val('').trigger('change');
	jQuery("#productAmount").val("");
	jQuery("#productQuantity").val("");
	jQuery("#priceinPackage").val("");
	jQuery("#unitpriceinPackage").val("");
})

jQuery("#service").change(function (event) {
	if(jQuery("#service").val() == "") {
		return;
	}
	var serviceId = jQuery("#service").val().split("_")[0];
	var serviceCode = jQuery("#service").val().split("_")[2];
	var serviceUnitPrice = jQuery("#service").val().split("_")[3];
	jQuery("#serviceCode").val(serviceCode);
	jQuery("#serviceUnitPrice").val(serviceUnitPrice);
	jQuery("#serviceId").val(serviceId);
	//$(this).parents('td').next().text(selectedText);
});

jQuery('#addservicemodal').on('hidden.bs.modal', function (e) {
	jQuery("#serviceCode").val("");
	jQuery("#serviceUnitPrice").val("");
	jQuery("#serviceId").val("");
	//jQuery("#service").val("");
	jQuery('#service').val('').trigger('change');
	jQuery("#serviceAmount").val("");
	jQuery("#serviceQuantity").val("");
	jQuery("#servicePriceinPackage").val("");
	jQuery("#serviceUnitPriceinPackage").val("");
})

jQuery('#addproductmodal').delegate('#productQuantity', 'input propertychange', function (event) {
	var thisQuantity = +jQuery(this).val();
	var unitPrice = jQuery("#unitPrice").val();
	var totalAmount = parseFloat(unitPrice) * thisQuantity;
	jQuery("#productAmount").val(totalAmount);
	var unitPriceInPackage = jQuery("#unitpriceinPackage").val();
	if(unitPriceInPackage != "") {
		var quantity = thisQuantity;
		var totalAmount = parseFloat(unitPriceInPackage) * quantity;
		jQuery("#priceinPackage").val(totalAmount);
	}
});

jQuery('#addproductmodal').delegate('#unitpriceinPackage', 'input propertychange', function (event) {
	var thisPrice = +jQuery(this).val();
	var quantity = +jQuery("#productQuantity").val();
	var totalAmount = parseFloat(thisPrice) * quantity;
	jQuery("#priceinPackage").val(totalAmount);
});

jQuery('#addservicemodal').delegate('#serviceUnitPriceinPackage', 'input propertychange', function (event) {
	var thisPrice = +jQuery(this).val();
	var quantity = +jQuery("#serviceQuantity").val();
	var totalAmount = parseFloat(thisPrice) * quantity;
	jQuery("#servicePriceinPackage").val(totalAmount);
});

jQuery('#addservicemodal').delegate('#serviceQuantity', 'input propertychange', function (event) {
	var thisQuantity = +jQuery(this).val();
	var unitPrice = jQuery("#serviceUnitPrice").val();
	var totalAmount = parseFloat(unitPrice) * thisQuantity;
	jQuery("#serviceAmount").val(totalAmount);
	var serviceUnitPriceinPackage = jQuery("#serviceUnitPriceinPackage").val();
	if (serviceUnitPriceinPackage != "") {
		var quantity = thisQuantity;
		var totalAmount = parseFloat(serviceUnitPriceinPackage) * quantity;
		jQuery("#servicePriceinPackage").val(totalAmount);
	}
});

function appendRowForProductInTable() {
	if(jQuery("#product").val() == "") {
		jQuery("#productselectvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#productselectvalidation").html("");
	var quantity = parseInt(jQuery("#productQuantity").val());
	if(quantity == 0) {
		jQuery("#productQuantityvalidation").html("<strong>Quantity can not be zero</strong>");
		return;
	}
	jQuery("#productQuantityvalidation").html("");
	if(isNaN(quantity)) {
		jQuery("#productQuantityvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#productQuantityvalidation").html("");
	if(jQuery("#unitpriceinPackage").val() == "") {
		jQuery("#unitPriceproductvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#unitPriceproductvalidation").html("");
	

	var productName = jQuery("#product").val().split("_")[1];
	var productId = jQuery("#productId").val();
	var productCode = jQuery("#productCode").val();
	var productAmount = jQuery("#productAmount").val();
	var productQuantity = jQuery("#productQuantity").val();
	var priceinPackage = jQuery("#priceinPackage").val();
	var unitPrice = jQuery("#unitPrice").val();
	var unitPriceinPackage = jQuery("#unitpriceinPackage").val();
	var flag = checkDuplicateServiceProduct(productId);
	if(flag) {
		alert("Product Already Added Once");
		return;
	}
	var size = jQuery('#trainingList >tbody >tr').length + 1;
	jQuery("#trainingList tbody").append("<tr id='rec-"+size+"'><td><span class=\"sn\">"+size+"</span>.</td><td>"+productName+"</td><td>"+productId+"</td><td>"+productCode+"</td><td>"+productQuantity+"</td><td>"+unitPrice+"</td><td>"+productAmount+"</td><td>"+unitPriceinPackage+"</td><td>"+priceinPackage+"</td><td><a class=\"btn btn-xs delete-record\" data-id="+size+"><i class=\"fa fa-trash\"></i></a></td></tr>");
	
	calculateTotalPackagePrice();
	jQuery('#addproductmodal').modal('hide');
}

function appendRowForServiceInTable() {
	
	if(jQuery("#service").val() == "") {
		jQuery("#serviceselectvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#serviceselectvalidation").html("");
	var quantity = parseInt(jQuery("#serviceQuantity").val());
	if(quantity == 0) {
		jQuery("#serviceQuantityvalidation").html("<strong>Quantity can not be zero</strong>");
		return;
	}
	jQuery("#serviceQuantityvalidation").html("");
	if(isNaN(quantity)) {
		jQuery("#serviceQuantityvalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#serviceQuantityvalidation").html("");
	if(jQuery("#serviceUnitPriceinPackage").val() == "") {
		jQuery("#serviceUnitPriceinPackagevalidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#serviceUnitPriceinPackagevalidation").html("");
	
	var serviceName = jQuery("#service").val().split("_")[1];
	var serviceId = jQuery("#serviceId").val();
	var serviceCode = jQuery("#serviceCode").val();
	var serviceAmount = jQuery("#serviceAmount").val();
	var serviceQuantity = jQuery("#serviceQuantity").val();
	var servicePriceinPackage = jQuery("#servicePriceinPackage").val();
	var serviceUnitPrice = jQuery("#serviceUnitPrice").val();
	var serviceUnitPriceinPackage = jQuery("#serviceUnitPriceinPackage").val();
	var flag = checkDuplicateServiceProduct(serviceId);
	if(flag) {
		alert("Service Already Added Once");
		return;
	}
	var size = jQuery('#trainingList >tbody >tr').length + 1;
	jQuery("#trainingList tbody").append("<tr id='rec-"+size+"'><td><span class=\"sn\">"+size+"</span>.</td><td>"+serviceName+"</td><td>"+serviceId+"</td><td>"+serviceCode+"</td><td>"+serviceQuantity+"</td><td>"+serviceUnitPrice+"</td><td>"+serviceAmount+"</td><td>"+serviceUnitPriceinPackage+"</td><td>"+servicePriceinPackage+"</td><td><a class=\"btn btn-xs delete-record\" data-id="+size+"><i class=\"fa fa-trash\"></i></a></td></tr>");
	calculateTotalPackagePrice();
	jQuery('#addservicemodal').modal('hide');
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
		calculateTotalPackagePrice();
		return true;
	} else {
		return false;
	}
});

function calculateTotalPackagePrice() {
	var totalPrice = 0;
	jQuery('#trainingList > tbody > tr').each(function(index, tr) {
		var $row = jQuery(this).closest('tr'); //get the current row
		var packagePrice = $row.find('td').eq(8).text();
		totalPrice = totalPrice + parseFloat(packagePrice);
	});
	jQuery("#totalPackagePrice").val(totalPrice);
}

function checkDuplicateServiceProduct(selectedId) {
	var flag = false;
	jQuery('#trainingList > tbody > tr').each(function(index, tr) {
		var $row = jQuery(this).closest('tr'); //get the current row
		var productId = $row.find('td').eq(2).text();
		if(selectedId == productId) {
			flag = true;
			return false;
		}
	});
	return flag;
}

function savePackageData() {
	if(jQuery("#packageName").val() == "") {
		jQuery("#packagenameValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#packagenameValidation").html("");

	if(jQuery("#packageCode").val() == "") {
		jQuery("#packageCodeValidation").html("<strong>Please fill out this field</strong>");
		return;
	}
	jQuery("#packageCodeValidation").html("");
	
	var url = "/openmrs/ws/rest/v1/package/save-update";
	var token = jQuery("meta[name='_csrf']").attr("content");
	var header = jQuery("meta[name='_csrf_header']").attr("content");


	var packageDetails = [];

	jQuery('#trainingList > tbody > tr').each(function(index, tr) {
				var packageObject = {};
				var $row = jQuery(this).closest('tr'); //get the current row
				var productId = $row.find('td').eq(2).text();
				var quantity = $row.find('td').eq(4).text();
				var unitPriceInPackage = $row.find('td').eq(7).text();
				var packageItemPriceInPackage = $row.find('td').eq(8).text();
				packageObject["packageDetailsId"] = 0;
				packageObject["serviceProductId"] = parseInt(productId);
				packageObject["quantity"] = parseInt(quantity);
				packageObject["unitPriceInPackage"] = parseFloat(unitPriceInPackage);
				packageObject["packageItemPriceInPackage"] = parseFloat(packageItemPriceInPackage);
				packageDetails.push(packageObject);
			});
	if(packageDetails.length < 1) {
		jQuery("#validationFailedMessage").show();
		jQuery("#validationFailedMessage").html("<strong>* Please fill out the required fields</strong>");
		jQuery(window).scrollTop(0);
		 return;
	}
	jQuery("#validationFailedMessage").hide();
	var formData;

		formData = {
			"packageId" : 0,
			"clinicName" : "${psiClinicManagement.name }",
			"clinicCode" : "${psiClinicManagement.clinicId }",
			"clinicId": ${id},
			"packageName" : jQuery("#packageName").val(),
			"packageCode": jQuery("#packageCode").val(),
			"voided": false,
			"packagePrice": +jQuery("#totalPackagePrice").val(),
			"shnPackageDetails" : packageDetails
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
			   	if(data.isSuccess){					   
				   window.location.replace("/openmrs/module/PSI/package-list.form?id=${id}");
			   }
			   
			},
			error : function(e) {
			   
			},
			done : function(e) {				    
			    console.log("DONE");				    
			}
		}); 
	};
</script>


<%@ include file="/WEB-INF/template/footer.jsp"%>