
package com.epam.esm.persistence.query;

public class UserQuery {
	public static final String FIND_MOST_POPULAR_TAG = """
							select tag.id_tag, tag_name
							from certificates.orders
							join gift_certificate_has_tag on gift_certificate_has_tag.gift_certificate_id = id_certificate
							join tag on tag.id_tag = gift_certificate_has_tag.tag_id
							where id_user = (select id_user
							from certificates.orders
							group by id_user
							order by sum(certificates.orders.price) desc
							limit 1)
							group by tag_name
							order by count(tag_id) desc
							limit 1
							""";
	public static final String FIND_MOST_POPULAR_TAG_TEST = """
			select new Tag (t.id, t.name)
			from Order o
			join o.certificates c
			join c.tags t
			where o.idCertificate = c.certificateId and o.idUser = :userId
			group by t.name
			order by count (t.id) desc
			""";
	public static final String FIND_HIGHEST_ORDER_COST = """
			select idUser
			from Order
			group by idUser
			order by count(price) desc
			""";
}
