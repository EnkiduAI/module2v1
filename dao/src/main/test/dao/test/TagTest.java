package dao.test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.esm.model.dao.impl.JdbcTemplateTagDaoImpl;
import com.epam.esm.model.entity.Tag;

public class TagTest {

	@Autowired
	JdbcTemplateTagDaoImpl tagDao;
	
	@Test
	void findByNameTest() {
		Tag expected = tagDao.findByName("film");
		assertNotNull(expected);
	}
	
	@Test
	void findByNameNullTest() {
		Tag expected = tagDao.findByName("f");
		assertNull(expected);
	}
}
