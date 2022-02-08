package com.epam.esm.persistence.query;

public class GiftCertificateQuery {
	
	private GiftCertificateQuery() {
		
	}
	
	public static final String FIND_CERTIFICATES_WITH_TAGS = """
			select new CertificateWithTag
			(t.id, t.name, c.certificateId, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate) 
			from GiftCertificate c
			join c.tags t
			""";
	public static final String FIND_CERTIFICATE_WITH_TAG = """
			select new CertificateWithTag
			(t.id, t.name, c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate) 
			from GiftCertificate c
			join c.tags t
			where t.id = :tag_id and c.id = :certificate_id
			""";
	public static final String FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME = """
			select new CertificateWithTag
			(t.id, t.name, c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate) 
			from GiftCertificate c
			join c.tags t
			where t.name = :tag_name
			""";
	public static final String FIND_CERTIFICATE_WITH_TAG_BY_PARTNAME = """
			select new CertificateWithTag
			(t.id, t.name, c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate) 
			from GiftCertificate c
			join c.tags t
			where c.name like :name
			""";
	public static final String FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME_PARTNAME = """
			select new CertificateWithTag
			(t.id, t.name, c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate) 
			from GiftCertificate c
			join c.tags t
			where c.name like :name and t.name = :tag_name
			""";
}
