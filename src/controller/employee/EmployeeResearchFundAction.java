package controller.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.FundPriceDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import controller.main.Action;
import databean.FundBean;
import databean.FundPriceBean;

public class EmployeeResearchFundAction extends Action {

	private final String CURRENT_OPERATION_JSP = "graph_for_employee.jsp";

	private FundDAO fundDAO;
	private FundPriceDAO priceDAO;

	public EmployeeResearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getFundPriceDAO();

	}

	@Override
	public String getName() {
		return "employee_research_fund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			String fundName = request.getParameter("fundName");
			if (fundName == null || fundName.length() == 0) {
				errors.add("Missing fund name");
				return "employee-result.jsp";
			}
			FundBean fund = fundDAO.getFundByName(fundName);
			request.setAttribute("fund", fund);
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
