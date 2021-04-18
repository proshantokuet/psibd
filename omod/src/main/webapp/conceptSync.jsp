<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />
<style>

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
#tabs{
	display:none;
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
    border: 1px solid #1aad96;
    /* background: #1aac9b url(images/ui-bg_inset-soft_30_ffffff_1x100.png) 50% 50% repeat-x; */
    font-weight: bold;
    background: #4aad9b;
    color: #0e5c52;
}
</style>
<div id="loader"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Concept Sync</a></li>
    <li><a href="#tabs-2">Drug Sync</a></li>
  </ul>
  <div id="tabs-1">
    <form:form id="conceptSync">
  	<div class="form-content">
  	<div id="loading_prov" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
  	<div class="alert alert-info" style="display:none;" id="conceptAlert" role="alert"></div>
        	<div class="row">
              	<div class="col-md-12">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button  type="submit" class="btnSubmit">Sync Concept</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  
	</div>
	
	<div id="tabs-2">
    <form:form id="drugsync">
  	<div class="form-content">
  	<div id="loading_prov_money_receipt" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
  	<div class="alert alert-info" style="display: none;" id="drugAlert" role="alert"></div>
        	<div class="row">
              	<div class="col-md-12">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button  type="submit" class="btnSubmit">Sync Drug</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
   
  	

	</div>
 </div>
 <script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<!-- <script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/pdfmake.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.print.min.js"></script>
<script defer type="text/javascript" src="/openmrs/moduleResources/PSI/js/vfs_fonts.js"></script> -->

<script type="text/javascript">
var $jq = jQuery.noConflict();
$jq(window).load(function() {
	$jq("#loader").hide();
	$jq( "#tabs" ).tabs();
	$jq("#tabs").show();
});


$jq("#conceptSync").on("submit",function(event){
	var token = $jq("meta[name='_csrf']").attr("content");
	var header = $jq("meta[name='_csrf_header']").attr("content");
	event.preventDefault();
	$jq("#loading_prov").show();
	var url = "/openmrs/ws/rest/v1/sync/save-concept";
	$jq.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	   $jq("#conceptAlert").show();
			   $jq("#conceptAlert").html(data);
			   $jq("#loading_prov").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov").hide();
	    }
	    
	});
});

$jq("#drugsync").on("submit",function(event){
	var token = $jq("meta[name='_csrf']").attr("content");
	var header = $jq("meta[name='_csrf_header']").attr("content");
	event.preventDefault();
	$jq("#loading_prov_money_receipt").show();
	var url = "/openmrs/ws/rest/v1/sync/save-drug";
	$jq.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	   $jq("#drugAlert").show();
			   $jq("#drugAlert").html(data);
			   $jq("#loading_prov_money_receipt").hide();
	    },
	    error:function(data){
	    	$jq("#loading_prov_money_receipt").hide();
	    }
	    
	});
});

</script>