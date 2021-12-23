package com.epam.esm.controller;

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

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
public class AppController {

	DtoConverter converter = DtoConverter.getInstance();

	@Autowired
	ProjectServiceImpl service;

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

	@GetMapping("/get_tags")
	@ResponseBody
	public ResponseEntity<List<TagDto>> getAllTags() throws ServiceException {
		List<Tag> tags = service.findAllTags();
		return new ResponseEntity<>(converter.convertTags(tags), HttpStatus.OK);
	}

	@GetMapping("/certificate/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> getCertificateById(@PathVariable("id") int id) throws ServiceException {
		GiftCertificate certificate;
		certificate = service.findCertificateById(id);
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	@GetMapping("/tag/{id}")
	@ResponseBody
	public ResponseEntity<TagDto> getTagById(@PathVariable("id") int id) throws ServiceException {
		Tag tag;
		try {
			tag = service.findTagById(id);
		} catch (ServiceException e) {
			throw new ServiceException("Tag with id:=" + id + " not found");
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}

	@GetMapping("/get_certificates_tags")
	@ResponseBody
	public ResponseEntity<List<CertificateWithTagDto>> getCertificatesWithTags(
			@RequestBody Map<String, String> fieldMap) {
		List<CertificateWithTag> cwt;
		try {
			cwt = service.getCertificatesWithTags(fieldMap.get("tagName"), fieldMap.get("certificateName"),
					fieldMap.get("sortType"));
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertsWithTags(cwt), HttpStatus.OK);
	}

	@PostMapping("/create_certificate")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> createTag(@RequestBody Map<String, Object> fields) {
		GiftCertificate certificate;
		try {
			certificate = service.createCertificate(fields.get("name").toString(),
					fields.get("description").toString(),(int)fields.get("price"), fields.get("duration").toString());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	@PostMapping("/create_tag")
	@ResponseBody
	public ResponseEntity<TagDto> createTag(@RequestBody Tag tag) {
		Tag tagToCreate;	
		try {
			tagToCreate = service.createTag(tag.getName());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(converter.convertTag(tagToCreate), HttpStatus.OK);
	}

	@PostMapping("/create_certificate_tag")
	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> createCertificateWithTag(@RequestBody CertificateWithTag cwt) {
		CertificateWithTag created;
		try {
			created = service.createCertificate(cwt.getTagName(), cwt.getCertificateName(), cwt.getDescription(),
					cwt.getPrice(), cwt.getDuration());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertWithTag(created), HttpStatus.OK);
	}

	@DeleteMapping("/delete_certificate/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> deleteCertificate(@PathVariable("id") int id) {
		GiftCertificate certificate;
		try {
			certificate = service.deleteCertificate(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.OK);
	}

	@DeleteMapping("/delete_tag/{id}")
	public ResponseEntity<TagDto> deleteTag(@PathVariable("id") int id) {
		Tag tag;
		try {
			tag = service.deleteTag(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}

	@PatchMapping(value = "/updateCertificate/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@PathVariable("id") int id,
			@RequestBody GiftCertificate request) {
		GiftCertificate updating;
		GiftCertificate updated;
		try {
			updating = service.findCertificateById(id);
			if (request.getName() != null) {
				updating.setName(request.getName());
			}
			if (request.getDescription() != null) {
				updating.setDescription(request.getDescription());
			}
			if (request.getPrice() > 0) {
				updating.setPrice(request.getPrice());
			}
			if (request.getDuration() != null) {
				updating.setDuration(request.getDuration());
			}
			updated = service.update(updating, id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(updated), HttpStatus.OK);
	}
	
	@PatchMapping(value = "/updateCertificateWithTag/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CertificateWithTagDto> updateCertificateWithTag(@PathVariable("id") int id,
			@RequestBody Map<String, Object> fields){
		GiftCertificate updating;
		CertificateWithTag updated;
		try {
			updating = service.findCertificateById(id);
			if (fields.get("name") != null) {
				updating.setName(fields.get("name").toString());
			}
			if (fields.get("description") != null) {
				updating.setDescription(fields.get("description").toString());
			}
			if (fields.get("price") != null) {
				updating.setPrice((int)fields.get("price"));
			}
			if (fields.get("duration") != null) {
				updating.setDuration(fields.get("duration").toString());
			}
			updated = service.update(fields.get("tagName").toString(),updating, id);
		}catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertCertWithTag(updated), HttpStatus.OK);
	}
}
