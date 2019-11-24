
<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="slipTracking"> </div>
  <div class="form-content">
      <div class="row">
          <div class="col-md-3">
              <div class="form-group">
                  <label> ${dashboard_patients_served } </label> &nbsp;&nbsp; Patients Served
              </div>

          </div>
          <div class="col-md-3">
              <div class="form-group">
                  <label for="Service Code">${dashboard_revenue }</label>
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
                <!-- <th>Action</th>  -->
            </tr>
        </thead>
        <tbody>
        <% int sl = 0; %>
        	<c:if test="${not empty slipReport }">
				<c:forEach var="report" items="${ slipReport }">
			        <tr>
			        	
			        	<td><%=++sl%></td>
			              <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.slip_no }</a></td>	             
			        	 <td>${ report.slip_date }</td> 
			        	 <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.patient_name }</a></td>
			            <td><a href="/bahmni/clinical/index.html#/default/patient/e3a6a9f3-3b5c-4861-826d-ee46a4efbba2/dashboard" target="_blank">${ report.phone }</a></td>
			            <td>${ report.wealth_classification }</td>
			            <td>${ report.service_point }</td>
			            <td>${ report.total_amount }</td>
			            <td>${ report.discount }</td>
			            <td>${ report.net_payable }</td>
			           <%--  <td>${ report.slip_link }</td> --%>  
			             
			               <%-- <c:forEach var="report_t" item=${report }>
			             	<td>${report_t }</td>
			             </c:forEach>   --%>
			        </tr>
		       </c:forEach>
		    </c:if>
        </tbody>
</table>
</div>    