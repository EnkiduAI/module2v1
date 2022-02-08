package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
import com.epam.esm.model.service.impl.TagServiceImpl;
import com.epam.esm.persistence.impl.TagPersistenceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TagTest {

	@MockBean
	protected TagPersistenceImpl persistence;
	
	@InjectMocks
	protected TagServiceImpl service;
	
	private Tag tag;
	private List<Tag> tags;
	
	@BeforeEach
	protected void setUp() {
		tag = new Tag();
		tags = new ArrayList<>();
	}
	
	@Test
	protected void findById() throws ServiceException {
		when(persistence.findById(57)).thenReturn(tag);
		Tag expected = service.findTagById(57);
		assertEquals(expected, tag);
	}
	
	@Test
	protected void findByName() throws ServiceException{
		when(persistence.findAll(1, 5)).thenReturn(tags);
		List<Tag> expected = service.findAllTags(1, 5);
		assertEquals(expected, tags);
		
	}
}
