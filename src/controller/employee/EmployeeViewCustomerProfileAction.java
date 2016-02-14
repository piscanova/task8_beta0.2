package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;

import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import controller.main.Action;
import databean.CustomerBean;

import databean.PositionBean;
import databean.ShareInformationBean;

public class EmployeeViewCustomerProfileAction extends Action {
	private static final String ACTION_NAME = "employee_view_customer_profile.do";
	private static final String VIEW_PROFILE_JSP_NAME = "employee_view_customer_profile.jsp";
	private PositionDAO positionDAO;
	
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private Model model;
	

	public EmployeeViewCustomerProfileAction(Model model) {
		positionDAO = model.getPositionDAO();
		
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		this.model = model;

	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String userName = request.getParameter("userName");
			if (userName == null) {
				errors.add("Missing user name");
				return "employee-result.jsp";
			}

			CustomerBean customer = customerDAO.getCustomerByUserName(userName);
			if (customer == null) {
				errors.add("Didn't find customer with userName" + userName);
				return "employee-result.jsp";
			}

			request.setAttribute("customer", customer);
			PositionBean[] positionList = positionDAO
				    .getPositionsByCustomerId(customer.getId());
				if (positionList == null) {
					return  VIEW_PROFILE_JSP_NAME;
				}
				ShareInformationBean[] shareList = model.getShares(customer.getId());
				String lastTransactionDay = null;
				
				lastTransactionDay = transactionDAO.getUsersLastTransactionDay(customer.getId());
				request.setAttribute("lastTransactionDay", lastTransactionDay);
				request.setAttribute("shareList", shareList);

			return VIEW_PROFILE_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "employee-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
