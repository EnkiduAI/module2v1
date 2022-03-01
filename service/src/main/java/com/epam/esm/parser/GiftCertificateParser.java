package com.epam.esm.parser;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.esm.exception.ServiceException;

public class GiftCertificateParser {

	private static final String NAME_REGEX = "[\\w]{4,15}$";
	private static final String DESCRIPTION_REGEX = "[?!=\\s\\.\\,\\w]{30,160}";

	public static void parseToGiftCertificate(Map<String, Object> giftCertificate) throws ServiceException {
		String name = "";
		String description = "";
		int duration = 0;
		if (giftCertificate.containsKey("price")) {
			try {
				Integer.parseInt(giftCertificate.get("price").toString());
			} catch (NumberFormatException e) {
				throw new ServiceException("Price format is incorrect");
			}
		}
		if (giftCertificate.containsKey("name")) {
			name = giftCertificate.get("name").toString();
		}
		if (giftCertificate.containsKey("description")) {
			description = giftCertificate.get("description").toString();
		}
		if (giftCertificate.containsKey("duration")) {
			try {
				duration = Integer.parseInt(giftCertificate.get("duration").toString());
			} catch (NumberFormatException e) {
				throw new ServiceException("duration format is incorrect");
			}
		}
		if(duration < 0) {
			throw new ServiceException("duration cannot be negative");
		}
		Pattern namePattern = Pattern.compile(NAME_REGEX);
		Pattern descritpionPattern = Pattern.compile(DESCRIPTION_REGEX);

		Matcher nameMatcher = namePattern.matcher(name);
		Matcher descritionMatcher = descritpionPattern.matcher(description);

		if(!nameMatcher.matches()) {
			throw new ServiceException("Name format is incorrect");
		}
		
		if(!descritionMatcher.matches()) {
			throw new ServiceException("description format is incorrect");
		}
	}
}
