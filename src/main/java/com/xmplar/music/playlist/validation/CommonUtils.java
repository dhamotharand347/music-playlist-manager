package com.xmplar.music.playlist.validation;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
	
	private CommonUtils() {}
	
	public static List<String> isValidUsername(String username) {

		List<String> error = new ArrayList<>();

		if (username.isEmpty() || username == null) {
			error.add("Username cannot be empty.");
		}
		if (username.length() < 4 || username.length() > 25) {
			error.add("Username should contain atleast 4 and atmost 25 characters.");
		}
		if (username.matches(".*\\s{2,}.*")) {
			error.add("Username should not contain more than one whitespace between words.");
		}
		if (!username.matches("^[a-zA-Z0-9_]*$")) {
			error.add("Username should not contain special characters except underscore.");
		}
		
		return error;
	}

	public List<String> isValidPassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            errors.add("Password cannot be empty.");
        }
        if (password.length() < 6) {
            errors.add("Password should contain at least 6 characters.");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            errors.add("Password should contain at least one special character.");
        }
        if (!password.matches(".*\\d.*")) {
            errors.add("Password should contain at least one digit (number).");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password should contain at least one uppercase letter.");
        }

        return errors;
    }
}
