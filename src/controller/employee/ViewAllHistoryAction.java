package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import controller.main.Action;
import databean.DetailedTransactionBean;

public class ViewAllHistoryAction extends Action {
	private static final String VIEW_HISTORY_JSP = "emp-view-all-history.jsp";
	private static final String NAME = "employee-view-all-transactions.do";
	private Model model;

	public ViewAllHistoryAction(Model model) {

		this.model = model;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {

			ArrayList<DetailedTransactionBean> detailedTransactionBeans = model
			    .getTransactionHistory();
			Log.i("View all history", "" + detailedTransactionBeans.size());
			DetailedTransactionBean[] transactions = new DetailedTransactionBean[detailedTransactionBeans
			    .size()];
			detailedTransactionBeans.toArray(transactions);
			request.setAttribute("transactions", transactions);
			Log.i("View all history", "" + transactions.length);
			if (transactions == null || transactions.length == 0) {
				return VIEW_HISTORY_JSP;
			}

			return VIEW_HISTORY_JSP;
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
