package dao.test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.dao.impl.JdbcTemplateTagDaoImpl;
import com.epam.esm.model.entity.Tag;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles(profiles = "dev")
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
