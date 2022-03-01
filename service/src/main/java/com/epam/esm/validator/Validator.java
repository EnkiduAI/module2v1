package com.epam.esm.validator;

public class Validator {

	public boolean isPageble(int page, int size) {
		if((page>0 && size > 0) && (page <= 2147483647 && size <= 2147483647)){
				return true;
		}
		return false;
	}
}
