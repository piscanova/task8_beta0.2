package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;

import com.google.gson.JsonObject;

import controller.main.Action;

public class DepositCheckAction extends Action {

	public static final String NAME = "depositCheck";
	private Model model;

	public DepositCheckAction(Model model) {

		this.model = model;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public JsonObject perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		JsonObject innerObject = new JsonObject();
		try {
			String userName = request.getParameter("username");
			String amount = request.getParameter("cash");
			Log.i("DC Action", "user name " + userName);
			Log.i("DC Action", "cash " + amount);
			if (userName == null || userName.length() == 0 || amount == null
					|| amount.length() == 0) {
				innerObject.addProperty("message",
						"I’m sorry, there was a problem depositing the money");
				return innerObject;
			}

			model.commitDepositCheckTransaction(userName, amount);
			request.setAttribute("message",
					"The account has been successfully updated");

			innerObject.addProperty("message",
					"The account has been successfully updated");

			return innerObject;
		} catch (RollbackException e) {

			errors.add(e.toString());
			innerObject.addProperty("message",
					"I’m sorry, there was a problem depositing the money");
			return innerObject;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
