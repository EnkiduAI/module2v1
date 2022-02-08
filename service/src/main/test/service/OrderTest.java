package service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.OrderServiceImpl;
import com.epam.esm.persistence.impl.GiftCetificatePersistanceImpl;
import com.epam.esm.persistence.impl.OrderPersistanceImpl;
import com.epam.esm.persistence.impl.UserPersistenceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class OrderTest {

	@MockBean
	protected OrderPersistanceImpl persistence;
	
	@MockBean
	protected UserPersistenceImpl userDao;
	
	@InjectMocks
	protected OrderServiceImpl service;
	
	private Order order;
	private List<Order> orders;
	private User user;
	
	@BeforeEach
	void setUp() {
		user = new User();
		order = new Order();
		orders = new ArrayList<>();
	}
	
	@Test
	protected void findById() throws ServiceException {
		when(persistence.findAllUserOrders(user.getUserId(), 1, 5)).thenReturn(orders);
		when(userDao.findById(1)).thenReturn(user);
		List<Order> expected = service.findAllUserOrders(1, 1, 5);
		assertEquals(expected, orders);
	}
	
	@Test
	protected void findOrderById() throws ServiceException {
		when(persistence.findUserOrderById(user.getUserId(), 4)).thenReturn(order);
		when(userDao.findById(2)).thenReturn(user);
		Order expected = service.findUserOrderById(2, 4);
		assertThat(expected instanceof Order);
	}
}
