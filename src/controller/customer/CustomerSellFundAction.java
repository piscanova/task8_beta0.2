package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Log;
import util.Util;
import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import formbean.SellFundForm;

public class CustomerSellFundAction extends Action {
	private static final String FUND_TRANSACTION_JSP = "customersellfund.jsp";
	public static final String NAME = "customer-sell-fund.do";
	private static final String TAG = "CustomerSellFundAction";
	private static final String FORMAT_STRING = "#,##0.00";
	private static final String FORMAT_STRING_THREE_DECIMAL = "#,##0.000";
	private FormBeanFactory<SellFundForm> formBeanFactory;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private Model model;

	public CustomerSellFundAction(Model model) {
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		this.model = model;
		formBeanFactory = FormBeanFactory.getInstance(SellFundForm.class);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();
		request.setAttribute("errors", errorList);

		try {
			String fundIdString = request.getParameter("fundId");
			if (fundIdString == null || fundIdString.length() == 0) {
				errorList.add("Parameter is required: Please enter valid Fund Id");
				return "customer-result.jsp";
			}

			int fundId = Integer.valueOf(fundIdString);
			FundBean fund = fundDAO.read(fundId);
			if (fund == null) {
				errorList.add("Invalid Parameter: Fund ID");
				return "customer-result.jsp";
			}
			request.setAttribute("fund", fund);
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute(
			    "customer");
			customer = customerDAO.read(customer.getId());
			request.setAttribute("customer", customer);

			double[] amountValues = model.getAmount(customer.getId());
			double currentAmount = amountValues[0];
			double validAmount = amountValues[2];

			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, FORMAT_STRING));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, FORMAT_STRING));

			double[] shareValues = model.getShare(customer.getId(), fundId);
			double currentShare = shareValues[0] * 1.0 / 1000.0;
			double pendingShare = shareValues[1] * 1.0 / 1000.0;
			double validShare = shareValues[2] * 1.0 / 1000.0;
			request.setAttribute("currentShare",
			    Util.formatNumber(currentShare, FORMAT_STRING_THREE_DECIMAL));
			request.setAttribute("pendingShare",
			    Util.formatNumber(pendingShare, FORMAT_STRING_THREE_DECIMAL));
			Log.i(TAG, "pendingShare: " + pendingShare);
			request.setAttribute("validShare",
			    Util.formatNumber(validShare, FORMAT_STRING_THREE_DECIMAL));

			SellFundForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return FUND_TRANSACTION_JSP;
			}

			errorList.addAll(form.getValidationErrors());
			if (errorList.size() != 0) {

				return FUND_TRANSACTION_JSP;
			}
			model.commitSellFund(fundId, customer.getId(),
			    (long) (form.getShareValue() * 1000));

			request.setAttribute("message", "Thanks, we have accepted your request.");
			return "customer_success.jsp";
		} catch (FormBeanException e) {
			errorList.add(e.toString());
			return FUND_TRANSACTION_JSP;
		} catch (RollbackException e) {
			errorList.add(e.getMessage());
			return FUND_TRANSACTION_JSP;
		} catch (NumberFormatException e) {
			errorList.add("invalid Fund ID");
			return "customer-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
