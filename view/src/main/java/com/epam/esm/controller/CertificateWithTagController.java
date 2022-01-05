package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.CertificateWithTagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.impl.ProjectServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/certificateWithTag")
public class CertificateWithTagController {

	/** Converter. */
	private DtoConverter converter = DtoConverter.getInstance();

	/** Service. */
	@Autowired
	private ProjectServiceImpl service;

	/**
	 * Gets certificates with tags.
	 *
	 * @param fieldMap the field map
	 * @return the certificates with tags
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTags() {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTags();
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/tag/{name}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByTagname(
			@PathVariable(name = "name", required = false) String tagName) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByTagname(tagName);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/tag/{name}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByTagnameSorted(
			@PathVariable("name") String tagName, @PathVariable("sortType") String sortType) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByTagnameSorted(tagName, sortType);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/certificate/{name}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificate(
			@PathVariable("name") String certificateName) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByCertificate(certificateName);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/certificate/{name}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateSorted(

			@PathVariable("name") String certificateName, @PathVariable("sortType") String sortType) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByCertificateSorted(certificateName, sortType);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/{tagName}/{certificateName}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateAndTagname(

			@PathVariable("certificateName") String certificateName, @PathVariable("tagName") String tagName) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByCertificateAndTagname(tagName, certificateName);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@GetMapping("/{tagName}/{certificateName}/{sortType}")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTagByCertificateAndTagname(
			@PathVariable("tagName") String tagName, @PathVariable("certificateName") String certificateName,
			@PathVariable("sortType") String sortType) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTagsByCertificateAndTagnameSorted(tagName, certificateName, sortType);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	/**
	 * Creates certificate with tag.
	 *
	 * @param CertificateWithTag the cwt
	 * @return response entity
	 */

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
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> updateCertificateWithTag(@PathVariable("id") int id,

			@RequestBody Map<String, Object> fields) {
		GiftCertificate certificateToUpdate = new GiftCertificate();
		CertificateWithTag updated = new CertificateWithTag();
		try {
			certificateToUpdate = service.findCertificateById(id);
			if (fields.get("name") != null) {
				certificateToUpdate.setName(fields.get("name").toString());
			}
			if (fields.get("description") != null) {
				certificateToUpdate.setDescription(fields.get("description").toString());
			}
			if (fields.get("price") != null) {
				certificateToUpdate.setPrice((int) fields.get("price"));
			}
			if (fields.get("duration") != null) {
				certificateToUpdate.setDuration(fields.get("duration").toString());
			}
			updated = service.update(fields.get("tagName").toString(), certificateToUpdate, id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertWithTag(updated), HttpStatus.OK);
	}

}
