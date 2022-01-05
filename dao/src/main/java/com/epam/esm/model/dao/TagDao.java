package com.epam.esm.model.dao;

import java.util.List;

import com.epam.esm.model.entity.Tag;

public interface TagDao {
	int create(String name);
	int delete(int id);
	int unbindTag(int id);
	Tag findById(int id);
	List<Tag>findAll();
}
