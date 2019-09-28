<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/magicsuggest-min.css">
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/magicsuggest-min.js"></script>
<c:url var="saveUrl" value="/module/PSI/addPSIClinicUser.form" />
<c:url var="cancelUrl" value="/module/PSI/PSIClinicUserList.form?id=${psiClinicManagementId }" />
<%
String users = (String)session.getAttribute("users");
String userIds = (String)session.getAttribute("userIds");
//String message = (String)session.getAttribute("message");
%>

<%@page import="org.openmrs.module.PSI.utils.HtmlUtils"%>
<style>
.margin-top{
margin-top: 4px;
}
.form-content {
    padding: 2%;
    border: 1px solid #ced4da;
    margin-bottom: 1%;
}
.row {
    display: -ms-flexbox;
    display: flex;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    /* margin-right: -15px; */
    /* margin-left: -15px; */
}
.listItemBox {
    width: 1024px;
    padding: 2px;
    border: 1px solid lightgray;
    float: left;
    background-color: #EFEFEF;
}
.listItem {
    padding: 2px;
    float: left;
    margin: 2px;
    width: 250px;
    font-size: .9em;
    border: 1px solid lightgrey;
    background-color: white;
}
input[type="text"], input[type="password"] {
    
    border: 1px solid #c1c5c5;
    padding: 4px;
}
</style>
<openmrs:require privilege="Edit Clinic User" otherwise="/login.htm" />

<%-- <a href="${pageContext.request.contextPath}/module/PSI/PSIClinicList.form"><spring:message
				code="PSI.psiclinic" /></a>Edit Assinged User --%>
<form:form method="POST" id="editUserForm" action="${saveUrl}" modelAttribute="pSIClinicUser">


<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
	<div class="form">
    	<div class="note">
        	<p>Edit User</p>
       	</div>
       	 <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
							
		</div>
		<div style="clear: both;"></div>
		 <div id="mesage" style="color: red; font-weight: bold;display: none;position: absolute; z-index: 1000;margin-left:13%"> 
							
		</div>
		<div style="clear: both;"></div>

  		<div class="form-content">
        	<div class="row">
        		<input type="hidden" id="clinicId" name="clinicId" value="${psiClinicManagementId}">
        		<form:hidden path="cuid" />
        		
        		<input type="hidden" id="personId" name="personId" value="${user.personId }">
        		<input type="hidden" id="userId" name="userId" value="${user.userId }">
            	<table>
	            	<tr>
	            		<td>First Name<span class="required">*</span></td>
	            		<td><input type="text" value="${user.firstName }" name="firstName" id="firstName" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	<tr>
	            		<td>Last Name<span class="required">*</span></td>
	            		<td><input type="text" value="${user.lastName }" name="lastName" id="lastName" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	<c:set var="gender" value="${user.gender }"/>	
	            	<%
	            	String gender = (String)pageContext.getAttribute("gender");
	            	String m = HtmlUtils.genderChecked("M", gender);
	            	String f = HtmlUtils.genderChecked("F", gender);
	            	String other = HtmlUtils.genderChecked("O", gender);
	            	%>
	            	<tr>
					<td>Gender<span class="required">*</span></td>
						<td>
						
							<input type="radio" name="gender" id="M" value="M" <%=m %>>
								<label for="M"> Male </label>
						
							<input type="radio" name="gender" id="F" value="F" <%=f %>>
								<label for="F"> Female </label>
								<input type="radio" name="gender" id="O" value="O" <%=other %>>
								<label for="F"> Other </label>
						</td>
					</tr>
					
					<tr>
	            		<td>Email<span class="required">*</span></td>
	            		<td><input value="${user.email }" type="email" name="email" id="email" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Mobile<span class="required">*</span></td>
	            		<td><input value="${user.mobile }" type="text" name="mobile" id="mobile" class="form-control margin-top" required="required" autocomplete="off" pattern="01[3-9]{1}[0-9]{8}"></td>
	            	</tr>	            	
	            	
	            	
	            	<tr>
	            		<td>UserName<span class="required">*</span></td>
	            		<td><input value="${user.userName }" type="text" name="userName" id="userName" class="form-control margin-top" required="required" autocomplete="off" readonly></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Password<span class="required">*</span> </td>
	            		<td><input value="${defaultPassword }" type="password" name="password" id="password" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
	            	
	            	<tr>
	            		<td>Retype Password<span class="required">*</span> </td>
	            		<td><input value="${defaultPassword }" type="password" name="reTypePassword" id="reTypePassword" class="form-control margin-top" required="required" autocomplete="off"></td>
	            	</tr>
					<tr>
						<td valign="top">Roles <span class="required">*</span> </td>
						<td valign="top">
						 <c:set var="roleString" value="${user.role }"/>	
						 
						 
						<div id="roleStrings" class="listItemBox">
							<c:forEach items="${roles}" var="role">	
							<c:set var="roleName" value="${role.role }"/>							
							<% 
							String roleStrings = (String)pageContext.getAttribute("roleString");
							String roleName = (String)pageContext.getAttribute("roleName");
							if(HtmlUtils.roleChecked(roleStrings, roleName)){ %>
							<span class="listItem">
								<input type="checkbox" name="roleStrings" class="roles" id="roleStrings.${role.role }" value="${role.role }" checked>
							<label for="roleStrings.${role.role }">${role.role }</label></span>
							<%} else { %>
								<span class="listItem">
								<input type="checkbox" name="roleStrings" class="roles" id="roleStrings.${role.role }" value="${role.role }">
								<label for="roleStrings.${role.role }">${role.role }</label></span>
							<% } %>				
							
							</c:forEach>
						</div>
						</td>
					</tr>
					<c:if test="${!user.retired}">
					<tr>
	            		<td>Disable Account</td>
	            		<td>  <input type="radio" name="disableAccount" id="yesDisable" value="yes"> Yes
  							  <input type="radio" name="disableAccount" id="noDisable" value="no"> No
  						</td>
	            	</tr>
	            	</c:if>
	            	<c:if test="${user.retired}">
					<tr>
						<td>Enable Account</td>
						<td><input type="radio" name="enableAccount" id="enableAccount"
							value="yes"> Yes </td>
					</tr>
					</c:if>

					<tr>
	            		<td><span id="deactivatereasonLabel" class="required">Reason *</span></td>
	            		<td><input type="text" name="deactivatereasonName" id="deactivatereason" class="form-control margin-top" autocomplete="off"></td>
	            	</tr>	
					
            	</table>
          	</div>
          	<div class="row" style="margin-left: 558px; margin-top: 5px;">
          	
          	<input type="submit" class="btnSubmit" onclick="return Validate()" value="Submit"/> <a href="${cancelUrl}">Back</a>
          	
          	</div>
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
	        required: true,
	        maxEntryLength: 100,
	 		maxEntryRenderer: function(v) {
	 			return '<div style="color:red">Typed Word TOO LONG </div>';
	 		}	       
	  });
  </script> --%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/user.js"></script>
<%@ include file="/WEB-INF/template/footer.jsp"%>