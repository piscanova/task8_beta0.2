package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import controller.main.Action;
import databean.CustomerBean;
import formbean.RequestCheckForm;

public class CustomerRequestCheckAction extends Action {

	private static final String FORMAT_STRING = "###,###,###,###,###,##0.00";
	private static final String ACTION_NAME = "customer-request-check.do";
	private static final String REQUES_CHECK_JSP = "customer-request-check.jsp";
	private FormBeanFactory<RequestCheckForm> formBeanFactory;
	private CustomerDAO customerDAO;
	private Model model;

	public CustomerRequestCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
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
			RequestCheckForm form = formBeanFactory.create(request);
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute(
			    "customer");
			customer = customerDAO.read(customer.getId());
			request.setAttribute("customer", customer);
			double[] arr = model.getAmount(customer.getId());
			double currentAmount = arr[0] / 100;
			double validAmount = arr[2] / 100;
			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, FORMAT_STRING));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, FORMAT_STRING));

			if (!form.isPresent()) {
				return REQUES_CHECK_JSP;
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return REQUES_CHECK_JSP;
			}
			model.commitRequestCheck(customer.getId(),
			    ((long)(Double.parseDouble(form.getAmount()) * 100)));
			request
			    .setAttribute("message",
			        "Thanks, we have accepted your request. Please wait until the Transition Day");
			return "customer_success.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return REQUES_CHECK_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return REQUES_CHECK_JSP;
		} catch (NumberFormatException e) {
			errors.add(e.toString());
			return REQUES_CHECK_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
