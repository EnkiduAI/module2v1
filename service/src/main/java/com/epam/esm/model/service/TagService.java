package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;

public interface TagService {

	Tag createTag(String name) throws ServiceException;

	Tag findTagById(int id) throws ServiceException;

	List<Tag> findAllTags() throws ServiceException;

	Tag deleteTag(int tagId) throws ServiceException;
}