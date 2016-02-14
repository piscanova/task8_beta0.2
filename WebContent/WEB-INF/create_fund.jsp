<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="employee-top.jsp" />
<div class="panel-heading">
	<h3 class="panel-title">Create Fund</h3>
</div>
<br>
<div class="panel-body">
<p>Input length should be no more than 15</p>
	<div>
		<c:if test="${errors !=null && fn:length(errors) > 0}">
			<div class="alert alert-warning">
				<h4>
					Error In Input 
				</h4>

				<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
				</c:forEach>
			</div>
		</c:if>
	</div>

	<div class="col-sm-12" style="margin-left: 0px">
		<!-- <form role="form">
			<div class="form-group">
				<label for="exampleInputEmail1">Fund Name</label><input type="text"
					class="form-control" name="name">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Ticker</label><input type="text"
					class="form-control" name="ticker">
			</div>
			<button type="submit" class="btn btn-primary">Create New
				Fund</button>
		</form> -->
		<form class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1" class="col-sm-3 control-label">Fund Name</label>
				<div class="col-sm-8">
					<input type="text" pattern="(.){1,15}" class="form-control" id="inputEmail3" name="name"
						placeholder="Please input fund name">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-3 control-label">Ticker(length no more than 5)</label>
				<div class="col-sm-8">
					<input type="text" pattern="(.){1,5}" name="ticker" class="form-control" id="inputPassword3"
						placeholder="Please input Ticker">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-10">
					<button type="submit" class="btn btn-primary">Create New
				Fund</button>
				</div>
			</div>
		</form>

	</div>
	<div class="panel-body">
            <c:choose>
                <c:when test="${funds ==null}">
                    <h5>Don't have any funds currently.</h5>
                </c:when>
                <c:otherwise>
                    <table class="table" id="fundTable">
                        <thead>
                            <tr>
                                <th>Fund name(You could click to see price history)</th>
                                <th>Ticker</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="fund" items="${funds}">
                                <tr>
                                    <td><a
                                        href="employee_research_fund.do?fundName=${fund.getName()}">${fund.getName() }</a></td>
                                    <td>${fund.getTicker() }</td>
                                </tr>
                                
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
</div>

