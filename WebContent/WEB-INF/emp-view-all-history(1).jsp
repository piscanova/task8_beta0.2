<jsp:include page="employee-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class="panel-heading">
	<h3 class="panel-title">All Transaction History</h3>
</div><jsp:include page="template-result.jsp" /><br>
<div class="panel-body">
	<div class="col-md-12 column">
		<h3>Transaction History </h3>
		<c:choose>
 			<c:when test="${transactions ==null}">
				<h5>No transactions.</h5>
			</c:when>
			<c:otherwise>
				<table class="table table-striped" id="all">
					<thead>
						<tr>
							<th>Date</th>
							<th>User Name</th>
							<th>Fund name</th>
							<th style="text-align: right">Shares</th>
							<th style="text-align: right">Price</th>
							<th style="text-align: right">Amount</th>
							<th></th>
							<th>Type</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="transaction" items="${transactions}">
							<tr>
								<td>${transaction.getExecuteDay()}</td>
								<td>${transaction.getUserName()}</td>
								<td>${transaction.getFundName()}</td>
								<td align="right">${transaction.getShares()}</td>
								<td align="right">${transaction.getPrice()}</td>
								<td align="right">${transaction.getAmount()}</td>
								<td></td>
								<td>${transaction.getTransactionType()}</td>
								<td>${transaction.getStatus()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		<br>
		<%--         <table class="table table-striped table-hover" id="all">
            <thead>
                <tr>
                    <th width="15%">Customer ID</th>
                    <th>Fund Ticker</th>
                    <th>Fund Name</th>

                    <th style="text-align: right">Current Price</th>
                    <th width="30%">Update Price
                    <br>
                     (at most 2 decimal digits)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="fund" items="${funds}">
                    <tr>
                        <td>${fund.getDate()}</td>
                        <td>${fund.getTicker()}</td>
                        <td>${fund.getFundName()}</td>


                        <td align="right">${fund.getPrice()}</td>
                        <td><input type="hidden" name="ids"
                            value="${fund.getFundId()}" /> <input type="text"
                            name="prices" value="1.00" placeholder="1.00"
                            pattern="\d+(\.\d{1,2})?" class="form-control" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table> --%>

		<br> <br>

	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="${customer.getUserName() }" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">View Profile</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th width="50%">Key</th>
							<th>Value</th>
					</thead>
					<tbody>
						<tr>
							<td>Customer Name</td>
							<td>${customer.getFirstName()}&nbsp;${customer.getLastName()}</td>
						</tr>
						<tr>
							<td>Address</td>
							<td><c:if test="${customer.getAddrLine1() !=null}">${customer.getAddrLine1()}
                                </c:if> <c:if
									test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
									<br></br>${customer.getAddrLine2()}
                                </c:if> ${customer.getCity()}<br>
								${customer.getState()}&nbsp;${customer.getZip()}</td>
						</tr>
						<tr>
							<td>Cash Balance</td>
							<td>${customer.getCash()}</td>
						</tr>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>



