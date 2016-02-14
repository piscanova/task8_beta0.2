<jsp:include page="customer-top.jsp" />
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<div class="panel-heading">
		<h3 class="panel-title"><b>Transaction history</b></h3>
	</div>
			
			<jsp:include page="template-result.jsp" />
			
			<div class="panel-body">
		<div class="col-sm-12">
			
			<c:choose>
				<c:when test="${transactions ==null}">
					<h5>No transactions.</h5>
				</c:when>
				<c:otherwise>
					<table class="table table-striped" style="display: block;  height: 500px; overflow-y: scroll">
						<thead >
							<tr >
								<th width="15%">Date</th>
								<th width="10%" >Fund name</th>
								<th width="15%" style="text-align:right">Shares</th>
								<th width="15%" style="text-align:right">Price($)</th>
								<th width="25%"style="text-align:right">Amount($)</th>
								<th >Operation</th>
								<th >Status</th>
							</tr>
						</thead>
						<tbody >
							<c:forEach var="transaction" items="${transactions}">
								<tr>
									<td >${transaction.getExecuteDay()}</td>
									<td >${transaction.getFundName()}</td>
									<td align="right">
									
									${transaction.getSharesThreeDecimal()}
									</td>

									<td align="right">

									${transaction.getPriceTwoDecimal()}
									</td>
									<td align="right">
									${transaction.getAmountTwoDecimal()}

									</td>

									<td >${transaction.getTransactionType()}</td>
									<td >${transaction.getStatus()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-4 column"></div>
	</div>


