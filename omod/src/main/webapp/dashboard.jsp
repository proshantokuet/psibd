<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Clinic List" otherwise="/login.htm" />


<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Service Point Wise Revenue Report</a></li>
    <li><a href="#tabs-2">Service Provider Wise Revenue Report</a></li>   
  </ul>
  <div id="tabs-1">
  
  <form:form id="ServicePointWise" method="POST" action="${saveUrl}" modelAttribute="pSIServiceManagement">
  <div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">                							
						Service Code
						<input id="startDate"  name="startDate" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	<label for="Service Code">Item Name</label>
						<input id="endDate" name="endDate" type="date"  required="true"/>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>  <a href="${cancelUrl}">Back</a>
      	</div>
  </form:form>
    <table id="table_id" class="display">
	    <thead>
	        <tr>
	            <th>#Id</th>
	            <th>Clinic Name</th>
	            <th>Clinic ID</th>
	            <th>Category</th>
	            <th>Address</th>
	            <th>Action</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<%-- <c:forEach var="clinic" items="${ pSIClinics }">
	        <tr>
	        	<td>${ clinic.cid }</td>
	            <td>${ clinic.name }</td>
	            <td>${ clinic.clinicId }</td>
	            <td>${ clinic.category }</td>
	            <td>${ clinic.address }</td>
	            <td> <a class="btn btn-primary" href="<c:url value="/module/PSI/uploadPSIClinicService.form?id=${clinic.cid}"/>"> Upload Services</a> <a class="btn btn-primary" href="<c:url value="/module/PSI/PSIClinicUserList.form?id=${clinic.cid}"/>"> User List</a>  <a class="btn btn-primary" href="<c:url value="/module/PSI/editPSIClinic.form?id=${ clinic.cid }"/>"> Edit</a> </td>
	        </tr>
	       </c:forEach> --%>
	        
	    </tbody>
	</table>
  </div>
  <div id="tabs-2">
    <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
  </div>
  
</div>
 
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();
$jq(document).ready( function () {
	$jq('#table_id').DataTable();
} );
var $jQuery = jQuery.noConflict();
$jQuery( function() {
	$jQuery( "#tabs" ).tabs();
  } );
  

$jq( function() {
	$jq("#startDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
/* $jq('#startDate').attr('max', maxDate);
$jq('#endDate').attr('max', maxDate); */

</script>

<script type="text/javascript">


var url = "/openmrs/ws/rest/v1/service-management/save";
/* $.ajax({
	   type : "GET",
	   contentType : "application/json",
	   url : url,
	 
	   dataType : 'html',
	   timeout : 100000,
	   beforeSend: function() {
	    
	   
	   },
	   success : function(data) {
	   
	    $("#"+id).html(data);
	   },
	   error : function(e) {
	    console.log("ERROR: ", e);
	    display(e);
	   },
	   done : function(e) {
	    
	    console.log("DONE");
	    //enableSearchButton(true);
	   }
	  }); */
	  
	  var $jq = jQuery.noConflict();
	  $jq("#ServicePointWise").submit(function(event) { 
		  $jq("#loading").show();
			var url = "/openmrs/ws/rest/v1/service-management/save";			
			var token = $jq("meta[name='_csrf']").attr("content");
			var header = $jq("meta[name='_csrf_header']").attr("content");			
			
			
			var formData;			
				formData = {
			            'startDate': $jq('input[name=startDate]').val(),			           
			            'endDate': $jq('input[name=endDate]').val()			           
			        };			
			
			event.preventDefault();			
			$jq.ajax({
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
					$jq("#usernameUniqueErrorMessage").html(data);
					$jq("#loading").hide();
				   if(data == ""){					   
					   window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form");
					   
				   }
				   
				},
				error : function(e) {
				   
				},
				done : function(e) {				    
				    console.log("DONE");				    
				}
			}); 
		});
		
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>