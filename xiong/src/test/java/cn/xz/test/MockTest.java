package cn.xz.test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = { "classpath:spring_cfg.xml", "classpath:spring_mvc.xml" })
@Rollback(true)
// 4.2已废弃@TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = true)
public class MockTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private MockHttpSession mockHttpSession;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		mockHttpSession = new MockHttpSession();
	}

	@Test
	public void get() throws Exception {
		// mockMvc.perform(fileUpload("/doc").file("a1",
		// "ABC".getBytes("UTF-8")));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/aaa").accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.view().name("user/info"))//
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lee"))
				.andReturn();
		logger.info("" + mockHttpSession);
		logger.info(" getok!");
	}

	@Test
	public void post() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/test/{id}", 42)
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.view().name("user/info"))//
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lee"))
				.andReturn();
		logger.info("post ok!");
	}

	@Test
	public void postJson() throws Exception {
		String json = "{user:1,name:2}";
		mockMvc.perform(MockMvcRequestBuilders.post("/test/json{}", 12).contentType(MediaType.APPLICATION_JSON_UTF8).param("11", "")
				.content(json).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.view().name("user/info"))//
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				// .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lee"))
				.andReturn();
		logger.info("post json ok!");
	}
}
