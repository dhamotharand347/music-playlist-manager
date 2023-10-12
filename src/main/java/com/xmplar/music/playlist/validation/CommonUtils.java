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
	
	public static String calculateTotalDuration(String existingDuration, String newSongDuration) {
	    // Split the existing and new durations into hours, minutes, and seconds
	    String[] existingParts = existingDuration.split(":");
	    String[] newSongParts = newSongDuration.split(":");

	    int existingHours = 0;
	    int existingMinutes = 0;
	    int existingSeconds = 0;

	    if (existingParts.length == 2) {
	        // Handle "MM:SS" format
	        existingMinutes = Integer.parseInt(existingParts[0]);
	        existingSeconds = Integer.parseInt(existingParts[1]);
	    } else if (existingParts.length == 3) {
	        // Handle "HH:MM:SS" format
	        existingHours = Integer.parseInt(existingParts[0]);
	        existingMinutes = Integer.parseInt(existingParts[1]);
	        existingSeconds = Integer.parseInt(existingParts[2]);
	    } else {
	        // Handle invalid format gracefully or return an error
	        return "Invalid Duration Format";
	    }

	    int newSongMinutes = Integer.parseInt(newSongParts[0]);
	    int newSongSeconds = Integer.parseInt(newSongParts[1]);

	    // Calculate the total hours, minutes, and seconds
	    int totalHours = existingHours;
	    int totalMinutes = existingMinutes + newSongMinutes;
	    int totalSeconds = existingSeconds + newSongSeconds;

	    // Handle the case where seconds or minutes exceed 59
	    if (totalSeconds >= 60) {
	        totalMinutes += totalSeconds / 60;
	        totalSeconds %= 60;
	    }

	    if (totalMinutes >= 60) {
	        totalHours += totalMinutes / 60;
	        totalMinutes %= 60;
	    }

	    // Format the total duration based on the presence of hours
	    if (totalHours > 0) {
	        return String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds);
	    } else {
	        return String.format("%02d:%02d", totalMinutes, totalSeconds);
	    }
	}

}
