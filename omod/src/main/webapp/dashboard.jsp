<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:require privilege="dashboard" otherwise="/login.htm" />

<%-- <div class="form-content">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                  	
             </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.newPatient }</label>  &nbsp;&nbsp; New Registration                 			
				</div>                  	
              </div>              	
       </div>          	
</div> --%>
     
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Comprehensive Service Report</a></li>
    <li><a href="#tabs-2">Service Provider Wise Report</a></li>
    <li><a href="#tabs-3">Money Receipt Report</a></li> 
    <li><a href="#tabs-4">Draft Tracking Report</a></li>
    <!-- <li><a href="#tabs-5">Comprehensive Service Report</a></li> -->
    <li><a href="#tabs-5">Registration Report</a>
    <li><a href="#tabs-6">Visit Report</a>
  </ul>
  <div id="tabs-1">
  <input id="clinicName" type="hidden" value="${clinic_name}">
  <form:form id="ServicePointWise">
  	<div class="form-content">
        	<div class="row">
            	<div class="col-md-3">
                	<div class="form-group">                							
						<label for="Service Code"> From</label><br />
						<input id="startDate"  name="startDate" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">To</label><br />
						<input id="endDate" name="endDate" type="text"  required="true"/>
						<%-- <input id="clnic" type="hidden" value="${clinic}">    --%>                			
					</div>
                  	
              	</div>
              	  <c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_comp" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if> 
              	<%--<c:if test="${showServiceCategory eq 1 }">
              		<div class="col-md-3">
              			<div class="form-group">
              				<label>Service Category</label><br/>
              				<select name="service_category" id="service_category" class="form-control selcls">
              					<c:forEach items="${serviceCategory}" var="scat">
              						<option value="${scat}">${scat}</option>
              					</c:forEach>
              				</select>
              			</div>
              		</div>
              	</c:if>  --%>
              	<div class="col-md-3">
              		<div class="form-group">
                  		Service Category: 
                  			<select class="form-control selcls" id="cat">
                  				<option value="" selected>Select Category</option>
					               <c:forEach items="${services}" var="service"> 
					              	 <option value="${service.categoryName}" label="${service.categoryName}"/>					              
					              </c:forEach>				             

					         </select>	
                   	</div>
              	</div>
              	<div class="col-md-3">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  		<button style="width: 120px ;margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
  
    <div id="servicePoints">
    	<div class="form-content" id="servicePointWiseReport">	</div> 
    	<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label for="Service Code">${ comp_dashboard.newRegistration }</label>
                  	  	&nbsp;&nbsp; New Registration
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${ comp_dashboard.oldClients } </label> &nbsp;&nbsp; 
                  		Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${ comp_dashboard.newClients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label> ${ comp_dashboard.totalServiceContact } </label> &nbsp;&nbsp; Total Service Contact
	                </div>
				</div>
			</div>
			<div class="row">
			
 				<div class="col-md-3">
                	<div class="form-group">                							
						<label> ${ comp_dashboard.patientServed } </label>  &nbsp;&nbsp; Patients Served						
                   </div>
                  	
             	</div>
             	<div class="col-md-3">
               	<div class="form-group">
                  	<label for="Service Code">${ comp_dashboard.revenueEarned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ comp_dashboard.totalDiscount } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div>
             	
 			
			</div>
		</div>

	<div id="loading_comp_" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
 	<div style="overflow:auto;">
		<br/>
		 <table id="servicePoint" class="display" border="1">
			 	<thead>
					<tr>
						<th rowspan="2">Sl</th>
						<th rowspan="2">Service Code</th>
						<th rowspan="2">Service Name</th>
						<th colspan="4">Service Contact</th>
						<th colspan="4">Revenue</th>
						<th colspan="4">Discount</th>
					</tr>
					<tr>
						<% for(int i = 0; i < 3; i++) {%>
					 	<th>Static</th>
					 	<th>CSP</th>
					 	<th>Satellite</th>
					 	<th>Total</th>
					 	<% } %>
					</tr>
				</thead>
			    <tbody>
			    	<% int slc = 0; %>
			    	 <c:forEach var="report" items="${ compReports }">
			        <tr>
			        	<td><%=++slc%></td>
			            <td>${ report.service_code }</td>
			        	<td>${ report.service_name }</td>
			            <td>${ report.service_contact_static }</td>
			            <td>${ report.service_contact_satellite }</td>
			            <td>${ report.service_contact_csp }</td>
			            <td>${ report.service_total }</td>
			            <td>${ report.revenue_static }</td>
			            <td>${ report.revenue_satellite }</td>
			            <td>${ report.revenue_csp }</td>
			            <td>${ report.revenue_total }</td>
			            <td>${ report.discount_static }</td>
			            <td>${ report.discount_satellite }</td>
			            <td>${ report.discount_csp }</td>
			            <td>${ report.discount_total }</td>
			            
			        </tr>
			       </c:forEach>
			        
			    </tbody>
		
		</table>
	</div>
	    
	
  </div>
  </div>
  <div id="tabs-2">
    <form:form id="ServiceProviderWise">
  	<div class="form-content">
  	
        	<div class="row">
            	<div class="col-md-2">
                	<div class="form-group">                							
						<label for="Service Code">From</label><br />
						<input style="width: 160px" id="from"  name="from" type="text"  required="true"/>
                  	</div>
                  	
             	</div>
              	<div class="col-md-2">
               		<div class="form-group">
                  	<label for="Service Code">To</label> <br/>
						<input style="width: 160px" id="to" name="to" type="text"  required="true"/>                  			
					</div>
                  	
              	</div>
              	<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinics" id="clinics" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
              	<div class="col-md-3">
               		<div class="form-group">
                  	<label for="Service Code">Data Collector</label><br />
						<select id="provider" required="true"  class="form-control selcls">
						  <option value=""></option>
						  <c:forEach var="user" items="${ psiClinicUsers }">
							  <option value="${user.username }">${user.userRole }</option>							  
						  </c:forEach>
						</select>                 			
					</div>
                  	
              	</div>
              	<div class="col-md-2">
               		<div class="form-group">
               		<label for="Service Code"></label><br />
                  	<button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>                  			
					</div>
                  	
              	</div>
              	
          	</div>
          	
     </div>
  </form:form>
 
    <div id="serviceProviders">
    
     
    <div class="form-content" id="serviceProviderReports"></div>
	<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label for="Service Code">${dashboard.newPatient }</label>
                  	  	&nbsp;&nbsp; New Registration
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; 
                  		Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label> ${ dashboard_service_cotact_value } </label> &nbsp;&nbsp; Total Service Contact
	                </div>
				</div>
			</div>
			<div class="row">
			
 				<div class="col-md-3">
                	<div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                   </div>
                  	
             	</div>
             	<div class="col-md-3">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ dashbaord_discount_value } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div>
             	
 			
			</div>
		</div>
 
 
 
  	<div id="loading_prov" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	<table id="serviceProviderDefault" class="display">
		  <thead>
		        <tr>
		            <th>Category</th>	
		            <th>Code</th>            
		            <th>Item</th>
		            <th>Number of Service</th>	            
		            <th>Total Revenue</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach var="report" items="${ providerWiseReports }">
		        <tr>
		            <td>${ report.category }</td>	            
		        	<td>${ report.code }</td>
		        	<td>${ report.item }</td>
		            <td>${ report.serviceCount }</td>
		            <td>${ report.total }</td>
		        </tr>
		       </c:forEach>
		        
		    </tbody>
	</table>
	</div>
  </div>
  <div id="tabs-3">

    <form:form id="slipTracking_">

        <div class="form-content">

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="startDateSlip"> From</label>
                        <br />
                        <input class="dt" id="startDateSlip" name="startDateSlip" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="endDateSlip">To</label>
                        <br />
                        <input class="dt" id="endDateSlip" name="endDateSlip" type="text" required="true" />
                    </div>

                </div>
                <c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_slip" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="collector">Select Data Collector</label>
                        <br />
                        <select id="collector" name="collector" class="form-control selcls">
                            <option value=""></option>
                            <c:forEach var="user" items="${ psiClinicUsers }">
                                <option value="${user.username }">${user.userRole }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="form-group">
                        Wealth Classification
                        <br />
                        <input type="checkbox" id="wlth_poor" name="wlthPoor" value="">Poor
                        <br>
                        <input type="checkbox" id="wlth_pop" name="wlthPop" value="">Pop
                        <br>
                        <input type="checkbox" id="wlth_pay" name="wlthAbleToPay" value=""> Able to pay
                        <br>
                        <br>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        Service Point
                        <br />
                        <input type="checkbox" id="sp_satelite" name="spSatelite" value="">Satelite
                        <br>
                        <input type="checkbox" id="sp_static" name="spStatic" value="">Static
                        <br>
                        <input type="checkbox" id="sp_csp" name="spCsp" value="">CSP
                        <br>
                        <br>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <!-- 	<label for="Service Code"></label><br /> -->
                        <button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>
                    </div>

                </div>
            </div>
        </div>
        
    </form:form>
    <div id="loading" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
							
    
     <div id="slipTrackers">
     	<div class="form-content" id="slipTracking"> </div>
	    <div class="form-content">
	        <div class="row">
	            <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${dashboard.servedPatient } </label> &nbsp;&nbsp; Patients Served
	                </div>
	
	            </div>
	            <div class="col-md-3">
	                <div class="form-group">
	                    <label for="Service Code">${dashboard.earned }</label>
	                    &nbsp;&nbsp; Revenue Earned
	                </div>
	
	            </div>
	            <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ dashbaord_discount_value } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div>
	            <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ dashboard_service_cotact_value } </label> &nbsp;&nbsp; Total Service Contact
	                </div>
	            </div>
	        </div>
	
	    </div>
	    <div style="overflow:auto;">
		<br/>
		    <table id="slip_tracking" class="display">
	         <thead>
	            <tr>
	                 <th>SL</th> 
	                 <th>Slip No.</th>
	                 <th>Money Receipt Date</th>
	                 <th>Patient Name</th>
	                <th>Phone</th>
	                <th>Wealth Class</th>
	                <th>Service Point</th>
	                <th>Total Amount</th>
	                <th>Discount</th>
	                <th>Payable Amount</th>
	              <!--   <th>Action</th> --> 
	            </tr>
	        </thead>
	        <tbody>
	        	<c:if test="${not empty slipReport }">
					<c:forEach var="report" items="${ slipReport }">
				        <tr>
		        	 	    <td>#</td>
		                    <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.slip_no }</a></td>	             
			            	 <td>${ report.slip_date }</td> 
				        	 <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.patient_name }</a></td>
				            <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.phone }</a></td>
				            <td>${ report.wealth_classification }</td>
				            <td>${ report.service_point }</td>
				            <td>${ report.total_amount }</td>
				            <td>${ report.discount }</td>
				            <td>${ report.net_payable }</td>
				     <%--        <td>${ report.slip_link }</td> --%>  
				             
				        
				        </tr>
			       </c:forEach>
			    </c:if>
	        </tbody>
	    </table>
    </div>
    </div>
    
 </div>
 <div id="tabs-4">
 	<form:form id="draftTracking_">
 		  <div class="form-content">

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="startDateDraft"> From</label>
                        <br />
                        <input class="dt" id="startDateDraft" name="startDateDraft" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="endDateDraft">To</label>
                        <br />
                        <input class="dt" id="endDateDraft" name="endDateDraft" type="text" required="true" />
                    </div>

                </div>
                <c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_draft" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="collector_draft"> Data Collector</label>
                        <br />
                        <select id="collector_draft" name="collector_draft" class="form-control selcls">
                            <option value=""></option>
                            <c:forEach var="user" items="${ psiClinicUsers }">
                                <option value="${user.username }">${user.userRole }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        Wealth Classification
                        <br />
                        <input type="checkbox" id="wlth_poor_draft" name="wlthPoorDraft" value="">Poor
                        <br>
                        <input type="checkbox" id="wlth_pop_draft" name="wlthPopDraft" value="">Pop
                        <br>
                        <input type="checkbox" id="wlth_pay_draft" name="wlthAbleToPayDraft" value=""> Able to pay
                        <br>
                        <br>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        Service Point
                        <br />
                        <input type="checkbox" id="sp_satelite_draft" name="spSateliteDraft" value="">Satelite
                        <br>
                        <input type="checkbox" id="sp_static_draft" name="spStaticDraft" value="">Static
                        <br>
                        <input type="checkbox" id="sp_csp_draft" name="spCspDraft" value="">CSP
                        <br>
                        <br>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <!-- 	<label for="Service Code"></label><br /> -->
                        <button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>
                    </div>

                </div>
            </div>
        </div>
 	</form:form>
 	<div id="loading_draft" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	
	<div id="draftTrackers">
		<div class="form-content" id="draftTracking"> </div>
		<div class="form-content">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
	                    <label> ${ no_slip_draft} </label> &nbsp;&nbsp; No of Slips in Draft
	                </div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
	                    <label> ${ total_payable_draft} </label> &nbsp;&nbsp; Total Payable in Draft
	                </div>
				</div>
			</div>
		</div>
		<div style="overflow:auto;">
		<br/>
		<table id="draft_tracking" class="display">
			<thead>
		           <tr>
		                <th>SL</th> 
		                <th>Slip No.</th>
		                <th>Money Receipt Date</th>
		                <th>Patient Name</th>
		               <th>Phone</th>
		               <th>Wealth Class</th>
		               <th>Service Point</th>
		               <th>Total Amount</th>
		               <th>Discount</th>
		               <th>Payable Amount</th>
		               <!--  <th>Action</th> -->
		           </tr>
		       </thead>
		       <tbody>
		         <% int sl_d = 0; %>
		       	<c:if test="${not empty draftReport }">
					<c:forEach var="report" items="${ draftReport }">
				        <tr>
				        	
				        	<td><%=++sl_d%></td>
				              <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.slip_no }</a></td>	             
				        	 <td>${ report.slip_date }</td> 
				        	 <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.patient_name }</a></td>
				            <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.phone }</a></td>
				            <td>${ report.wealth_classification }</td>
				            <td>${ report.service_point }</td>
				            <td>${ report.total_amount }</td>
				            <td>${ report.discount }</td>
				            <td>${ report.net_payable }</td>
				        <%--      <td>${ report.slip_link }</td> --%>  
				        </tr>
			       </c:forEach>
			    </c:if>
		       </tbody>
	</table>
	</div>
	</div>

	
 </div>
 <div id="tabs-5">
 	<form:form id="regReport">
 		<div class="form-content">
 			<div class="row">
 				<div class="col-md-3">
 					<div class="form-group">
 						 <label for="startDateReg"> From</label>
                        <br />
                        <input class="dt" id="startDateReg" name="startDateReg" type="text" required="true" />
 					</div>
 				</div>
 				<div class="col-md-3">
 					<div class="form-group">
 						 <label for="endDateReg"> To</label>
                        <br />
                        <input class="dt" id="endDateReg" name="endDateReg" type="text" required="true" />
 					</div>
 				</div>
 				<div class="col-md-3">
 					<div class="form-group">
                        Gender
                        <br />
                        <input type="checkbox" id="male" name="male" value=""> Male
                        <br>
                        <input type="checkbox" id="female" name="female" value=""> Female
                        <br>
                        <input type="checkbox" id="others" name="others" value=""> Others
                    </div>
 				</div>
 				<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_reg" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
              	<div class="col-md-3">
                    <div class="form-group">
                        Wealth Classification
                        <br />
                        <input type="checkbox" id="wlth_poor_reg" name="wlthPoorReg" value="">Poor
                        <br>
                        <input type="checkbox" id="wlth_pop_reg" name="wlthPopReg" value="">Pop
                        <br>
                        <input type="checkbox" id="wlth_pay_reg" name="wlthAbleToPayReg" value=""> Able to pay
                        <br>
                        <br>
                    </div>
                </div>
 			</div>
 			<div class="row">
 				<div class="col-md-4">
                   <div class="form-group">
                     
                       <button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>
                   </div>

               </div>
 			</div>
 		</div>
 	</form:form>
 	<div id="loading_reg" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	<div id="regReports">
		<div class="form-content" id="regReportTile"></div>
		<div class="form-content">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard.newPatient } </label> &nbsp;&nbsp; New Registration
              		</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; Old Clients
              		</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
			</div>
		</div>
		<div style="overflow:auto;">
			<br/>
			<table id="reg_report" class="display">
				<thead>
					<tr>
						<th>SL</th>
						<th>Patient Name</th>
						<th>UIC</th>
						<th>Health Id</th>
						<th>Mobile No</th>
						<th>Gender</th>
						<th>Registration Date</th>
						<th>Age</th>
						<th>Union/Municipality/CC</th>
						
					</tr>
				</thead>
				<tbody>
					<%int sl_r = 0;%>
					<c:forEach var="report" items="${regReport }">
						<tr>
							<td><%=++sl_r%></td>
							<td>${report.patient_name }</td>
							<td>${report.uic }</td>
							<td>${report.health_id }</td>
							<td>${report.mobile_no }</td>
							<td>${report.gender }</td>
							<td>${report.register_date }</td>
							<td>${report.age }</td>
							<td>${report.cc }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
 </div>
 <div id="tabs-6">
 	<form:form id="visitReport">
 		<div class="form-content">
 			<div class="row">
 				<div class="col-md-3">
 					<div class="form-group">
 						 <label for="startDateVisit"> From</label>
                        <br />
                        <input class="dt" id="startDateVisit" name="startDateVisit" type="text" required="true" />
 					</div>
 				</div>
 				<div class="col-md-3"> 					
					<div class="form-group">
						 <label for="endDateVisit"> To</label>
                      <br />
                      <input class="dt" id="endDateVisit" name="endDateVisit" type="text" required="true" />
					</div> 					
 				</div>
 			<!-- 	<div class="col-md-3">
 					
 					<div class="form-group">
 						<label for="search_visit">Search</label><br/>
 						<input id="search_visit" name="search_visit" type="text">
 					</div>
 				
 				</div> -->
 				<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic_visit" class="form-control selcls">
	                  			<option value="0">Please Select</option>
								<c:forEach items="${clinics}" var="clinic">	                  				 
									<option value="${clinic.clinicId}">${clinic.name}</option>						             
								</c:forEach>
							</select>                			
						</div>                  	
	              	</div>
              	</c:if>
              	 <div class="col-md-3">
                    <div class="form-group">
                        Wealth Classification
                        <br />
                        <input type="checkbox" id="wlth_poor_visit" name="wlthPoorVisit" value="">Poor
                        <br>
                        <input type="checkbox" id="wlth_pop_visit" name="wlthPopVisit" value="">Pop
                        <br>
                        <input type="checkbox" id="wlth_pay_visit" name="wlthAbleToPayVisit" value=""> Able to pay
                        <br>
                        <br>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        Service Point
                        <br />
                        <input type="checkbox" id="sp_satelite_visit" name="spSateliteVisit" value="">Satelite
                        <br>
                        <input type="checkbox" id="sp_static_visit" name="spStaticVisit" value="">Static
                        <br>
                        <input type="checkbox" id="sp_csp_visit" name="spCspVisit" value="">CSP
                        <br>
                        <br>
                    </div>
                </div>
 				<div class="col-md-3">
                   <div class="form-group">
                     
                       <button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>
                   </div>

               </div>
 			</div>
 			
 		</div>
 	</form:form>
 	<div id="loading_visit" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	<div id="visitReports">
		<div class="form-content" id="visitReportTitle"></div>
		<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
						<label for="Service Code">${dashboard.newPatient }</label>
                  	  	&nbsp;&nbsp; New Registration
                  	</div>  
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; 
                  		Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
	                    <label> ${ dashboard_service_cotact_value } </label> &nbsp;&nbsp; Total Service Contact
	                </div>
				</div>
			</div>
			<div class="row">
			
 				<div class="col-md-3">
                	<div class="form-group">                							
						<label> ${dashboard.servedPatient  } </label>  &nbsp;&nbsp; Patients Served						
                   </div>
                  	
             	</div>
             	<%-- <div class="col-md-3">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-3">
	                <div class="form-group">
	                    <label> ${ dashbaord_discount_value } </label> &nbsp;&nbsp; Total Discount
	                </div>
	            </div> --%>
             	
 			
			</div>
		</div>
		<div style="overflow:auto;">
			<br/>
			<table id="visit_report" class="display">
				<thead>
					<tr>
						
						<th>SL</th>
						<th>Name</th>
						<th>HID</th>
						<th>Mobile Number</th>
						<th>Gender</th>
						<th>Age</th>
						<th>Registration Date</th>
						<th>Last Visit Date</th>
						<th>Visit Count</th>
					</tr>
				</thead>
				<tbody>
					<%int sl_v = 0;%>
					<c:forEach var="report" items="${visitReport }">
						<tr>
							<td><%=sl_v++%></td>
							<td>${report.patient_name}</td>
							<td>${report.hid }</td>
							<td>${report.mobile_number }</td>
							<td>${report.gender }</td>
							<td>${report.age }</td>
							<td>${report.reg_date }</td>
							<td>${report.last_visit_date}</td>
							<td>${report.visit_count }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
 </div>
