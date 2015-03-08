package controllers;

import helpers.RegistrationRequest;

import java.io.IOException;

import models.Account;

public class AccountsController extends Controller {

	
	public AccountsController() {
		
	}
	
	public RegistrationRequest register(String username, String password, String confirmPassword) throws IOException {
		RegistrationRequest request = new RegistrationRequest(username, password, confirmPassword);
		return Account.register(request);
	}
	
}
