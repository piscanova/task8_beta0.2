

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
	<h3 class="panel-title">Deposit Check</h3>
</div><jsp:include page="template-result.jsp" /><br>
<div class="panel-body">
	<div class="col-md-8 column">
		<h3>
			Deposit check for  <a
                                                    href="employee_view_customer_profile.do?userName=${customer.getUserName()}">${customer.getUserName()}
                                                        </a>
		</h3>
		<br>

		<form role="form form-inline">
			<div class="form-group">
				<label class="sr-only" for="exampleInputAmount">Amount (in
					dollars)</label>
				<div class="input-group">
					<div class="input-group-addon">$</div>
					<input type="hidden" name="userName"
						value="${customer.getUserName()}" class="form-control"> <input
						type="text"  class="form-control"
						name="amount" value="1.00" placeholder="at most 2 decimal digits">
					<!-- <div class="input-group-addon">.00</div> -->
				</div>
				<div class="input-group" style="margin-top: 20px">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>
		<p style="color: grey">*The range of deposit check should be $0.01 - $1,000,000. </p>
		<p style="color: grey">The decimal places should be no more than 2.</p>
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



