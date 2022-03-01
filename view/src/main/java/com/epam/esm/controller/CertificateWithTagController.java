package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.CertificateWithTagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.service.impl.CertificateServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/certificateWithTag")
public class CertificateWithTagController {

	/** Converter. */
	private DtoConverter converter = DtoConverter.getInstance();

	/** Service. */
	@Autowired
	private CertificateServiceImpl service;

	/**
	 * Gets certificates with tags.
	 *
	 * @param fieldMap the field map
	 * @return the certificates with tags
	 * @throws NotFoundException
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTags(@QueryParam("page") int page,
			@QueryParam("limit") int limit) throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTags(page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/tag/{name}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByTagname(
			@PathVariable(name = "name", required = false) String tagName, @QueryParam("page") int page,
			@QueryParam("limit") int limit) throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByTagname(tagName, page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/tags/{tag}")
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesByMultipleTags(
			@PathVariable("tag") List<String> tags, @QueryParam("page") int page, @QueryParam("limit") int limit)
			throws NotFoundException, ServiceException {
		List<CertificateWithTag> certificatesList = new ArrayList<>();
		List<CertificateWithTag> freshElements;
		for (String tag : tags) {
			freshElements = service.getCertificatesWithTagsByTagname(tag, page, limit);
			certificatesList.addAll(freshElements);
		}
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(certificatesList);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/tag/{name}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByTagnameSorted(
			@PathVariable("name") String tagName, @PathVariable("sortType") String sortType,
			@QueryParam("page") int page, @QueryParam("limit") int limit) throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByTagnameSorted(tagName, sortType, page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/certificate/{name}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificate(
			@PathVariable("name") String certificateName, @QueryParam("page") int page, @QueryParam("limit") int limit)
			throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByCertificate(certificateName, page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/certificate/{name}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateSorted(
			@PathVariable("name") String certificateName, @PathVariable("sortType") String sortType,
			@QueryParam("page") int page, @QueryParam("limit") int limit) throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByCertificateSorted(certificateName, sortType,
				page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/{tagName}/{certificateName}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateAndTagname(
			@PathVariable("certificateName") String certificateName, @PathVariable("tagName") String tagName,
			@QueryParam("page") int page, @QueryParam("limit") int limit) throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByCertificateAndTagname(tagName, certificateName,
				page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	@GetMapping("/{tagName}/{certificateName}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateAndTagname(
			@PathVariable("tagName") String tagName, @PathVariable("certificateName") String certificateName,
			@PathVariable("sortType") String sortType, @QueryParam("page") int page, @QueryParam("limit") int limit)
			throws NotFoundException, ServiceException {
		List<CertificateWithTag> cwt = service.getCertificatesWithTagsByCertificateAndTagnameSorted(tagName,
				certificateName, sortType, page, limit);
		List<CertificateWithTagDto> cwtDto = converter.convertCertsWithTags(cwt);
		return new ResponseEntity<>(cwtDto, HttpStatus.OK);
	}

	/**
	 * Creates certificate with tag.
	 *
	 * @param CertificateWithTag the cwt
	 * @return response entity
	 */

	@PostMapping
	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> createCertificateWithTag(@RequestBody CertificateWithTag cwt) {
		CertificateWithTag created = new CertificateWithTag();
		try {
			created = service.createCertificate(cwt.getTagName(), cwt.getCertificateName(), cwt.getDescription(),
					cwt.getPrice(), cwt.getDuration());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertWithTag(created), HttpStatus.OK);
	}

	/**
	 * Update certificate with tag.
	 *
	 * @param id     the id
	 * @param fields the fields
	 * @return response entity
	 */
	@PatchMapping("/{id}")
	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> updateCertificateWithTag(@PathVariable("id") int id,
			@RequestBody Map<String, Object> fields) throws ServiceException, NotFoundException {
		CertificateWithTag updated = service.updateWithTag(fields, id);
		return new ResponseEntity<>(converter.convertCertWithTag(updated), HttpStatus.OK);
	}

}
