package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class CreateFundForm extends FormBean {

	private static final int MAX_FUND_TICKER_LENGTH = 5;
	private String name;
	private String ticker;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);
		if (errors.size() > 0) {
			return errors;
		}
		if (Util.hasInvalidSymbol(name) || Util.hasInvalidSymbol(ticker)) {
			errors.add("please don't input brackets, slash and \"&\".");
			return errors;
		}
		if (name.trim().length() > 15 || ticker.trim().length() > 15) {
			errors.add("input length should be less than 15 (consist of not only space)");
		}
		if (errors.size() > 0) {
			return errors;
		}

		if (ticker.length() > MAX_FUND_TICKER_LENGTH || !ticker.matches("[a-zA-Z]+")) {
			errors.add("Fund ticker should be shorter than 5 characters and only consists of letters");
		}
		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(name, errors, "Please input your fund name (consist of not only spaces).");
		checkEmptyInput(ticker, errors, "Please input your fund ticker (consist of not only spaces).");
	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {
		if (input == null || input.trim().length() == 0) {
			errors.add(errMsg);
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Util.sanitizeInputString(name);
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = Util.sanitizeInputString(ticker);
	}

}
