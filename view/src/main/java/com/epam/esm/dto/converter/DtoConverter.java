package com.epam.esm.dto.converter;

import java.util.List;

import com.epam.esm.dto.CertificateWithTagDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

public class DtoConverter {
	private static DtoConverter instance = new DtoConverter();

	public DtoConverter() {
		// default constructor
	}

	public static DtoConverter getInstance() {
		return instance;
	}

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

	public TagDto convertTag(Tag entity) {
		TagDto dto = new TagDto();
		dto.setTagId(entity.getId());
		dto.setTagName(entity.getName());
		return dto;
	}
	
	public List<GiftCertificateDto> convertCertificates(List<GiftCertificate> list){
		return list.stream().map(this::convertGiftCertificate).toList();
	}
	
	public List<TagDto> convertTags(List<Tag> list){
		return list.stream().map(this::convertTag).toList();
	}
	
	public List<CertificateWithTagDto> convertCertsWithTags(List<CertificateWithTag> list){
		return list.stream().map(this::convertCertWithTag).toList();
	}
}
