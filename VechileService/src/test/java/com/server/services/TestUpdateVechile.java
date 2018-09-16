package com.server.services;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
public class TestUpdateVechile {

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
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldUpdateVehicleStatusSuccessfully() throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("vechileId", "10002");
		jsonObject.accumulate("status", "DisConn");

		this.mvc.perform(post("/vechile/update/status").accept(MediaType.APPLICATION_JSON)
				.content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

		this.mvc.perform(get("/vechile").param("vechileId", "10002")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", containsString("DisConn")));

	}

	@Test
	public void shouldReturnUnsupportedTypeStatus() throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("vechileId", "10002");
		jsonObject.accumulate("status", "DisConn");

		this.mvc.perform(
				post("/vechile/update/status").accept(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
				.andDo(print()).andExpect(status().isUnsupportedMediaType());

	}

	@Test
	public void shouldReturnErrorWithCode400AndErrorMsg() throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("vechileId", "10002");
		jsonObject.accumulate("statas", "DisConn");

		this.mvc.perform(post("/vechile/update/status").accept(MediaType.APPLICATION_JSON)
				.content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.entity", containsString("status can not be empty")))
				.andExpect(jsonPath("$.status", is(400)));

	}

	@Test
	public void shouldReturnErrorWithCode400AndErrorMsgForId() throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("vechileId2", "10002");
		jsonObject.accumulate("status", "DisConn");

		this.mvc.perform(post("/vechile/update/status").accept(MediaType.APPLICATION_JSON)
				.content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.entity", containsString("vechile id can not be empty")))
				.andExpect(jsonPath("$.status", is(400)));

	}

}
