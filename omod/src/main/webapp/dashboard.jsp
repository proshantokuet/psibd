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
    <li><a href="#tabs-1">Service Point Wise Revenue Report</a></li>
    <li><a href="#tabs-2">Service Provider Wise Revenue Report</a></li>
    <li><a href="#tabs-3">Slip Tracking Report</a></li> 
    <li><a href="#tabs-4">Draft Tracking Report</a></li>
    <li><a href="#tabs-5">Comprehensive Service Report</a></li>
  </ul>
  <div id="tabs-1">
  
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
					</div>
                  	
              	</div>
              	<c:if test="${showClinic eq 1}">
	              	<div class="col-md-3">
	               		<div class="form-group">
	                  		<label for="Service Code">Clinic</label> <br />
	                  		<select name="clinic" id="clinic" class="form-control selcls">
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
	</div>
	
	 
	 <table id="servicePointDefault" class="display">
	 	<thead>
		        <tr>
		            <th>Category</th>
		            <th>Code</th>
		            <th>Item</th>
		            <th>Static</th>
		            <th>Satellite</th>
		            <th>CSP</th>
		            <th>Total</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach var="report" items="${ servicePointWiseReports }">
		        <tr>
		            <td>${ report.category }</td>
		        	<td>${ report.code }</td>
		            <td>${ report.item }</td>
		            <td>${ report.clinic }</td>
		            <td>${ report.satelite }</td>
		            <td>${ report.csp }</td>
		            <td>${ report.total }</td>
		        </tr>
		       </c:forEach>
		        
		    </tbody>
	
	</table>
	    
	
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
						<select id="provider" required="true" style="width: 160px" >
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
	</div>  
 
 
 
  
	<table id="serviceProviderDefault" class="display">
		  <thead>
		        <tr>
		            <th>Category</th>	
		            <th>Code</th>            
		            <th>Item</th>
		            <th>Number of Service</th>	            
		            <th>Total</th>
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
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="startDateSlip"> From</label>
                        <br />
                        <input class="dt" id="startDateSlip" name="startDateSlip" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="endDateSlip">To</label>
                        <br />
                        <input class="dt" id="endDateSlip" name="endDateSlip" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="collector">Select Data Collector</label>
                        <br />
                        <select id="collector" name="collector" style="width: 160px">
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
	    <table id="slip_tracking" class="display">
         <thead>
            <tr>
                 <th>SL</th> 
                 <th>Slip No.</th>
                 <th>Date</th>
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
	                    <td>${ report.slip_no }</td>	             
		            	 <td>${ report.slip_date }</td> 
			        	 <td>${ report.patient_name }</td>
			            <td>${ report.phone }</td>
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
 <div id="tabs-4">
 	<form:form id="draftTracking_">
 		  <div class="form-content">

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="startDateDraft"> From</label>
                        <br />
                        <input class="dt" id="startDateDraft" name="startDateDraft" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="endDateDraft">To</label>
                        <br />
                        <input class="dt" id="endDateDraft" name="endDateDraft" type="text" required="true" />
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="collector_draft">Select Data Collector</label>
                        <br />
                        <select id="collector_draft" name="collector_draft" style="width: 160px">
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
		<table id="draft_tracking" class="display">
			<thead>
		           <tr>
		                <th>SL</th> 
		                <th>Slip No.</th>
		                <th>Date</th>
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
				              <td>${ report.slip_no }</td>	             
				        	 <td>${ report.slip_date }</td> 
				        	 <td>${ report.patient_name }</td>
				            <td>${ report.phone }</td>
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
 <div id="tabs-5">
 	<form:form id="compServiceReporting_">
 		<div class="form-content">
 			<div class="row">
 				<div class="col-md-4">
 					<div class="form-group">
 						 <label for="startDateComp"> From</label>
                        <br />
                        <input class="dt" id="startDateComp" name="startDateComp" type="text" required="true" />
 					</div>
 				</div>
 				<div class="col-md-4">
 					<div class="form-group">
 						 <label for="endDateComp"> To</label>
                        <br />
                        <input class="dt" id="endDateComp" name="endDateComp" type="text" required="true" />
 					</div>
 				</div>
 			</div>
 			<div class="row">
 				<div class="col-md-4">
 					<div class="form-group">
 						<label for="service_category">Service Category</label><br/>
 						<select id="service_category" name="service_category" style="width: 160px">
 							<option value=""></option>
 							  <c:forEach items="${service_category}" var="service"> 
					              	 <form:option value="${service.categoryName}" label="${service.categoryName}"/>					              
					              </c:forEach>
 						</select>
 					</div>
 				</div>
 				<div class="col-md-4">
 					<div class="form-group">
 						<label for="search_comp">Search</label><br/>
 						<input id="search_comp" name="search_comp" type="text">
 					</div>
 				</div>
				 <div class="col-md-4">
                   <div class="form-group">
                       <!-- 	<label for="Service Code"></label><br /> -->
                       <button style="width: 120px; margin-top: 30px;" type="submit" class="btnSubmit">Submit</button>
                   </div>

               </div>
			
               </div>
 			</div>
 	</form:form>
 	<div id="loading_comp" style="display: none;position: absolute; z-index: 1000;margin-left:45%"> 
			<img width="50px" height="50px" src="<c:url value="/moduleResources/PSI/images/ajax-loading.gif"/>">
	</div>
	<div id="compServiceReports">
		<div class="form-content" id="compServiceReporting"></div>
		<div class="form-content">
			<div class="row">
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_reg } </label> &nbsp;&nbsp; New Registration
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_old_clients } </label> &nbsp;&nbsp; Old Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_new_clients } </label> &nbsp;&nbsp; New Clients
              		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                  		<label> ${dashboard_service_cotact_value } </label> &nbsp;&nbsp; Old Client
              		</div>
				</div>
			</div>
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
			</div>
		</div>
		<table id="comp_service_reporting" class="display">
		</table>
	</div>
 </div>
 
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/jszip.min.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/buttons.html5.min.js"></script>

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
var reportTitle = "Service Provider Wise Revenue Report for "+$JQuery("#provider option:selected").html()+ " (" +startDate +" to "+ endDate+")" ;

