package com.epam.esm.dto.converter;

import java.util.List;

import com.epam.esm.dto.CertificateWithTagDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;


/**
 * Class DtoConverter.
 */
public class DtoConverter {
	
	/** Istance. */
	private static DtoConverter instance = new DtoConverter();

	/**
	 * Instantiates a new dto converter.
	 */
	public DtoConverter() {
		// default constructor
	}

	/**
	 * Gets the single instance of DtoConverter.
	 *
	 * @return single instance of DtoConverter
	 */
	public static DtoConverter getInstance() {
		return instance;
	}

	/**
	 * Convert cert with tag.
	 *
	 * @param entity the entity
	 * @return the certificate with tag dto
	 */
	public CertificateWithTagDto convertCertWithTag(CertificateWithTag entity) {
		CertificateWithTagDto dto = new CertificateWithTagDto();
		dto.setTagId(entity.getTagId());
		dto.setTagName(entity.getTagName());
		dto.setCertificateId(entity.getCertificateId());
		dto.setCertificateName(entity.getCertificateName());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setDuration(entity.getDuration());
		dto.setCreateDate(entity.getCreateDate());
		dto.setLastUpdateDate(entity.getLastUpdateDate());
		return dto;
	}

	/**
	 * Convert gift certificate.
	 *
	 * @param entity the entity
	 * @return the gift certificate dto
	 */
	public GiftCertificateDto convertGiftCertificate(GiftCertificate entity) {
		GiftCertificateDto dto = new GiftCertificateDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setDuration(entity.getDuration());
		dto.setCreateDate(entity.getCreateDate());
		dto.setLastUpdateDate(entity.getLastUpdateDate());
		return dto;
	}

	/**
	 * Convert tag.
	 *
	 * @param entity the entity
	 * @return the tag dto
	 */
	public TagDto convertTag(Tag entity) {
		TagDto dto = new TagDto();
		dto.setTagId(entity.getId());
		dto.setTagName(entity.getName());
		return dto;
	}
	
	/**
	 * Convert certificates.
	 *
	 * @param list the list
	 * @return the list
	 */
	public List<GiftCertificateDto> convertCertificates(List<GiftCertificate> list){
		return list.stream().map(this::convertGiftCertificate).toList();
	}
	
	/**
	 * Convert tags.
	 *
	 * @param list the list
	 * @return the list
	 */
	public List<TagDto> convertTags(List<Tag> list){
		return list.stream().map(this::convertTag).toList();
	}
	
	/**
	 * Convert certs with tags.
	 *
	 * @param list the list
	 * @return the list
	 */
	public List<CertificateWithTagDto> convertCertsWithTags(List<CertificateWithTag> list){
		return list.stream().map(this::convertCertWithTag).toList();
	}
	
	public UserDto convertUser(User user) {
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setUserName(user.getUserName());
		dto.setUserSurname(user.getUserSurname());
		return dto;
	}
	
	public List<UserDto> convertUserList(List<User> userList){
		return userList.stream().map(this::convertUser).toList();
	}
}