</div>

  
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>


<script type="text/javascript">

var $jq = jQuery.noConflict();

var $jQuery = jQuery.noConflict();
$jQuery( function() {
	$jQuery( "#tabs" ).tabs();
  } );
  

$jq( function() {
	$jq("#startDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#endDate").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
$jq( function() {
	$jq("#from").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
	$jq("#to").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
  } );
$jq(function(){
	$jq(".dt").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date });
});
/* $jq('#startDate').attr('max', maxDate);
$jq('#endDate').attr('max', maxDate); */

</script>

<script type="text/javascript">
var $JQuery = jQuery.noConflict();
$JQuery("#ServiceProviderWise").submit(function(event) { 
var e = document.getElementById("provider");
var provider = e.options[e.selectedIndex].value;
var startDate = $JQuery('input[name=from]').val();
var endDate = $JQuery('input[name=to]').val();
var reportTitle = "Service Provider Wise Report for "+$JQuery("#provider option:selected").html()+ " (" +startDate +" to "+ endDate+")" ;
var clinicName = $JQuery("#clinicName").val();

var clinic = document.getElementById("clinics");
var clinicCode = "";
if(clinic !== null){		
	clinicCode = clinic.options[clinic.selectedIndex].value;
	if(clinicCode != "0") {
		clinicName = "";
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
	}
}
else {
	clinicCode = -1;
}
var title = "Service Provider Wise Report "+clinicName+" From "+startDate+" To "+endDate;

var url = "/openmrs/module/PSI/ServiceProviderWise.form?startDate="+startDate+"&endDate="+endDate+"&dataCollector="+provider+"&code="+clinicCode;
event.preventDefault();
$JQuery("#loading_prov").show();
$JQuery.ajax({
	   type : "GET",
	   contentType : "application/json",
	   url : url,	 
	   dataType : 'html',
	   timeout : 100000,
	   beforeSend: function() {	    
	   
	   },
	   success : function(data) {		   
		   $JQuery("#serviceProviders").html(data);
		   $JQuery('#serviceProvider').DataTable({
			   bFilter: false,
		       bInfo: false,
		       "searching": true,
			   dom: 'Bfrtip',
			   destroy: true,
			   buttons: [
			              {
			                 extend: 'excelHtml5',
			                 title: title,
			                 text: 'Export as .xlxs'
			             },
			             {
			         		extend: 'pdfHtml5',
			         		title: title,
			         		text: 'Export as .pdf',
			         		orientation: 'landscape',
			         		pageSize: 'LEGAL'
			         	  }
			            
			         ]
			   
		   });
		   $JQuery("#loading_prov").hide();
		   $JQuery("#serviceProviderReports").html(reportTitle);
	   },
	   error : function(e) {
	    console.log("ERROR: ", e);
	    $JQuery("#loading_prov").hide();
	    /* display(e); */
	   },
	   done : function(e) {	    
		 $JQuery("#loading_prov").hide();
	    console.log("DONE");
	    //enableSearchButton(true);
	   }
	  }); 
});

