package com.epam.esm.dto.pagination;

import java.util.Collections;
import java.util.List;

public class DtoPagination<T> {
	
	public DtoPagination() {

	}

	public List<T> getPage(List<T> pagebleList, int page, int size) {
		if (page <= 0) {
			throw new IllegalArgumentException("invalid page 'page' = " + page);
		}
		if (size <= 0) {
			throw new IllegalArgumentException("invalid size 'size' = " + size);
		}
		int startIndex = (page - 1) * size;
		if (pagebleList == null || pagebleList.size() <= startIndex) {
			return Collections.emptyList();
		}
		return pagebleList.subList(startIndex, Math.min(startIndex + size, pagebleList.size()));
	}
}
