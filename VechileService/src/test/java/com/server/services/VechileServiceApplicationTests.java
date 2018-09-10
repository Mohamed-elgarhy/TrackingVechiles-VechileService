package com.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class VechileServiceApplicationTests {

	/*
	 * @Test public void contextLoads() { }
	 */

	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private VechileController vechileController;

/*//	@Test
//	public void shouldReturnAllCustomersAsJson() throws Exception {
//
//		this.vechileController.retrieveVechileBeanList();
//	}
*/
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
				                return vechileId == 10001L ? Optional.of(new Vechile(10001L, "a", "b", 1L, "c")) : Optional.empty();
				            }
						}
						
						);//Optional.of(new Vechile(10001L, "YS2R4X20005384567", "JKL045", 1L, "Error in brakes"))

		/*this.mvc.perform(get("/vechile").param("vechileId", "10001")).andDo(print()).andExpect(status().isOk())
				// ;
				.andExpect(jsonPath("$.id", is(10001))).andExpect(jsonPath("$.vechileId", is("YS2R4X20005399401")))
				.andExpect(jsonPath("$.registerationNumber", is("ABC123")))
				.andExpect(jsonPath("$.ownerCustomerId", is(1))).andExpect(jsonPath("$.status", IsNull.nullValue()));*/
		ResponseEntity<Vechile> vechile =  vechileController.retrieveVechileById(10001L);
		
		assertNotNull(vechile);
		assertNotNull(vechile.getBody());
		assertEquals(Long.valueOf("10001"), vechile.getBody().getId());
		assertEquals("a", vechile.getBody().getVechileId());
	}

	@Test
	public void shouldNotReturnAVechile() throws Exception {

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

		//this.mvc.perform(get("/vechile").param("vechileId", "10002")).andDo(print()).andExpect(status().isNotFound());
		
		ResponseEntity<Vechile> vechile =  vechileController.retrieveVechileById(10003L);
		
		assertNotNull(vechile);
		assertEquals(vechile.getStatusCode().value(),HttpStatus.SC_NOT_FOUND);
		

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
	
	/*private class ReturnVechile implements Answer<Vechile> {
		 
        public Vechile answer(InvocationOnMock invocation) throws Throwable {
            Seed seed = invocation.getArgumentAt(0, Seed.class);
            return seed.getName().equals("Apple") ? appleTree : tree;
        }
         
    }*/

}