var title = "Service Provider Wise Revenue Report_"+startDate+"_"+endDate;
var clinic = document.getElementById("clinics");
var clinicCode = "";
if(clinic !== null){		
	clinicCode = clinic.options[clinic.selectedIndex].value;
}else {
	clinicCode = -1;
}

var url = "/openmrs/module/PSI/ServiceProviderWise.form?startDate="+startDate+"&endDate="+endDate+"&dataCollector="+provider+"&code="+clinicCode;
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
		   $JQuery("#serviceProviders").html(data);
		   $JQuery('#serviceProvider').DataTable({
			   bFilter: false,
		       bInfo: false,
			   dom: 'Bfrtip',
			   destroy: true,
			   buttons: [
			             {
			                 extend: 'excelHtml5',
			                 title: title,
			                 text: 'Export as .xlxs'
			             }			         
			         ]
			   
		   });
		   $JQuery("#serviceProviderReports").html(reportTitle);
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
});

$JQuery("#ServicePointWise").submit(function(event) {	
	
	var clinic = document.getElementById("clinic");
	var clinicCode = "";
	if(clinic !== null){		
		clinicCode = clinic.options[clinic.selectedIndex].value;
	}else {
		clinicCode = -1;
	}
	
	var startDate = $JQuery('input[name=startDate]').val();
	var endDate = $JQuery('input[name=endDate]').val();
	
	var url = "/openmrs/module/PSI/ServicePointWise.form?startDate="+startDate+"&endDate="+endDate+"&clinic_code="+clinicCode;
	var title = "Service Point Wise Revenue Report_"+startDate+"_"+endDate;
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
			   $JQuery("#servicePoints").html(data);			  
			   $JQuery('#servicePoint').DataTable({
				   bFilter: false,
			       bInfo: false,
				   dom: 'Bfrtip',
				   destroy: true,
				   buttons: [
				             {
				                 extend: 'excelHtml5',
				                 title: title,
				                 text: 'Export as .xlxs'
				             }			         
				         ]
			   });
			   $JQuery("#servicePointWiseReport").html("Service Point Wise Revenue Report for "+startDate + " to " + endDate);
		   },
		   error : function(e) {
		    console.log("ERROR: ", e);
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
	
	
	
$JQuery('#servicePointDefault').DataTable({
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
$JQuery("#servicePointWiseReport").html("Service Point Wise Revenue Report for Today");
$JQuery("#slipTracking").html("Slip Tracking wise Report For Today");
$JQuery("#draftTracking").html("Draft Tracking wise Report For Today");
$JQuery("#compServiceReporting").html("Comprenhesive Service Reporting For Today");
$JQuery('#serviceProviderDefault').DataTable({
	   bFilter: false,
       bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Service Provider Wise Revenue Report_"+ new Date(),
	                 text: 'Export as .xlxs'
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
$JQuery("#serviceProviderReports").html("Service Provider Wise Revenue Report for Today");
 $JQuery("#slip_tracking").DataTable({
	bFilter: false,
    bInfo: false,
	   dom: 'Bfrtip',
	   destroy: true,
	   buttons: [
	             {
	                 extend: 'excelHtml5',
	                 title: "Slip wise Report_"+ new Date(),
	                 text: 'Export as .xlxs'
	             }			         
	         ]
});
 $JQuery("#draft_tracking").DataTable({
		bFilter: false,
	    bInfo: false,
		   dom: 'Bfrtip',
		   destroy: true,
		   buttons: [
		             {
		                 extend: 'excelHtml5',
		                 title: "Draft wise Report_"+ new Date(),
		                 text: 'Export as .xlxs'
		             }			         
		         ]
	});
$JQuery("#slipTracking_").submit(function(event){
	event.preventDefault();
	$JQuery("#loading").show();
	/*  alert("hits"); */
	var checkStrVal = ["false","true"];
	var startDateSlip = $JQuery("#startDateSlip").val();
	var endDateSlip = $JQuery("#endDateSlip").val();
	console.log(startDateSlip);
	console.log(endDateSlip);
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

	$JQuery.ajax({
		   type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   		
		   },
		   success:function(data){
			  /* alert("Success"); */
			  /* console.log(data); */
			 /*  $JQuery("#slipTrackers").html(""); */
			/*   $JQuery("#slipTrackers").html(""); */
			  /* $JQuery("#slip_tracking").html(""); */
		/* 	  $JQuery("#slip_tracking_wrapper").html(""); */
			 $JQuery("#slipTrackers").html(data);
			 
			  $JQuery("#slip_tracking").DataTable({
					bFilter: false,
				    bInfo: false,
					   dom: 'Bfrtip',
					   destroy: true,
					   buttons: [
					             {
					                 extend: 'excelHtml5',
					                 title: "Slip wise Report_"+ new Date(),
					                 text: 'Export as .xlxs'
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
	$JQuery("#loading_draft").show();
	var startDateDraft = $JQuery("#startDateDraft").val();
	var endDateDraft = $JQuery("#endDateDraft").val();
	console.log(startDateSlip);
	console.log(endDateSlip);
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
	$JQuery.ajax({
		type : "GET",
		   contentType : "application/json",
		   url : url,	 
		   dataType : 'html',
		   timeout : 100000,
		   beforeSend: function() {	    
		   		
		   },
		   success:function(data){
			  /*  	$JQuery("#draftTrackers").html("");
				  $JQuery("#draft_tracking").html("");
				  $JQuery("#draft_tracking_wrapper").html(""); */
				 $JQuery("#draftTrackers").html(data);
				 
				  $JQuery("#draft_tracking").DataTable({
						bFilter: false,
					    bInfo: false,
						   dom: 'Bfrtip',
						   destroy: true,
						   buttons: [
						             {
						                 extend: 'excelHtml5',
						                 title: "Draft wise Report_"+ new Date(),
						                 text: 'Export as .xlxs'
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

$JQuery("#compServiceReporting_").submit(function(event){
	event.preventDefault();
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
</style>