package com.epam.esm.persistence;

import java.util.List;

import com.epam.esm.model.entity.Tag;

public interface TagPersistence {
	int create(String name);
	int delete(int id);
	int unbindTag(int id);
	Tag findByName(String name);
	Tag findById(int id);
	List<Tag>findAll(int page, int limit);
}
