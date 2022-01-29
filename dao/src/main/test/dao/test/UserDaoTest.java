package dao.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.dao.impl.JdbcTemplateUserDaoImpl;
import com.epam.esm.model.entity.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles(profiles = "dev")
class UserDaoTest {

	@Autowired
	public JdbcTemplateUserDaoImpl userDao;
	
	@Test
	void findAllTest() {
		List<User> expected = userDao.findAll(1,5);
		assertNotNull(expected);
	}
	
	@Test
	void findById() {
		User expected = userDao.findById(2);
		assertNotNull(expected);
	}
}
