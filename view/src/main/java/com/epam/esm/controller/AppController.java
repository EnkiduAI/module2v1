package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.CertificateWithTagDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.impl.ProjectServiceImpl;

/**
 * Class AppController.
 */
@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
public class AppController {

	/** Converter. */
	private DtoConverter converter = DtoConverter.getInstance();

	/** Service. */
	@Autowired
	private ProjectServiceImpl service;

	/**
	 * Gets all certificates.
	 *
	 * @return all certificates
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/get_certificates")
	@ResponseBody
	public ResponseEntity<List<GiftCertificateDto>> getAllCertificates() throws ServiceException {
		List<GiftCertificate> giftCertificates;
		try {
			giftCertificates = service.findAllCertificates();

		} catch (ServiceException e) {
			throw new ServiceException("Can't find certificates");

		}
		return new ResponseEntity<>(converter.convertCertificates(giftCertificates), HttpStatus.OK);
	}

	/**
	 * Gets all tags.
	 *
	 * @return all tags
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/get_tags")
	@ResponseBody
	public ResponseEntity<List<TagDto>> getAllTags() throws ServiceException {
		List<Tag> tags = service.findAllTags();
		return new ResponseEntity<>(converter.convertTags(tags), HttpStatus.OK);
	}

	/**
	 * Gets certificate by id.
	 *
	 * @param id the id
	 * @return the certificate by id
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/certificate/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> getCertificateById(@PathVariable("id") int id) throws ServiceException {
		 GiftCertificate certificate = service.findCertificateById(id);
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	/**
	 * Gets tag by id.
	 *
	 * @param id the id
	 * @return tag by id
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/tag/{id}")
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
	 * Gets certificates with tags.
	 *
	 * @param fieldMap the field map
	 * @return the certificates with tags
	 */
	@GetMapping("/get_certificates_tags")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTags(
			@RequestBody Map<String, String> fieldMap) {
		List<CertificateWithTag> cwt = new ArrayList<>();
		try {
			cwt = service.getCertificatesWithTags(fieldMap.get("tagName"), fieldMap.get("certificateName"),
					fieldMap.get("sortType"));
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	/**
	 * Creates certificate.
	 *
	 * @param fields the fields
	 * @return response entity
	 */
	@PostMapping("/create_certificate")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> createCertificate(@RequestBody Map<String, Object> fields) {
		GiftCertificate certificate = new GiftCertificate();
		try {
			certificate = service.createCertificate(fields.get("name").toString(),
					fields.get("description").toString(),(int)fields.get("price"), fields.get("duration").toString());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	/**
	 * Creates tag.
	 *
	 * @param tag the tag
	 * @return response entity
	 */
	@PostMapping("/create_tag")
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
	 * Creates certificate with tag.
	 *
	 * @param CertificateWithTag the cwt
	 * @return response entity
	 */
	@PostMapping("/create_certificate_tag")
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
	 * Delete certificate.
	 *
	 * @param id the id
	 * @return response entity
	 */
	@DeleteMapping("/delete_certificate/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> deleteCertificate(@PathVariable("id") int id) {
		GiftCertificate certificate = new GiftCertificate();
		try {
			certificate = service.deleteCertificate(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	/**
	 * Delete tag.
	 *
	 * @param id the id
	 * @return response entity
	 */
	@DeleteMapping("/delete_tag/{id}")
	public ResponseEntity<TagDto> deleteTag(@PathVariable("id") int id) {
		Tag tag = new Tag();
		try {
			tag = service.deleteTag(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}

	/**
	 * Update gift certificate.
	 *
	 * @param id the id
	 * @param request the request
	 * @return response entity
	 */
	@PatchMapping(value = "/updateCertificate/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@PathVariable("id") int id,
			@RequestBody GiftCertificate request) {
		GiftCertificate certificateToUpdate = new GiftCertificate();
		GiftCertificate updated = new GiftCertificate();
		try {
			certificateToUpdate = service.findCertificateById(id);
			if (request.getName() != null) {
				certificateToUpdate.setName(request.getName());
			}
			if (request.getDescription() != null) {
				certificateToUpdate.setDescription(request.getDescription());
			}
			if (request.getPrice() > 0) {
				certificateToUpdate.setPrice(request.getPrice());
			}
			if (request.getDuration() != null) {
				certificateToUpdate.setDuration(request.getDuration());
			}
			updated = service.update(certificateToUpdate, id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(updated), HttpStatus.OK);
	}
	
	/**
	 * Update certificate with tag.
	 *
	 * @param id the id
	 * @param fields the fields
	 * @return response entity
	 */
	@PatchMapping(value = "/updateCertificateWithTag/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> updateCertificateWithTag(@PathVariable("id") int id,
			@RequestBody Map<String, Object> fields){
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
				certificateToUpdate.setPrice((int)fields.get("price"));
			}
			if (fields.get("duration") != null) {
				certificateToUpdate.setDuration(fields.get("duration").toString());
			}
			updated = service.update(fields.get("tagName").toString(),certificateToUpdate, id);
		}catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertWithTag(updated), HttpStatus.OK);
	}
}