$JQuery("#ServicePointWise").submit(function(event) {	
	/* alert("checked"); */
 	var clinic = document.getElementById("clinic_comp");
	var clinicCode = "";
	var clinicName = $JQuery("#clinicName").val();
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
		if(clinicCode != "0") {
		clinicName = "";
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
		}
	}else {
		clinicCode = -1;
	}

	var startDate = $JQuery('input[name=startDate]').val();
	var endDate = $JQuery('input[name=endDate]').val();
	var category = $JQuery("#cat").val();
	var url = "/openmrs/module/PSI/ServicePointWise.form?startDate="+startDate+"&endDate="+endDate+"&category="+category;
	url += "&code="+clinicCode;
	var title = "Comprehensive Report "+clinicName+" From "+startDate + " To " + endDate;
	event.preventDefault();	
	$JQuery("#loading_comp_").show();
	$JQuery.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   
		   },
		   success : function(data) {	   
			   $JQuery("#servicePoints").html(data);			  
			    $JQuery("#servicePoint").DataTable({
			    	
				   bFilter: false,
			       bInfo: false,
			       "searching": true,
				   dom: 'Bfrtip',
				   destroy: true,
				   buttons: [
				             {
				                 extend: 'excelHtml5',
				                 title: title,
				                 text: 'Export as .xlxs'
				             },
				             {
				         		extend: 'pdfHtml5',
				         		title: title,
				         		text: 'Export as .pdf',
				         		orientation: 'landscape',
				         		pageSize: 'LEGAL'
					         }
				         ]
			   }); 
			   $JQuery("#servicePointWiseReport").html("Comprehensive Report From "+startDate + " To " + endDate);
			   $JQuery("#loading_comp_").hide();
		   },
		   error : function(e) {
		    console.log("ERROR: ", e);
		    $JQuery("#loading_comp_").hide();
		  /*   display(e); */
		   },
		   done : function(e) {	    
		    console.log("DONE");
		    //enableSearchButton(true);
		   }
		  }); 
	});
	  
