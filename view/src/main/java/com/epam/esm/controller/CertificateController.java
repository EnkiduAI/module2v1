package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.impl.CertificateServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/certificates")
public class CertificateController {

	/** Converter. */
	private DtoConverter converter = DtoConverter.getInstance();

	/** Service. */
	@Autowired
	private CertificateServiceImpl service;

	/**
	 * Gets all certificates.
	 *
	 * @return all certificates
	 * @throws ServiceException the service exception
	 */
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<GiftCertificateDto>> getAllCertificates(@QueryParam("page") int page,
			@QueryParam("limit") int limit) throws ServiceException {
		List<GiftCertificate> giftCertificates;
		List<GiftCertificateDto> certificatesDto;
		try {
			giftCertificates = service.findAllCertificates(page, limit);
		} catch (ServiceException e) {
			throw new ServiceException("Can't find certificates");
		}
		certificatesDto = converter.convertCertificates(giftCertificates);
		for (GiftCertificateDto certificate : certificatesDto) {
			certificate.add(linkTo(methodOn(CertificateController.class).getCertificateById(certificate.getId()))
					.withSelfRel());
			certificate.add(linkTo(methodOn(CertificateWithTagController.class)
					.getCertificatesWithTagByCertificate(certificate.getName(), page, limit))
							.withRel("certificate with tag"));
		}
		return new ResponseEntity<>(certificatesDto, HttpStatus.OK);
	}

	/**
	 * Gets certificate by id.
	 *
	 * @param id the id
	 * @return the certificate by id
	 * @throws ServiceException the service exception
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> getCertificateById(@PathVariable("id") int id) throws ServiceException {
		GiftCertificate certificate = service.findCertificateById(id);
		GiftCertificateDto certificateDto = converter.convertGiftCertificate(certificate);
		certificateDto.add(
				linkTo(methodOn(CertificateController.class).getAllCertificates(1, 5)).withRel("all certificates"));
		return new ResponseEntity<>(certificateDto, HttpStatus.OK);
	}

	/**
	 * Creates certificate.
	 *
	 * @param fields the fields
	 * @return response entity
	 */
	@PostMapping
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> createCertificate(@RequestBody Map<String, Object> fields)
			throws ServiceException {
		GiftCertificate certificate = new GiftCertificate();
		try {
			certificate = service.createCertificate(fields.get("name").toString(), fields.get("description").toString(),
					(int) fields.get("price"), fields.get("duration").toString());
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		GiftCertificateDto certificateDto = converter.convertGiftCertificate(certificate);
		certificateDto.add(linkTo(methodOn(CertificateController.class).getCertificateById(certificateDto.getId()))
				.withRel("get certificate"));
		certificateDto.add(linkTo(methodOn(CertificateController.class).getAllCertificates(1, 5)).withRel("get all"));
		return new ResponseEntity<>(certificateDto, HttpStatus.OK);
	}

	/**
	 * Update gift certificate.
	 *
	 * @param id      the id
	 * @param request the request
	 * @return response entity
	 */
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
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
	 * Delete certificate.
	 *
	 * @param id the id
	 * @return response entity
	 * @throws ServiceException
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<GiftCertificateDto> deleteCertificate(@PathVariable("id") int id) throws ServiceException {
		GiftCertificate certificate = new GiftCertificate();
		try {
			certificate = service.deleteCertificate(id);
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
		return new ResponseEntity<>(converter.convertGiftCertificate(certificate), HttpStatus.NO_CONTENT);
	}
}
