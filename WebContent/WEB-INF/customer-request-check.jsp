<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<jsp:include page="customer-top.jsp" />

	<div class="panel-heading">
		<h3 class="panel-title"><b>Request Check</b></h3>
	</div>
	<jsp:include page="template-result.jsp" />
	<br>
	<div class="panel-body">
		<div class="col-sm-10">
			<p>
				Your Current Balance: $${currentAmount} 
			</p>
			<p>
				Your Valid Balance:  $${validAmount}
			</p>

			<br>
			<form role="form">
			<div class="form-group">
				 <label
					class="col-sm-2 control-label"> Amount($): 
					</label>					
				<div class="col-sm-10">
					<input type="text" pattern="\d+(\.\d{1,2})?" class="form-control" name="amount">
				</div>
			</div>
			<br></br>
				<button type="submit" class="btn btn btn-primary" name="action"
					value="request">Submit</button>
			</form>
			<p style="color: grey">*Please enter a positive number. </p>
		    <p style="color: grey">The decimal places should be no more than 2.</p>
			<br></br>
			<br>
		</div>
	</div>