$JQuery("#clinics").change(function(event) { 
	var clinicForProvider = document.getElementById("clinics");
	var clinicForProviderValue = clinicForProvider.options[clinicForProvider.selectedIndex].value;	
	var url = "/openmrs/module/PSI/providerByClinic.form?code="+clinicForProviderValue;

	if(clinicForProviderValue ==""){
		 $JQuery("#provider").html("");	 			
		
	} else {
		event.preventDefault();
		$JQuery.ajax({
			   type : "GET",
			   contentType : "application/json",
			   url : url,	 
			   dataType : 'html',		   
			   beforeSend: function() {    
			   
			   },
			   success : function(data) {		   
				   $JQuery("#provider").html(data);			  
			   },
			   error : function(e) {
			    console.log("ERROR: ", e);
			    /* display(e); */
			   },
			   done : function(e) {	    
			    console.log("DONE");
			    //enableSearchButton(true);
			   }
			}); 
	 	}

	});	
	
$JQuery("#clinic_slip").change(function(event) { 
	var clinicForProvider = document.getElementById("clinic_slip");
	var clinicForProviderValue = clinicForProvider.options[clinicForProvider.selectedIndex].value;	
	var url = "/openmrs/module/PSI/providerByClinic.form?code="+clinicForProviderValue;

	if(clinicForProviderValue ==""){
		 $JQuery("#collector").html("");	 			
		
	} else {
		event.preventDefault();
		$JQuery.ajax({
			   type : "GET",
			   contentType : "application/json",
			   url : url,	 
			   dataType : 'html',		   
			   beforeSend: function() {    
			   
			   },
			   success : function(data) {		   
				   $JQuery("#collector").html(data);			  
			   },
			   error : function(e) {
			    console.log("ERROR: ", e);
			    /* display(e); */
			   },
			   done : function(e) {	    
			    console.log("DONE");
			    //enableSearchButton(true);
			   }
			}); 
	 	}

	});
