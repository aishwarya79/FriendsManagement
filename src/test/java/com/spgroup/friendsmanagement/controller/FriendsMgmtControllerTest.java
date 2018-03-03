package com.spgroup.friendsmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spgroup.friendsmanagement.dto.response.FriendsMgmtResponse;
import com.spgroup.friendsmanagement.model.User;
import com.spgroup.friendsmanagement.repository.FriendsMgmtRepository;
import com.spgroup.friendsmanagement.service.FriendsMgmtService;
import com.spgroup.friendsmanagement.spring.FriendsmanagementApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { FriendsmanagementApplication.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FriendsMgmtControllerTest {

	@Autowired
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Autowired
	private FriendsMgmtService friendsMgmtService;

	@Autowired
	private FriendsMgmtRepository friendsMgmtRepository;

	public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@After
	public void tearDown() {
	}

	protected ResultActions getResponseResultFor(String testUrl, String json) throws Exception {
		return this.mockMvc.perform(
				post(testUrl).content(json).contentType(MediaType.APPLICATION_JSON).accept("application/json"));
	}

	@Test
	public void testAddFriendsConnection() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String connectFreindsURI = "/friendsmanagement/api/v1/friends/connect";
		String json = "{\"friends\":[\"testuser1@example.com\",\"testuser2@example.com\"]}";
		ResultActions resultActions = getResponseResultFor(connectFreindsURI, json);
		resultActions.andExpect((status().isOk()));
		resultActions.andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
			}
		});

		deleteTestData(testUsers);

	}

	@Test
	public void testAddFriendsConnectionInvalidRequest() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		testUsers.add("testuser3@example.com");
		createTestData(testUsers);

		String connectFreindsURI = "/friendsmanagement/api/v1/friends/connect";
		String json = "{\"friends\":[\"testuser1@example.com\",\"testuser2@example.com\",\"testuser3@example.com\"]}";
		ResultActions resultActions = getResponseResultFor(connectFreindsURI, json);
		resultActions.andExpect((status().isBadRequest()));

		deleteTestData(testUsers);

	}

	@Test
	public void testAddFriendsConnectionNonExistingUser() throws Exception {
		// generate test data

		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String connectFreindsURI = "/friendsmanagement/api/v1/friends/connect";
		String json = "{\"friends\":[\"testuser1@example.com\",\"testuser4@example.com\"]}";
		ResultActions resultActions = getResponseResultFor(connectFreindsURI, json);
		resultActions.andExpect((status().isBadRequest()));

		deleteTestData(testUsers);
	}
	
	@Test
	public void testAddFriendsConnectionInvalidEmail() throws Exception {
		// generate test data

		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String connectFreindsURI = "/friendsmanagement/api/v1/friends/connect";
		String json = "{\"friends\":[\"testuser1example.com\",\"testuser4example.com\"]}";
		ResultActions resultActions = getResponseResultFor(connectFreindsURI, json);
		resultActions.andExpect((status().isBadRequest()));

		deleteTestData(testUsers);
	}

	@Test
	public void testGetFriendsList() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);
		connectFriends("testuser1@example.com", "testuser2@example.com");

		String getFriendListURI = "/friendsmanagement/api/v1/friends/list";
		String reqJson = "{\"email\" : \"testuser1@example.com\"}";
		ResultActions resultActions2 = getResponseResultFor(getFriendListURI, reqJson);
		resultActions2.andExpect((status().isOk()));
		resultActions2.andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
				assertEquals(1, response.getFriends().size());
				List<String> friends = new ArrayList<>(response.getFriends());
				assertEquals("testuser2@example.com", friends.get(0));
			}
		});

		deleteTestData(testUsers);

	}

	@Test
	public void testGetCommonFriends() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		testUsers.add("testuser3@example.com");
		createTestData(testUsers);
		connectFriends("testuser1@example.com", "testuser2@example.com");
		connectFriends("testuser1@example.com", "testuser3@example.com");

		String getCommonFriendsURI = "/friendsmanagement/api/v1/friends/common";
		String json = "{\"friends\":[\"testuser2@example.com\",\"testuser3@example.com\"]}";
		ResultActions resultActions = getResponseResultFor(getCommonFriendsURI, json);
		resultActions.andExpect((status().isOk()));
		resultActions.andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
				assertEquals(1, response.getFriends().size());
				List<String> friends = new ArrayList<>(response.getFriends());
				assertEquals("testuser1@example.com", friends.get(0));
			}
		});

		deleteTestData(testUsers);
	}

	@Test
	public void testSubsribeUpdates() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String subscribeFreindsURI = "/friendsmanagement/api/v1/users/subscribe";
		String json = "{\"requestor\": \"testuser1@example.com\",\"target\": \"testuser2@example.com\"}";
		ResultActions resultActions = getResponseResultFor(subscribeFreindsURI, json);
		resultActions.andExpect((status().isOk()));
		resultActions.andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
			}
		});

		deleteTestData(testUsers);

	}

	@Test
	public void testBlockUser() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String blockFreindsURI = "/friendsmanagement/api/v1/users/block";
		String json = "{\"requestor\": \"testuser1@example.com\",\"target\": \"testuser2@example.com\"}";
		ResultActions resultActions = getResponseResultFor(blockFreindsURI, json);
		resultActions.andExpect((status().isOk()));
		resultActions.andDo(new ResultHandler() {

			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
			}
		});

		deleteTestData(testUsers);

	}
	
	@Test
	public void testConnectBlockedUsers() throws Exception {
		// generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		createTestData(testUsers);

		String blockFreindsURI = "/friendsmanagement/api/v1/users/block";
		String json = "{\"requestor\": \"testuser1@example.com\",\"target\": \"testuser2@example.com\"}";
		ResultActions resultActions = getResponseResultFor(blockFreindsURI, json);
		resultActions.andExpect((status().isOk()));
		
		ResultActions resultActions2 =connectFriends("testuser1@example.com", "testuser2@example.com");
		resultActions2.andExpect((status().is5xxServerError()));
		deleteTestData(testUsers);
	}
	
	@Test
	public void testGetUpdateRecipientsList() throws Exception {
		//generate test data
		List<String> testUsers = new ArrayList<>();
		testUsers.add("testuser1@example.com");
		testUsers.add("testuser2@example.com");
		testUsers.add("testuser3@example.com");
		testUsers.add("testuser4@example.com");
		testUsers.add("testuser5@example.com");
		createTestData(testUsers);
		
		//add testuser1 and testuser2 as friends
		connectFriends("testuser1@example.com", "testuser2@example.com");
		//add testuser1 and testuser3 as friends
		connectFriends("testuser1@example.com", "testuser3@example.com");
		
		//subscribe testuser4 to testuser1 updates
		String subscribeFreindsURI = "/friendsmanagement/api/v1/users/subscribe";
		String subscribeJson = "{\"requestor\": \"testuser4@example.com\",\"target\": \"testuser1@example.com\"}";
		ResultActions resultActions = getResponseResultFor(subscribeFreindsURI, subscribeJson);
		resultActions.andExpect((status().isOk()));
		
		//testuser 3 blocks testuser1
		String blockFreindsURI = "/friendsmanagement/api/v1/users/block";
		String blockJson = "{\"requestor\": \"testuser3@example.com\",\"target\": \"testuser1@example.com\"}";
		ResultActions resultActions1 = getResponseResultFor(blockFreindsURI, blockJson);
		resultActions1.andExpect((status().isOk()));
		
		String getUpdateRecipientsURI = "/friendsmanagement/api/v1/notification/recipients";
		String json = "{\"sender\": \"testuser1@example.com\",\"text\" : \"hello  world ! testuser5@example.com\"}";
		ResultActions resultActions2 = getResponseResultFor(getUpdateRecipientsURI, json);
		resultActions2.andExpect((status().isOk()));
		resultActions2.andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				String str = result.getResponse().getContentAsString();
				FriendsMgmtResponse response = OBJECT_MAPPER.readValue(str, FriendsMgmtResponse.class);
				assertNotNull(response);
				assertEquals(true, response.getSuccess());
				assertEquals(3, response.getRecipients().size());
				Set<String> recipients = response.getRecipients();
				assertTrue(recipients.contains("testuser2@example.com"));
				assertTrue(recipients.contains("testuser4@example.com"));
				assertTrue(recipients.contains("testuser5@example.com"));
				assertFalse(recipients.contains("testuser3@example.com"));
			}
		});

		deleteTestData(testUsers);

	}

	private ResultActions connectFriends(String email1, String email2) throws Exception {
		String connectFreindsURI = "/friendsmanagement/api/v1/friends/connect";
		String json = "{\"friends\":[\"" + email1 + "\",\"" + email2 + "\"]}";
		ResultActions resultActions = getResponseResultFor(connectFreindsURI, json);
		return resultActions;
	}

	private void createTestData(List<String> userEmails) {
		for (String email : userEmails) {
			User testuser = new User();
			testuser.setEmail(email);
			friendsMgmtRepository.save(testuser);
		}
	}

	private void deleteTestData(List<String> userEmails) {
		for (String email : userEmails) {
			User testuser = friendsMgmtRepository.findByEmail(email);
			friendsMgmtRepository.delete(testuser);
		}
	}

}
