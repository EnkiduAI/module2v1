package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.TagService;
import com.epam.esm.persistence.impl.TagPersistenceImpl;

@Service
public class TagServiceImpl implements TagService {

	private TagPersistenceImpl tagDao;

	@Autowired
	public TagServiceImpl(TagPersistenceImpl tagDao) {
		this.tagDao = tagDao;
	}

	/**
	 * Creates the tag.
	 *
	 * @param name the name
	 * @return the tag
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public Tag createTag(String name) throws ServiceException {
		int id = tagDao.create(name);
		return tagDao.findById(id);
	}

	/**
	 * Find tag by id.
	 *
	 * @param id the id
	 * @return the tag
	 * @throws ServiceException the service exception
	 */
	@Override
	public Tag findTagById(int id) throws ServiceException {
		return tagDao.findById(id);
	}

	/**
	 * Find all tags.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	@Override
	public List<Tag> findAllTags() throws ServiceException {
		return tagDao.findAll();
	}

	/**
	 * Delete tag.
	 *
	 * @param tagId the tag id
	 * @return the tag
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public Tag deleteTag(int tagId) throws ServiceException {
		tagDao.unbindTag(tagId);
		Tag deleted = tagDao.findById(tagId);
		if (tagDao.delete(tagId) > 0) {
			return deleted;
		} else {
			throw new ServiceException("Method deleteTag at ProjectServiceImpl has been interrupted with error");
		}
	}
}
