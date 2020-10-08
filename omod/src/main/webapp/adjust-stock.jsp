<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Clinic Service List" otherwise="/login.htm" />
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>

<c:url var="cancelUrl" value="/module/PSI/product-list.form?id=${id}" /> 


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">    	    
        	<p>Adjust Stock of ${psiClinicManagement.name } (${psiClinicManagement.clinicId })</p>
        	
       	</div>
		<span class="text-red" id="usernameUniqueErrorMessage"></span>
		 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
				<div style="display: none;" class="alert alert-success" id="serverResponseMessage" role="alert"></div>
				<div style="display: none;" class="alert alert-danger" id="validationFailedMessage" role="alert"></div>
					
				<div class="form-content">	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>1. Product Name:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${product.name }</label>  
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label >2. Brand Name:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${product.brandName }</label>  
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label >3. Product ID:</label> 
					</div>
					<div class="col-lg-2 form-group">
						<label for="receiveDate">${product.sid }</label>  
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>4. Current Stock:</label> 
					</div>
					<div class="col-lg-3 form-group">
					    <input type="text" style="height: 39px;" class="form-control" name="currentStock" id ="currentStock" value="${product.stock }"  readonly/>
<!-- 						<label for="receiveDate"></label>   -->
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>5. Adjustment</label><span class="text-danger"> *</span> 
					</div>
					<div class="col-lg-3 form-group">
						<input type="number" min="1" step="1" oninput="this.value = Math.abs(this.value)" style="height: 39px;" class="form-control" id="changedStock" >
						<span class="text-danger" id="numberValidation"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>6. After Adjstment</label> 
					</div>
					<div class="col-lg-3 form-group">
						<input type="number"  style="height: 39px;" class="form-control" id="diference" readonly >
					</div>
				</div>	
				<div class="row">
					<div class="col-lg-2 form-group">
						<label>7. Reason</label><span class="text-danger"> *</span> 
					</div>
					<div class="col-lg-3 form-group">
						<select class="form-control" id="reason" name="reason">
										<option value="">Select Reason</option>
										<option value="Date Expired">Date Expired</option>
										<option value="Damaged">Damaged</option>
										<option value="Transferred">Transferred</option>
										<option value="Lost">Lost</option>
										<option value="Others">Others</option>
								</select>
					</div>
				</div>
				<div class="row" id="othersDiv" style="display:none;">
					<div class="col-lg-2 form-group">
						<label>8. Please Specify</label><span class="text-danger"> *</span> 
					</div>
					<div class="col-lg-3 form-group">
						<input type="text"  style="height: 39px;" class="form-control" id="others">
						<span id="othervalidation" class="text-danger"></span>
					</div>
				</div>				
				<div class="text-center">
	                <button type="submit" onclick="saveAdjustStockData()" class="btn btn-primary" value="confirm">Confirm</button>&nbsp;<a href="${cancelUrl}">Back</a>
	            </div>
	           </div>
   	</div>     

<script>
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
	
	jQuery("#reason").change(function (event) {
		let reasonValue = jQuery('#reason').val();
		if(reasonValue == "Others") {
			jQuery('#othersDiv').show();
		}
		else {
			jQuery('#othersDiv').hide();
		}

	});
	
	jQuery('.form-content').delegate('#changedStock', 'input propertychange', function (event) {
		var thisStock = +jQuery(this).val();
		var currntStock = parseInt("${product.stock }");
		if(thisStock > currntStock) {
			jQuery("#numberValidation").html("Changed stock can not be greater than current stock");
			jQuery("#diference").val('');
			return;
		}
		jQuery("#numberValidation").html("");
		var Difference = currntStock - thisStock;
		jQuery("#diference").val(Difference);
		
	});
	
	function Validate() {
		if(jQuery('#reason').val() == "Others") {
			var reasonValue = jQuery('#others').val();
			if(reasonValue == "") {
				jQuery("#othervalidation").html("<strong>Please fill out this field</strong>");
			     return false;
			}
		}
		var currentStock = +jQuery("#currentStock").val();
		var changedStock = +jQuery("#changedStock").val();
		if(changedStock > currentStock) {
			jQuery("#numberValidation").html("Changed stock can not be grater than current stock");
			return false;
		}
		jQuery("#othervalidation").html("");
		jQuery("#numberValidation").html("");
	}
</script>


<%@ include file="/WEB-INF/template/footer.jsp"%>