$JQuery("#clinic_draft").change(function(event) { 
	var clinicForProvider = document.getElementById("clinic_draft");
	var clinicForProviderValue = clinicForProvider.options[clinicForProvider.selectedIndex].value;	
	var url = "/openmrs/module/PSI/providerByClinic.form?code="+clinicForProviderValue;

	if(clinicForProviderValue ==""){
		 $JQuery("#collector_draft").html("");	 			
		
	} else {
		event.preventDefault();
		$JQuery.ajax({
			   type : "GET",
			   contentType : "application/json",
			   url : url,	 
			   dataType : 'html',		   
			   beforeSend: function() {    
			   
			   },
			   success : function(data) {		   
				   $JQuery("#collector_draft").html(data);			  
			   },
			   error : function(e) {
			    console.log("ERROR: ", e);
			    /* display(e); */
			   },
			   done : function(e) {	    
			    console.log("DONE");
			    //enableSearchButton(true);
			   }
			}); 
	 	}

	});
	
	
	
$JQuery('#servicePoint').DataTable({
	   bFilter: false,
       bInfo: false,
       "searching": true,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Comprehensive Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             },
	             {
		         		extend: 'pdfHtml5',
		         		title: "Comprehensive Revenue Report_"+ new Date(),
		         		text: 'Export as .pdf',
		         		orientation: 'landscape',
		         		pageSize: 'LEGAL'
		         }
	         ]
});
 $JQuery("#visit_report").DataTable({
	   bFilter: false,
	   "searching": true,
       bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Visit Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             },
	             {
	         		extend: 'pdfHtml5',
	         		title: "Visit Report_"+ new Date(),
	         		text: 'Export as .pdf',
	         		orientation: 'landscape',
	         		pageSize: 'LEGAL'
		         }
	         ]
}); 
/* $JQuery("#comp_service_reporting").DataTable({
	   bFilter: false,
       bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Service Point Wise Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             }			         
	         ]
}); */
$JQuery("#servicePointWiseReport").html("");
$JQuery("#slipTracking").html("");
$JQuery("#draftTracking").html("");
$JQuery("#compServiceReporting").html("");
$JQuery("#visitReportTitle").html("");
$JQuery("#regReportTile").html("");
$JQuery('#serviceProviderDefault').DataTable({
	   bFilter: false,
       bInfo: false,
       "searching": true,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Service Provider Wise Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             },
	             {
		         		extend: 'pdfHtml5',
		         		title: "Service Provider Wise Revenue Report_"+ new Date(),
		         		text: 'Export as .pdf',
		         		orientation: 'landscape',
		         		pageSize: 'LEGAL'
			     }
	         ]
});
/* $JQuery('#slip_tracking').DataTable({
	 bFilter: false,
	    bInfo: false,
		   dom: 'Bfrtip',
		   destroy: true,
		   buttons: [
		             {
		                 extend: 'excelHtml5',
		                 title: "Sliping Track Report_"+ new Date(),
		                 text: 'Export as .xlxs'
		             }			         
		         ]
	}
});  */
$JQuery("#serviceProviderReports").html("");
 $JQuery("#slip_tracking").DataTable({
	bFilter: false,
    bInfo: false,
    "searching": true,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Slip wise Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             },
	             {
	         		extend: 'pdfHtml5',
	         		title: "Slip wise Report_"+ new Date(),
	         		text: 'Export as .pdf',
	         		orientation: 'landscape',
	         		pageSize: 'LEGAL'
			     }
	         ]
});
 $JQuery("#draft_tracking").DataTable({
		bFilter: false,
	    bInfo: false,
	    "searching": true,
		   dom: 'Bfrtip',
		   destroy: true,
		   buttons: [
		             {
		                 extend: 'excelHtml5',
		                 title: "Draft wise Report_"+ new Date(),
		                 text: 'Export as .xlxs'
		             },
		             {
		         		extend: 'pdfHtml5',
		         		title: "Draft wise Report_"+ new Date(),
		         		text: 'Export as .pdf'
					 }
		         ]
	});
