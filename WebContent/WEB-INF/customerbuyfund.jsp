<jsp:include page="customer-top.jsp" />

	<div class="panel-heading">
		<h3 class="panel-title"><b>Buy Fund</b></h3>
	</div>
	<jsp:include page="template-result.jsp" />
	<div class="panel-body">
		<p>
<% 
			String currentAmount = request.getAttribute("currentAmount").toString();
			out.print("Your Cash Balance: $ <b>" + currentAmount + "</b>");
			
%>
		</p>

		<p>
<% 
            String validAmount = request.getAttribute("validAmount").toString();
			out.print("Your Available Balance: $ <b>" + validAmount + "</b>");
%>
		</p>

		<br>
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label"> Fund Name: </label>
				<div class="col-sm-10">
					<a href="customer_research_fund.do?fundName=${fund.getName()}">${fund.getName()}</a>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" style="width: 30%" name="fundId"
					value="${fund.getId()}"> <label
					class="col-sm-2 control-label" > Amount(USD): </label>
					
				<div class="col-sm-8">
					<input type="text" pattern="\d+(\.\d{1,2})?" class="form-control" name="amount" > 
				</div>
			</div>
			<br>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary" name="action"
						value="buy">Buy</button>
				</div>
			</div>
		</form>
		<p style="color: grey">*The range of amount is $1 - $1,000,000. </p>
		<p style="color: grey">The decimal places should be no more than 2.</p>
		<br></br>
		<br>
	</div>
