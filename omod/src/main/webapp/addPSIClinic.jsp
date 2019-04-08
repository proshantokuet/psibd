<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/magicsuggest-min.css">
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/magicsuggest-min.js"></script>
<c:url var="saveUrl" value="/module/PSI/addPsiClinic.form" />
<%
String users = (String)session.getAttribute("users"); 
%>

<form:form method="POST" action="${saveUrl}" modelAttribute="pSIClinic">


<div class="container register-form">
	<div class="form">
    	<div class="note">
        	<p>Add Community Clinic.</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="form-group">
                		Clinic Name : <form:input path="name" class="form-control" required="required"/>
                  	</div>
                  	<div class="form-group">
                  		Clinic ID:  <form:input path="clinicId" class="form-control" required="required"/>
                   	</div>
             	</div>
              	<div class="col-md-6">
               		<div class="form-group">
                  	Category: 	<form:select path="category" class="form-control selcls" required="required">
                  				<form:option value=""/>
					              <form:option value="BEmOC"/>
					              <form:option value="CEmOC"/>
					              <form:option value="Vital"/>					             
					         </form:select>
					</div>
                  	<div class="form-group">
                   	Address: 	<form:input path="address" class="form-control" required="required"/>                	
                   	
                  	</div>
              	</div>
              	<div class="col-md-6">               		
                  	<div class="form-group">
                   	DHIS2 ID: <form:input path="dhisId" class="form-control" required="required"/>
                  	</div>
                  
              	</div>
              	
              	<div class="col-md-6"> 
                  	<div class="form-group">
                  		<div id="cm" class="ui-widget">
							Assign User :
							<div id="userIds"></div>							
						</div>
                  	</div>
                  	
              	</div>
          	</div>
          	<button type="submit" class="btnSubmit">Submit</button>
      	</div>
   	</div>
</div>       
	

</form:form>

<script type="text/javascript">
  
	var $jq = jQuery.noConflict();
	 $jq('#userIds').magicSuggest({ 
		 	required: true,
			//placeholder: 'Type Locations',
     		data: <%=users%>,
	        valueField: 'username',
	        displayField: 'display',
	        name: 'usernames',
	        inputCfg: {"class":"magicInput"},
	        //value: [255],
	        useCommaKey: true,
	        allowFreeEntries: false,
	        maxSelection: 20,
	        maxEntryLength: 500,
	 		maxEntryRenderer: function(v) {
	 			return '<div style="color:red">Typed Word TOO LONG </div>';
	 		}
	       
	  });
  </script>

<%@ include file="/WEB-INF/template/footer.jsp"%>