$JQuery("#reg_report").DataTable({
	bFilter: false,
    bInfo: false,
    "searching": true,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	              {
	                 extend: 'excelHtml5',
	                 title: "Registration wise Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             },
	             {
	         		extend: 'pdfHtml5',
	         		title: "Registration wise Report_"+ new Date(),
	         		text: 'Export as .pdf',
	         		orientation: 'landscape',
	         		pageSize: 'LEGAL' 
				}
	         ]
}); 
$JQuery("#slipTracking_").submit(function(event){
	event.preventDefault();
	var clinic = document.getElementById("clinic_slip");
	var clinicCode = "";
	var clinicName = $JQuery("#clinicName").val();
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
		if(clinicCode != "0") {
		clinicName = "";
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
		}
	}else {
		clinicCode = -1;
	}
	$JQuery("#loading").show();
	/*  alert("hits"); */
	var checkStrVal = ["false","true"];
	var startDateSlip = $JQuery("#startDateSlip").val();
	var endDateSlip = $JQuery("#endDateSlip").val();
	/* console.log(startDateSlip);
	console.log(endDateSlip); */
	var dataCollector = $JQuery("#collector").val();
	var wlthPoor = $JQuery("#wlth_poor").is(":checked") == true ? "Poor" : "";
	var wlthPop = $JQuery("#wlth_pop").is(":checked") == true ? "PoP" : "";
	var wlthPay = $JQuery("#wlth_pay").is(":checked") == true ? "Able to Pay" : "";
	var spSatelite = $JQuery("#sp_satelite").is(":checked") == true ? "Satellite" : "";
	var spStatic = $JQuery("#sp_static").is(":checked") == true ? "Static" : "";
	var spCsp = $JQuery("#sp_csp").is(":checked") == true ? "CSP" : "";
	var url = "/openmrs/module/PSI/slipTracking.form?startDate="+startDateSlip+"&endDate="+endDateSlip;
	url += "&dataCollector="+dataCollector+"&wlthPoor="+wlthPoor+"&wlthPop="+wlthPop+"&wlthAbleToPay="+wlthPay;
	url += "&spSatelite="+spSatelite+"&spStatic="+spStatic+"&spCsp="+spCsp;
	url +="&code="+clinicCode;
	var title = "Slip wise Report "+clinicName+ " From "+startDateSlip+" To "+endDateSlip
	$JQuery.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   		
		   },
		   success:function(data){
			  
			 $JQuery("#slipTrackers").html(data);
			 
			  $JQuery("#slip_tracking").DataTable({
					bFilter: false,
				    bInfo: false,
				    "searching": true,
					   dom: 'Bfrtip',
					   destroy: true,
					   buttons: [
					             {
					                 extend: 'excelHtml5',
					                 title: title ,
					                 text: 'Export as .xlxs'
					             },
					             {
					         		extend: 'pdfHtml5',
					         		title: title ,
					         		text: 'Export as .pdf',
					         		orientation: 'landscape',
					         		pageSize: 'LEGAL'
								}
					         ]
				});	
			  /* console.log(data); */
			  
			  $JQuery("#slipTracking").html("Slip Tracking wise Report From "+startDateSlip+" To "+endDateSlip);
			  
			  $JQuery("#loading").hide();
		   },
		   error: function(data){
			   $JQuery("#loading").hide();
		   }
		  
	});
	
	
});

