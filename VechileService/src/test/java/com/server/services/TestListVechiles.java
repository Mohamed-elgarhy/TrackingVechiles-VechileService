package com.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
public class TestListVechiles {

	/*
	 * @Test public void contextLoads() { }
	 */

	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private VechileController vechileController;

	@Mock
	VechileRepository vechileRepository;

	@Mock
	CustomerServiceProxy customerServiceProxy;

	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldReturnAVechileAsJson() throws Exception {

		when(vechileRepository.findById(anyLong()))
				.thenAnswer(
						new Answer() {
							public Optional<Vechile> answer(InvocationOnMock invocation) throws Throwable {
				                Object [] arguments = invocation.getArguments();
				                Long vechileId = (Long) arguments[0];
				                return vechileId == 10001L ? Optional.of(new Vechile(10001L, "RX1", "REG456", 1L, "Connected")) : Optional.empty();
				            }
						}
						
						);
		ResponseEntity<Vechile> vechile =  vechileController.retrieveVechileById(10001L);
		
		assertNotNull(vechile);
		assertNotNull(vechile.getBody());
		assertEquals(Long.valueOf("10001"), vechile.getBody().getId());
		assertEquals("RX1", vechile.getBody().getVechileId());
		assertEquals("REG456", vechile.getBody().getRegisterationNumber());
		assertEquals(Long.valueOf("1"), vechile.getBody().getOwnerCustomerId());
		assertEquals("Connected", vechile.getBody().getStatus());
		
	}

	@Test
	public void shouldReturnNotFoundStatus_mocked() throws Exception {

		when(vechileRepository.findById(anyLong()))
		.thenAnswer(
				new Answer() {
					public Optional<Vechile> answer(InvocationOnMock invocation) throws Throwable {
		                Object [] arguments = invocation.getArguments();
		                Long vechileId = (Long) arguments[0];
		                return vechileId == 10001L ? Optional.of(new Vechile(10001L, "YS2R4X20005388011", "JKL012", 1L, "Connected")) : Optional.empty();
		            }
				}
				
				);
		
		ResponseEntity<Vechile> responseEntity =  vechileController.retrieveVechileById(10003L);
		
		assertNotNull(responseEntity);
		assertEquals(responseEntity.getStatusCode().value(),HttpStatus.SC_NOT_FOUND);
		

	}

	@Test
	public void shouldJoinCustomerAndVechileDataSucessfully() throws Exception {
		List<Vechile> expectedVechiles = Arrays
				.asList(new Vechile(10001L, "YS2R4X20005388011", "JKL012", 1L, "Connected"));
		when(vechileRepository.findAll()).thenReturn(expectedVechiles);

		List<CustomerDataBean> expectedCustomers = Arrays.asList(new CustomerDataBean[] {
				new CustomerDataBean(1L, "henry", "dokki"), new CustomerDataBean(2L, "Ronaldo", "Haram") });

		when(customerServiceProxy.retrieveVechileBeanList()).thenReturn(expectedCustomers);

		List<VechileBean> vechiles = this.vechileController.retrieveVechileBeanList();
		System.out.println(vechiles.get(0));
		assertEquals(1, vechiles.size());
		assertEquals("dokki", vechiles.get(0).getCustomerAdress());
		assertEquals("henry", vechiles.get(0).getCustomerName());
		assertEquals("Connected", vechiles.get(0).getStatus());
		assertEquals("YS2R4X20005388011", vechiles.get(0).getVechileId());
		assertEquals("JKL012", vechiles.get(0).getRegisterationNumber());

	}
	
	
	@Test
	public void shouldJoinCustomerAndVechileDataWithNoResult_noIntersectionInData() throws Exception {
		List<Vechile> expectedVechiles = Arrays
				.asList(new Vechile(10001L, "YS2R4X20005388011", "JKL012", 1L, "Connected"));
		when(vechileRepository.findAll()).thenReturn(expectedVechiles);

		List<CustomerDataBean> expectedCustomers = Arrays.asList(new CustomerDataBean[] {
				new CustomerDataBean(2L, "henry", "dokki"), new CustomerDataBean(3L, "Ronaldo", "Haram") });

		when(customerServiceProxy.retrieveVechileBeanList()).thenReturn(expectedCustomers);

		List<VechileBean> vechiles = this.vechileController.retrieveVechileBeanList();
		assertEquals(0, vechiles.size());
		

	}
	
	@Test
	public void shouldJoinCustomerAndVechileDataWithNoResult_noVechiles() throws Exception {
		List<Vechile> expectedVechiles = new ArrayList<>();
		when(vechileRepository.findAll()).thenReturn(expectedVechiles);

		List<CustomerDataBean> expectedCustomers = Arrays.asList(new CustomerDataBean[] {
				new CustomerDataBean(2L, "henry", "dokki"), new CustomerDataBean(3L, "Ronaldo", "Haram") });

		when(customerServiceProxy.retrieveVechileBeanList()).thenReturn(expectedCustomers);

		List<VechileBean> vechiles = this.vechileController.retrieveVechileBeanList();
		assertEquals(0, vechiles.size());
		

	}
	
	@Test
	public void shouldJoinCustomerAndVechileDataWithNoResult_noCustomers() throws Exception {
		List<Vechile> expectedVechiles = Arrays
				.asList(new Vechile(10001L, "YS2R4X20005388011", "JKL012", 1L, "Connected"));
		when(vechileRepository.findAll()).thenReturn(expectedVechiles);

		List<CustomerDataBean> expectedCustomers = new ArrayList<>();

		when(customerServiceProxy.retrieveVechileBeanList()).thenReturn(expectedCustomers);

		List<VechileBean> vechiles = this.vechileController.retrieveVechileBeanList();
		assertEquals(0, vechiles.size());
		

	}
	
	

}
