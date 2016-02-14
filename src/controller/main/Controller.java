package controller.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Model;

import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;

import com.google.gson.JsonObject;

import controller.customer.ChangeCustomerPasswordAction;
import controller.customer.CustomerBuyFundAction;
import controller.customer.CustomerRequestCheckAction;
import controller.customer.CustomerResearchFundAction;
import controller.customer.CustomerSearchFundAction;
import controller.customer.CustomerSellFundAction;
import controller.customer.CustomerViewAccountAction;
import controller.customer.CustomerViewFund;
import controller.customer.CustomerViewTransactionHistoryAction;
import controller.employee.CreateCustomerAccountAction;
import controller.employee.CreateEmployeeAccountAction;
import controller.employee.CreateFundAction;
import controller.employee.DepositCheckAction;
import controller.employee.EmployeeResearchFundAction;
import controller.employee.EmployeeViewCustomerProfileAction;
import controller.employee.ResetCustomerPwdAction;
import controller.employee.ResetEmployeePwdAction;
import controller.employee.SearchCustomerAction;
import controller.employee.TransactionDayAction;
import controller.employee.ViewAllHistoryAction;
import controller.employee.ViewCustomerTransactionHistoryAction;
import databean.EmployeeBean;

public class Controller extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1561542386870494042L;
	private static final String TAG = "Controller";

	@Override
	public void init() throws ServletException {

		Model model;
		try {
			model = new Model(getServletConfig());

			try {
				if (model.getEmployeeDAO().getCount() == 0) {
					createAdminAccount(model);
				}
			} catch (RollbackException e) {
				e.printStackTrace();
			} finally {
				if (Transaction.isActive()) {
					Transaction.rollback();
				}
			}

			// admin
			Action.add(new ResetEmployeePwdAction(model));
			Action.add(new ResetCustomerPwdAction(model));
			Action.add(new CreateCustomerAccountAction(model));
			Action.add(new CreateEmployeeAccountAction(model));
			Action.add(new CreateFundAction(model));
			Action.add(new SearchCustomerAction(model));
			Action.add(new DepositCheckAction(model));
			Action.add(new ViewCustomerTransactionHistoryAction(model));
			Action.add(new EmployeeViewCustomerProfileAction(model));
			Action.add(new ViewAllHistoryAction(model));

			// customer
			Action.add(new CustomerSearchFundAction(model));
			Action.add(new CustomerSellFundAction(model));
			Action.add(new CustomerViewAccountAction(model));
			Action.add(new CustomerViewTransactionHistoryAction(model));
			Action.add(new TransactionDayAction(model));
			Action.add(new CustomerRequestCheckAction(model));
			Action.add(new CustomerBuyFundAction(model));
			Action.add(new ChangeCustomerPasswordAction(model));
			Action.add(new CustomerResearchFundAction(model));
			Action.add(new CustomerViewFund(model));
			Action.add(new EmployeeResearchFundAction(model));

			// logout
			Action.add(new LoginAction(model));
			Action.add(new LogoutAction());
			Log.i(TAG, "finish adding");
		} catch (DAOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		Log.i("perform action", action);
		JsonObject responseJsonObject = null;
		responseJsonObject = Action.perform(action, request);
		Log.i(TAG, action);
		PrintWriter out = response.getWriter();
		out.print(responseJsonObject);
		out.flush();

	}

	private String getActionName(String path) {
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}

	private void createAdminAccount(Model model) throws RollbackException {
		EmployeeBean admin = new EmployeeBean();
		admin.setUserName("admin");
		admin.setFirstName("Cindy");
		admin.setLastName("Li");
		admin.setPassword("hi");
		model.getEmployeeDAO().create(admin);
	}

}