$JQuery("#draftTracking_").submit(function(event){
	event.preventDefault();
	var clinic = document.getElementById("clinic_draft");
	var clinicCode = "";
	var clinicName = $JQuery("#clinicName").val();
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
		if(clinicCode != "0") {
		clinicName = "";
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
		}
	}else {
		clinicCode = -1;
	}
	$JQuery("#loading_draft").show();
	var startDateDraft = $JQuery("#startDateDraft").val();
	var endDateDraft = $JQuery("#endDateDraft").val();
	
	var dataCollectorDraft = $JQuery("#collector_draft").val();
	var wlthPoorDraft = $JQuery("#wlth_poor_draft").is(":checked") == true ? "Poor" : "";
	var wlthPopDraft = $JQuery("#wlth_pop_draft").is(":checked") == true ? "PoP" : "";
	var wlthPayDraft = $JQuery("#wlth_pay_draft").is(":checked") == true ? "Able to Pay" : "";
	var spSateliteDraft = $JQuery("#sp_satelite_draft").is(":checked") == true ? "Satellite" : "";
	var spStaticDraft = $JQuery("#sp_static_draft").is(":checked") == true ? "Static" : "";
	var spCspDraft = $JQuery("#sp_csp_draft").is(":checked") == true ? "CSP" : "";
	var url = "/openmrs/module/PSI/draftTracking.form?startDate="+startDateDraft+"&endDate="+endDateDraft;
	url += "&dataCollector="+dataCollectorDraft+"&wlthPoor="+wlthPoorDraft+"&wlthPop="+wlthPopDraft+"&wlthAbleToPay="+wlthPayDraft;
	url += "&spSatelite="+spSateliteDraft+"&spStatic="+spStaticDraft+"&spCsp="+spCspDraft;
	url +="&code="+clinicCode;
	var title = "Draft wise Report "+clinicName+" From "+startDateDraft+" To "+endDateDraft;
	
	$JQuery.ajax({
		type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   		
		   },
		   success:function(data){
				 $JQuery("#draftTrackers").html(data);
				 
				  $JQuery("#draft_tracking").DataTable({
					  "searching": true,
						bFilter: false,
					    bInfo: false,
					    
						   dom: 'Bfrtip',
						   destroy: true,
						   buttons: [
						             {
						                 extend: 'excelHtml5',
						                 title: title,
						                 text: 'Export as .xlxs'
						             },
						             {
						         		extend: 'pdfHtml5',
						         		title: title,
						         		text: 'Export as .pdf',
						         		orientation: 'landscape',
						         		pageSize: 'LEGAL'
						         	  }
						         ]
					});	
				  /* console.log(data); */
				  $JQuery("#draftTracking").html("Draft Tracking wise Report From "+startDateDraft+" To "+endDateDraft);
				  $JQuery("#loading_draft").hide();   
		   },
		   error:function(){
			   $JQuery("#loading_draft").hide();
		   }
	});
	
});

/* $JQuery("#compServiceReporting_").submit(function(event){
	event.preventDefault();
	$JQuery("#loading_comp").show();

	var startDate = $JQuery("#startDateComp").val();
	var endDate = $JQuery("#endDateComp").val();
	var serviceCategory = $JQuery("#service_category").val();
	var searchString = $JQuery("#search_comp").val();
	
	var url = "/openmrs/module/PSI/compServiceReporting.form?startDate="+startDate;
	url += "&endDate="+endDate;
	url += "&serviceCategory="+serviceCategory;
	url += "&searchString="+searchString;
	
	 $JQuery.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	
	    	$JQuery("#compServiceReports").html(data);
	    	$JQuery("#comp_service_reporting").DataTable({
	    		   bFilter: false,
	    	       bInfo: false,
	    		   dom: 'Bfrtip',
	    		   destroy: true,
	    		   buttons: [
	    		             {
	    		                 extend: 'excelHtml5',
	    		                 title: "Service Point Wise Revenue Report_"+ new Date(),
	    		                 text: 'Export as .xlxs'
	    		             }			         
	    		         ]
	    	});
	    	$JQuery("#compServiceReporting").html("Comprenhesive Service Reporting From "+startDate+" "+endDate);
	    	$JQuery("#loading_comp").hide();
	    },
	    error:function(data){  	
	    	$JQuery("#loading_comp").hide();
	    }
	}); 
}); */

