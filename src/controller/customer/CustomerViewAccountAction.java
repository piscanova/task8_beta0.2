package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;

import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import util.Util;
import controller.main.Action;
import databean.CustomerBean;

import databean.PositionBean;
import databean.ShareInformationBean;

public class CustomerViewAccountAction extends Action {
	private PositionDAO positionDAO;
	
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
	private static final String FORMAT_STRING = "#,##0.00";
	private Model model;

	public CustomerViewAccountAction(Model model) {
		this.model = model;
		positionDAO = model.getPositionDAO();
		
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		
	}

	public String getName() {
		return "customer_viewaccount.do";
	}

	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {

			CustomerBean customer = (CustomerBean) request.getSession(false)
			    .getAttribute("customer");
			customer = customerDAO.read(customer.getId());
			double currentAmount = Double.parseDouble(customer.getCashTwoDecimal());
			double pendingAmount = (double) transactionDAO.getPendingAmount(customer
			    .getId()) / 100.0;
			double validAmount = currentAmount - pendingAmount;
			Log.i("Customer View Account Action", "valid Amount " + validAmount);
			Log.i("Customer View Account Action", "current Amount " + currentAmount);
			Log.i("Customer View Account Action", "pending Amount " + pendingAmount);
			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, FORMAT_STRING));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, FORMAT_STRING));
			System.out.println("customer id: " + customer.getId());
			PositionBean[] positionList = positionDAO
			    .getPositionsByCustomerId(customer.getId());
			if (positionList == null) {
				return "customerviewaccount.jsp";
			}
			ShareInformationBean[] shareList = model.getShares(customer.getId());
			String lastTransactionDay = null;
			
			lastTransactionDay = transactionDAO.getUsersLastTransactionDay(customer.getId());
			request.setAttribute("lastTransactionDay", lastTransactionDay);
			request.setAttribute("shareList", shareList);

			return "customerviewaccount.jsp";
		} catch (RollbackException e) {

			errors.add(e.getMessage());
			return "customer-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
