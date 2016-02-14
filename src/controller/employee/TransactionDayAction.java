package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.google.gson.JsonObject;

import controller.main.Action;

public class TransactionDayAction extends Action {

	private static final String NAME = "transitionDay";
	private Model model;

	public TransactionDayAction(Model model) {
		this.model = model;

	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public JsonObject perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		JsonObject innerObject = new JsonObject();
		try {

			model.updatePrices();

			innerObject.addProperty("message",
					"The fund prices have been recalculated");

			return innerObject;
		} catch (RollbackException e) {

			errors.add(e.toString());
			innerObject
					.addProperty("message", "I’m sorry, there was a problem");
			return innerObject;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
