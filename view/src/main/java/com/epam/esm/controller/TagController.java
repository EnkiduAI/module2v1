package com.epam.esm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.impl.ProjectServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/tags")
public class TagController {

	/** Converter. */
	private DtoConverter converter = DtoConverter.getInstance();

	/** Service. */
	@Autowired
	private ProjectServiceImpl service;
	
	/**
	 * Gets all tags.
	 *
	 * @return all tags
	 * @throws ServiceException the service exception
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<TagDto>> getAllTags() throws ServiceException {
		List<Tag> tags = service.findAllTags();
		return new ResponseEntity<>(converter.convertTags(tags), HttpStatus.OK);
	}
	
	/**
	 * Gets tag by id.
	 *
	 * @param id the id
	 * @return tag by id
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<TagDto> getTagById(@PathVariable("id") int id) throws ServiceException {
		Tag tag = new Tag();
		try {
			tag = service.findTagById(id);
		} catch (ServiceException e) {
			throw new ServiceException("Tag with id:=" + id + " not found");
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}
	
	/**
	 * Creates tag.
	 *
	 * @param tag the tag
	 * @return response entity
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<TagDto> createTag(@RequestBody Tag tag) {
		Tag tagToCreate = new Tag();
		try {
			tagToCreate = service.createTag(tag.getName());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(converter.convertTag(tagToCreate), HttpStatus.OK);
	}
	
	/**
	 * Delete tag.
	 *
	 * @param id the id
	 * @return response entity
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<TagDto> deleteTag(@PathVariable("id") int id) {
		Tag tag = new Tag();
		try {
			tag = service.deleteTag(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.NO_CONTENT);
	}
}
