package controller.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import databean.FundPriceBean;

public class CustomerResearchFundAction extends Action {

	private final String CURRENT_OPERATION_JSP = "Graph.jsp";

	private FundDAO fundDAO;
	private CustomerDAO customerDAO;

	private FundPriceDAO priceDAO;

	public CustomerResearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		priceDAO = model.getFundPriceDAO();

	}

	@Override
	public String getName() {
		return "customer_research_fund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			String fundName = request.getParameter("fundName");
			if (fundName == null || fundName.length() == 0) {
				errors.add("Please, choose fund to buy it.");
				return "customer-result.jsp";
			}

			FundBean fund = fundDAO.getFundByName(fundName);
			if (fund == null) {
				request.setAttribute("message", "No funds");
				return "customer-result.jsp";
			}
			request.setAttribute("fund", fund);
			CustomerBean currentCustomer = (CustomerBean) request.getSession()
			    .getAttribute("customer");
			currentCustomer = customerDAO.read(currentCustomer.getId());
			request.setAttribute("customer", currentCustomer);
			int id = fund.getId();
			FundPriceBean[] pricesArr = priceDAO.getPrices(id);
			List<FundPriceBean> prices;
			if (pricesArr.length > 7) {
				ArrayList<FundPriceBean> arrayList = new ArrayList<FundPriceBean>(
				    Arrays.asList(pricesArr));
				prices = arrayList.subList(0, 7);
			} else {
				prices = new ArrayList<FundPriceBean>(Arrays.asList(pricesArr));
			}
			request.setAttribute("prices", prices);
			return CURRENT_OPERATION_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return CURRENT_OPERATION_JSP;
		} catch (NumberFormatException e) {
			errors.add(e.toString());
			return CURRENT_OPERATION_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
