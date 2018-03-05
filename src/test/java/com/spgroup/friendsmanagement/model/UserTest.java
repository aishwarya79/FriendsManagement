package com.spgroup.friendsmanagement.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.spgroup.friendsmanagement.model.User;
import org.junit.Test;

public class UserTest {

	@Test
	public void testEquals() {
		User user1 = new User();
		user1.setId(100);
		user1.setEmail("testuser@nus.edu.sg");

		User user2 = new User();
		user2.setId(100);
		user2.setEmail("testuser@nus.edu.sg");

		User user3 = new User();
		User user4 = new User();

		assertEquals(user1, user2);
		assertEquals(user3, user4);
		assertEquals(user3, user3);
		assertEquals(user3.hashCode(), user3.hashCode());
	}

	@Test
	public void testNotEquals() {
		User user1 = new User();
		user1.setId(100);
		user1.setEmail("testuser1@nus.edu.sg");

		User user2 = new User();
		user2.setId(200);
		user2.setEmail("testuser2@nus.edu.sg");
		User user3 = new User();
		assertNotEquals(user1, user2);
		assertNotEquals(user1, user3);
	}

}
