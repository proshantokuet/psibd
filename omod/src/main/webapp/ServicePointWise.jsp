<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>	  <thead>
	        <tr>
	            <th>Code</th>
	            <th>Item</th>
	            <th>Clinic</th>
	            <th>Satelite</th>
	            <th>CSP</th>
	            <th>Total</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="report" items="${ reports }">
	        <tr>
	        	<td>${ report.code }</td>
	            <td>${ report.item }</td>
	            <td>${ report.clinic }</td>
	            <td>${ report.satelite }</td>
	            <td>${ report.csp }</td>
	            <td>${ report.total }</td>
	        </tr>
	       </c:forEach>
	        <!-- <tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr>
	        <tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr>
	        <tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr>
	        <tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr><tr>
	            <td>#Id</td>
	            <td>Clinic Name</td>
	            <td>Clinic ID</td>
	            <td>Category</td>
	            <td>Address</td>
	            <td>Action</td>
	        </tr> -->
	    </tbody>

