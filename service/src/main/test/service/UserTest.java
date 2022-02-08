package service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.UserServiceImpl;
import com.epam.esm.persistence.impl.UserPersistenceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserTest {

	@MockBean
	protected UserPersistenceImpl persistence;

	@InjectMocks
	protected UserServiceImpl service;

	private User user;
	private List<User> users;
	private Tag tag;

	@BeforeEach
	void setUp() {
		user = new User();
		users = new ArrayList<>();
		tag = new Tag();
	}

	@Test
	protected void findById() throws ServiceException {
		when(persistence.findById(2)).thenReturn(user);
		User expected = service.findById(2);
		assertEquals(expected, user);
	}

	@Test
	protected void findAll() throws ServiceException {
		when(persistence.findAll(1, 5)).thenReturn(users);
		List<User> expected = service.findAll(1, 5);
		assertEquals(expected, users);
	}
	
	@Test
	protected void findPopularTag() throws ServiceException{
		when(persistence.findMostPopularTag()).thenReturn(tag);
		Tag expected = service.findMostPopularTag();
		assertEquals(expected, tag);
	}

}
