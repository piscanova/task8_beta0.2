<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
	<span style="font-weight:bold;font-size:16px">Profile</span>
	<br>
	<span style="font-weight:bold">First Name:&nbsp;${customer.getFirstName()}
		<br>
	<span>Last Name:&nbsp;${customer.getLastName()}</span>
		<br>
	<span>
		<c:if test="${customer.getAddrLine1() !=null}">Address:&nbsp;${customer.getAddrLine1()}
			<br>
		</c:if>
		<c:if test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
		
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${customer.getAddrLine2()}
			<br></c:if>
	</span>
	<span>City:&nbsp;${customer.getCity()}&nbsp;State:&nbsp;${customer.getState()}&nbsp;</span>	<br>
	<span>Zip:&nbsp;${customer.getZip()}</span>	<br>
	<span>Cash Balance</span>	<br>
<%-- 	<dd><fmt:formatNumber type="currency" value="${currentAmount}" /></dd> --%>
	<span>$ ${currentAmount}</span>	<br>
	<span>Available Balance</dt></span>	<br>
<%-- 	<dd><fmt:formatNumber type="currency" value="${validAmount}" /> --%>
	<span>$ ${validAmount}</span>	<br>
</div>