$JQuery("#regReport").on("submit",function(event){
	event.preventDefault();
	var clinic = document.getElementById("clinic_reg");
	var clinicCode = "";
	var clinicName = $JQuery("#clinicName").val();
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
		if(clinicCode != "0") {
		clinicName = $JQuery("#clinicName").val();
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
		}
	}else {
		clinicCode = -1;
	}
	$JQuery("#loading_reg").show();
	var st_date = $JQuery("#startDateReg").val();
	var ed_date = $JQuery("#endDateReg").val();
	var gender = "";
	$JQuery("#male").is(":checked") == true ? gender += "M" : gender += "";
	$JQuery("#female").is(":checked") == true ? gender += "F" : gender += "";
	$JQuery("#others").is(":checked") == true ? gender += "O" : gender += "";
	var wlthPoorDraft = $JQuery("#wlth_poor_reg").is(":checked") == true ? "Poor" : "";
	var wlthPopDraft = $JQuery("#wlth_pop_reg").is(":checked") == true ? "PoP" : "";
	var wlthPayDraft = $JQuery("#wlth_pay_reg").is(":checked") == true ? "Able to Pay" : "";
	var url =  "/openmrs/module/PSI/registrationReport.form?startDate="+st_date;
	url += "&endDate="+ed_date;
	url += "&gender="+gender;
	url += "&code="+clinicCode;
	url += "&wlthPoor="+wlthPoorDraft+"&wlthPop="+wlthPopDraft+"&wlthAbleToPay="+wlthPayDraft;
	
	var title = "Registration Report "+clinicName+" From "+st_date+" To "+ed_date;
	
	$JQuery.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	$JQuery("#regReports").html(data);
	    	$JQuery("#reg_report").DataTable({
	    		bFilter: false,
	    	    bInfo: false,
	    	    "searching": true,
	    		   dom: 'Bfrtip',
	    		   destroy: true,
	    		   buttons: [
	    		              {
	    		                 extend: 'excelHtml5',
	    		                 title: title,
	    		                 text: 'Export as .xlxs',
	    		               
	    		             },
				             {
				         		extend: 'pdfHtml5',
				         		title: title,
				         		text: 'Export as .pdf',
				         		orientation: 'landscape',
				         		pageSize: 'LEGAL' 
							}
	    		         ]
	    	});
	    	$JQuery("#regReportTile").html("Registration Report From "+st_date+" To "+ed_date);
	    	$JQuery("#loading_reg").hide();
	    },
	    error:function(data){
	    	$JQuery("#loading_reg").hide();
	    }
	    
	});
});

$JQuery("#visitReport").on("submit",function(event){
	event.preventDefault();
	$JQuery("#loading_visit").show();
	var clinic = document.getElementById("clinic_visit");
	var clinicCode = "";
	var clinicName = $JQuery("#clinicName").val();
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
		if(clinicCode != "0") {
		clinicName = "";
		clinicName = "For " + clinic.options[clinic.selectedIndex].text;
		}
	}else {
		clinicCode = -1;
	}
	var startDate = $JQuery("#startDateVisit").val();
	var endDate = $JQuery("#endDateVisit").val();
	var wlthPoorDraft = $JQuery("#wlth_poor_visit").is(":checked") == true ? "Poor" : "";
	var wlthPopDraft = $JQuery("#wlth_pop_visit").is(":checked") == true ? "PoP" : "";
	var wlthPayDraft = $JQuery("#wlth_pay_visit").is(":checked") == true ? "Able to Pay" : "";
	var spSateliteDraft = $JQuery("#sp_satelite_visit").is(":checked") == true ? "Satellite" : "";
	var spStaticDraft = $JQuery("#sp_static_visit").is(":checked") == true ? "Static" : "";
	var spCspDraft = $JQuery("#sp_csp_visit").is(":checked") == true ? "CSP" : "";
	var url = "/openmrs/module/PSI/visitReport.form?startDate="+startDate;
	url += "&endDate="+endDate;
	url += "&wlthPoor="+wlthPoorDraft+"&wlthPop="+wlthPopDraft+"&wlthAbleToPay="+wlthPayDraft;
	url += "&spSatelite="+spSateliteDraft+"&spStatic="+spStaticDraft+"&spCsp="+spCspDraft;
	url += "&code="+clinicCode;
	
	var title = "Visit Report "+clinicName+" From "+startDate+" To "+endDate;
	
	$JQuery.ajax({
		type:"GET",
		contentType : "application/json",
	    url : url,	 
	    dataType : 'html',
	    timeout : 100000,
	    beforeSend: function() {	    
	    		
	    },
	    success:function(data){
	    	console.log(data);
	    	$JQuery("#visitReports").html(data);
	    	 $JQuery("#visit_report").DataTable({
	    		   bFilter: false,
	    		   "searching": true,
	    	       bInfo: false,
	    		   dom: 'Bfrtip',
	    		   destroy: true,
	    		   buttons: [
	    		             {
	    		                 extend: 'excelHtml5',
	    		                 title: title,
	    		                 text: 'Export as .xlxs'
	    		             },
				             {
				         		extend: 'pdfHtml5',
				         		title: title,
				         		text: 'Export as .pdf',
				         		orientation: 'landscape',
				         		pageSize: 'LEGAL'
					         }
	    		         ]
	    	}); 
	    	$JQuery("#visitReportTitle").html("Visit Report From "+startDate+" To "+endDate);
	    	$JQuery("#loading_visit").hide();    
	    },
	    error: function(data){
	    	$JQuery("#loading_visit").hide();
	    }
	});
});


</script>
 



<%@ include file="/WEB-INF/template/footer.jsp"%>
<style>
.dataTables_wrapper .dt-buttons {
  float:none;  
  text-align:right;
  position: absolute;
  top: -26px;
  margin-left: 1036px
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
    border: 1px solid #1aad96;
    /* background: #1aac9b url(images/ui-bg_inset-soft_30_ffffff_1x100.png) 50% 50% repeat-x; */
    font-weight: bold;
    background: #4aad9b;
    color: #0e5c52;
}
#serviceP > thead > tr > th{"Draft wise Report From "+startDateDraft+" To "+endDateDraft,
  border: 1px solid black;
}
.dataTables_wrapper .dataTables_filter {
    float: left;
    text-align: right;
}

</style>