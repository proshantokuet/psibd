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
  
  <form:form id="ServicePointWise">
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
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
  </form:form>
    <table id="table_id" class="display">
	    
	</table>
  </div>
  <div id="tabs-2">
    <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
  </div>
  
</div>
 
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript">

var $jq = jQuery.noConflict();

var $jQuery = jQuery.noConflict();
$jQuery( function() {
	$jQuery( "#tabs" ).tabs();
  } );
  
/* $jq(document).ready( function () {
	$jq("#ServicePointWise").submit(function(event) {
		event.preventDefault();
		var url = "/openmrs/ws/rest/v1/money-receipt/f";
		$jq('#table_id').DataTable({
			'sAjaxSource': url,
			'aoColumns': [
			              { 'mData': 'static' },
			              { 'mData': 'category' }
			               
			          ]
		});
	
	}); 
} ); */
$jq( function() {
	$jq("#startDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#endDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
/* $jq('#startDate').attr('max', maxDate);
$jq('#endDate').attr('max', maxDate); */

</script>

<script type="text/javascript">
var $JQuery = jQuery.noConflict();
$JQuery("#ServicePointWise").submit(function(event) { 
var url = "/openmrs/module/PSI/ServicePointWise.form";
alert ($JQuery('input[name=endDate]').val());
event.preventDefault();
$JQuery.ajax({
	   type : "GET",
	   contentType : "application/json",
	   url : url,	 
	   dataType : 'html',
	   timeout : 100000,
	   beforeSend: function() {	    
	   
	   },
	   success : function(data) {	   
		   $JQuery("#table_id").append("");
		   $JQuery("#table_id").append(data);
		   $JQuery('#table_id').DataTable();
	   },
	   error : function(e) {
	    console.log("ERROR: ", e);
	    display(e);
	   },
	   done : function(e) {	    
	    console.log("DONE");
	    //enableSearchButton(true);
	   }
	  }); 
});   
	  
		
</script>
 



<%@ include file="/WEB-INF/template/footer.jsp"%>