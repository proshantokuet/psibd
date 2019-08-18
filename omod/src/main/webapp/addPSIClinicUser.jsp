<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<%@ include file="template/localHeader.jsp"%>

<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/magicsuggest-min.css">
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<!-- <script type="text/javascript" src="/openmrs/moduleResources/PSI/js/magicsuggest-min.js"></script> -->
<c:url var="saveUrl" value="/module/PSI/addPSIClinicUser.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicList.form" />

<openmrs:require privilege="Add Clinic User" otherwise="/login.htm" />
<style>
.margin-top{
margin-top: 4px;
}
.row {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    /* margin-right: -15px; */
    /* margin-left: -15px; */
}
</style>
<%
String users = (String)session.getAttribute("users");
String userIds = (String)session.getAttribute("userIds");
//String message = (String)session.getAttribute("message");
%>
<form:form method="POST" id="userForm" action="${saveUrl}" modelAttribute="pSIClinicUser">


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Create User</p>
       	</div>

  		<div class="form-content">
        	<div class="row">
        	<input type="hidden" id="clinicId" name="clinicId" value="${psiClinicManagementId}">
        	<input type="hidden" id="cuid" name="cuid" value="0">
            	<table>
	            	<tr>
	            		<td>First Name<span class="required">*</span></td>
	            		<td><input type="text" name="firstName" id="firstName" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	<tr>
	            		<td>Last Name<span class="required">*</span></td>
	            		<td><input type="text" name="lastName" id="lastName" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
					<td>Gender<span class="required">*</span></td>
						<td>
						
							<input type="radio" name="gender" id="M" value="M">
								<label for="M"> Male </label>
						
							<input type="radio" name="gender" id="F" value="F">
								<label for="F"> Female </label>
						</td>
					</tr>
					
					<tr>
	            		<td>Email<span class="required">*</span></td>
	            		<td><input type="email" name="email" id="email" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Mobile<span class="required">*</span></td>
	            		<td><input type="text" name="mobile" id="mobile" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>	            	
	            	
	            	
	            	<tr>
	            		<td>UserName<span class="required">*</span></td>
	            		<td><input type="text" name="userName" id="userName" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Password<span class="required">*</span></td>
	            		<td><input type="password" name="password" id="password" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Retype Password<span class="required">*</span></td>
	            		<td><input type="password" name="reTypePassword" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
					<tr>
						<td valign="top">Roles</td>
						<td valign="top">							
						<div id="roleStrings" class="listItemBox">
							<c:forEach items="${roles}" var="role">	
							<span class="listItem"><input type="checkbox" name="roleStrings" class="roles" id="roleStrings.${role.role }" value="${role.role }">
							<label for="roleStrings.${role.role }">${role.role }</label></span>
							</c:forEach>
						</div>
						</td>
					</tr>
					
            	</table>
          	</div>
          	
          	<button type="submit" class="btnSubmit">Submit</button> <a href="${cancelUrl}">Back</a>
      	</div>
   	</div>
</div>       
	

</form:form>

<%-- <script type="text/javascript">  
	var $jq = jQuery.noConflict();
	 $jq('#userIds').magicSuggest({ 		 	
		 	allowFreeEntries: false,
		 	required: true,
			//placeholder: 'Type Locations',
     		data: <%=users%>,
	        valueField: 'username',
	        displayField: 'display',
	        name: 'usernames',
	        inputCfg: {"class":"magicInput"},
	        value: <%=userIds%>,
	        useCommaKey: true,	        
	        maxSelection: 1,	        
	        maxEntryLength: 100,
	 		maxEntryRenderer: function(v) {
	 			return '<div style="color:red">Typed Word TOO LONG </div>';
	 		}	       
	  });
  </script> --%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/user.js"></script>
<%@ include file="/WEB-INF/template/footer.jsp"%>