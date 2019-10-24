<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery-1.10.2.js"></script>
<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="Edit Clinic Type" otherwise="/login.htm" />

<div class="container register-form" style="max-width: 100%;padding: 0px; margin: 0px;">
    <div class="form">
        <div class="note">          
            <p>Edit Clinic Type</p>
            
        </div>
        <span class="text-red" id="usernameUniqueErrorMessage"></span>
         <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
            <img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>"></div>
                            
        </div>
        <form:form id="addClinicTypeForm" method="POST" action="/openmrs/module/PSI/editClinicType.form" modelAttribute="clinicType">
        
        <div class="form-content">
            <div class="row">   
                <div class="col-md-6">
                    
                    <div class="form-group">
                    Clinic Type Name
                        <form:input style="height: 39px;" path="clinicTypeName" id="clinicTypeName" class="form-control" required="required" min="0"/>
                        <form:input type="hidden" path="ctid" id="ctid" name="ctid"/>
                    </div>              
                    
                </div>
                
              
            </div>
            <button type="submit" class="btnSubmit">Submit</button> 
            <a href="${cancelUrl}">Back</a> 
        </div>
    </div>
</div>       
    

</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>