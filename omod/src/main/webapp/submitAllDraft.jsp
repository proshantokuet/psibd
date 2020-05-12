<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="draftTracking"> </div>
<div class="form-content">
	<div class="row">
		<div class="col-md-3">
			<div class="form-group">
                   <label> ${ no_slip_draft} </label> &nbsp;&nbsp; No of Slips in Draft
               </div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
                   <label> ${ total_payable_draft} </label> &nbsp;&nbsp; Total Payable in Draft
               </div>
		</div>
		<c:if test="${showSubmitDraft eq 2}">
			<div class="col-md-4">
				<c:if test="${not empty draftReport }">
					<div class="form-group">
						<button onclick="submitAllDraft()">Submit All Draft</button>
					</div>
				</c:if>
			</div>
		</c:if>
		<div class="col-md-3">
			<div class="form-group">
                   <label> ${msg} </label>
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
                 <th>E-Slip No</th>
                 <th>Money Receipt Date</th>
                 <th>Patient Name</th>
                <th>Phone</th>
                <th>Wealth Class</th>
                <th>Service Point</th>
                <th>Total Service Contact</th>
                <th>Total Amount</th>
                <th>Discount</th>
                <th>Payable Amount</th>
               <!--  <th>Action</th>  -->
            </tr>
        </thead>
        <tbody>
          <% int sl_d = 0; %>
        	<c:if test="${not empty draftReport }">
				<c:forEach var="report" items="${ draftReport }">
			        <tr>
			        	
			        	<td><%=++sl_d%></td>
			              <td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${ report.slip_no }</a></td>
			              <td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${ report.eslipNo }</a></td>	             
			        	 <td>${ report.slip_date }</td> 
			        	 <td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${ report.patient_name }</a></td>
			            <td><a href="/bahmni/clinical/index.html#/default/patient/${ report.patient_uuid }/dashboard" target="_blank">${ report.phone }</a></td>
			            <td>${ report.wealth_classification }</td>
			            <td>${ report.service_point }</td>
			            <td>${ report.total_service_contact }</td>
			            <td>${ report.total_amount }</td>
			            <td>${ report.discount }</td>
			            <td>${ report.net_payable }</td>
			            <%-- <td>${ report.slip_link }</td> --%>  
			        </tr>
		       </c:forEach>
		    </c:if>
        </tbody>
	</table>
</div>