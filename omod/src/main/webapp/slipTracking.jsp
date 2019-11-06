
<%@ include file="/WEB-INF/template/include.jsp"%>

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
                <th>Action</th> 
            </tr>
        </thead>
        <tbody>
        	<c:if test="${not empty slipReport }">
				<c:forEach var="report" items="${ slipReport }">
			        <tr>
			        	<td>#</td>
			              <td>${ report.slipNo }</td>	             
			        	 <td>${ report.slipDate }</td> 
			        	 <td>${ report.patientName }</td>
			            <td>${ report.phone }</td>
			            <td>${ report.wealthClassification }</td>
			            <td>${ report.servicePoint }</td>
			            <td>${ report.totalAmount }</td>
			            <td>${ report.discount }</td>
			            <td>${ report.netPayable }</td>
			            <td>${ report.slipLink }</td>  
			             
			               <%-- <c:forEach var="report_t" item=${report }>
			             	<td>${report_t }</td>
			             </c:forEach>   --%>
			        </tr>
		       </c:forEach>
		    </c:if>
        </tbody>
</table>
    