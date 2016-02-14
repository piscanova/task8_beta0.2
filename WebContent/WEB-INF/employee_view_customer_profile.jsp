<jsp:include page="employee-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel-heading">
	<h3 class="panel-title">Customer Profile 
	</h3>
</div>
<br>
<div class="panel-body">
	<div class="col-md-8 column">
<%-- 		<button  class="btn btn-default" type="button"><a  href="employee_view_customer_profile.do?userName=${customer.getUserName()}">View Profile</a></button> --%>
		<button  class="btn btn-default" type="button"><a href="employee-change-customer-pwd.do?userName=${customer.getUserName()}">Change
		Password</a></button>
		<button  class="btn btn-default" type="button"><a
			href="employee-view-transactions.do?userName=${customer.getUserName()}">Transaction
		History</a></button>
		<button  class="btn btn-default" type="button"><a
			href="employee-deposit-check.do?userName=${customer.getUserName()}">Deposit
			Check</a></button>
		</li>
		<h4>${customer.getUserName()} Personal Information </h4>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th width="50%"></th>
					<th>    </th>
			</thead>
			<tbody>
				<tr>
					<td>User Name</td>
					<td>${customer.getUserName()}</td>
				</tr>
				<tr>
					<td>First Name</td>
					<td>${customer.getFirstName()} </td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td>${customer.getLastName()}</td>
				</tr>
				<tr>
					<td>Address</td>
					<td>
						<c:if test="${customer.getAddrLine1() !=null}">${customer.getAddrLine1()}
						</c:if>
						<c:if
							test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
							<br></br>${customer.getAddrLine2()}
						</c:if>
						${customer.getCity()}<br>
						${customer.getState()}&nbsp;${customer.getZip()}
					</td>
				</tr>
				<tr>
					<td>Cash Balance($)</td>
					<td>
					${customer.getFormatCash()}
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<h4>${customer.getUserName()} Fund Information</h4>
			<c:choose>
				<c:when test="${shareList ==null}">
					<h5>${customer.getUserName()}  don't have any funds currently.</h5>
				</c:when>
				<c:otherwise>
					<h5>Last trading day: ${lastTransactionDay}</h5>
					<table class="table">
						<thead>
							<tr>
								<th>Fund name</th>
								<th>Ticker</th>
								<th style="text-align: right">Shares</th>
								<th style="text-align: right">Position($)</th>
								<!--<th style="text-align:right">Estimated value</th> -->
							</tr>
						</thead>
						<tbody>
							<c:forEach var="share" items="${shareList}">
								<tr>
									<td><a
										href="employee_research_fund.do?fundName=${share.getFundName()}">${share.getFundName() }</a></td>
									<td>${share.getFundSymbol() }</td>
									<td align="right">${share.getSharesThreeDecimal()}</td>
									<td align="right">${share.getAmountFormat()}</td>

								</tr>
								
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
	</div>
</div>

		