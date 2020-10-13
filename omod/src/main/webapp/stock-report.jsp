
<%@ include file="/WEB-INF/template/include.jsp"%>
<div class="form-content" id="stockReportTtile"></div>
<div style="overflow:auto;">
		<br/>
		 <table id="stockReportTable" class="display" border="1">
			 	<thead>
					<tr>
						<th rowspan="2">Sl</th>
						<th rowspan="2">Clinic Name</th>
						<th rowspan="2">Clinic Code</th>
						<th rowspan="2">Product Name</th>
						<th rowspan="2">Category</th>
						<th rowspan="2">Brand</th>
						<th rowspan="2">Nearest Expiry Date</th>
						<th id="monthHeader" colspan="5">Monthly Report</th>
					</tr>
					<tr>
					 	<th>Start Balance</th>
					 	<th>Sales</th>
				 	    <th>Adjust</th>
					 	<th>Supply</th>
					 	<th>End Balance</th>
					</tr>
				</thead>
			    <tbody>
        <% int sl = 0; %>
				<c:forEach var="report" items="${ stockReport }">
					
			        <tr>
			        	
			        	<td><%=++sl%></td>
							<td>${report.clinicname }</td>
							<td>${report.clinic_id }</td>
							<td>${report.productname }</td>
							<td>${report.category }</td>
							<td>${report.brandname }</td>
							<td>${report.earliestExpiry }</td>
							<td>${report.starting_balance }</td>
							<td>${report.sales }</td>
							<td>${report.adjust }</td>
							<td>${report.supply }</td>
							<td>${report.endBalance }</td>
			        </tr>
		       </c:forEach>
        </tbody>
		
		</table>
	</div>  