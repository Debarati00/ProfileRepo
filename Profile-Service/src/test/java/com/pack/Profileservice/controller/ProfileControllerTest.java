package com.pack.Profileservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import com.pack.Profileservice.Controller.ProfileController;
import com.pack.Profileservice.Entity.CollectionRules;
import com.pack.Profileservice.model.MessageSpecs;
import com.pack.Profileservice.Entity.Orderss;
import com.pack.Profileservice.Entity.Profile;
import com.pack.Profileservice.Service.ProfileService;

@SpringBootTest
class ProfileserviceControllerTest {

	@Mock
	ProfileService profileService;

	@InjectMocks
	ProfileController profilecontroller;

	@Mock
	MessageSpecs messageSpecs;

	@Mock
	Profile profile;

	@Mock
	KafkaTemplate<String, Object> template;

	LocalTime time = LocalTime.now();

	List<String> messages = new ArrayList<>();

	List<CollectionRules> collectionRules;

	List<Orderss> orderss;

	@BeforeEach
	void setup() throws Exception {

		profile.setCreatedBy("createdBy");
		profile.setLastUpdatedBy("lastUpdatedBy");
		profile.setProfileId("profileId");
		profile.setProfileStatus("profileStatus");
		profile.setProfileType("profileType");
		profile.setCollectionRules(collectionRules);
		profile.setOrderss(orderss);
	}

	@Test
	void testGetAllProfiles() {
		List<Profile> profileList = new ArrayList<>();
		profileList.add(profile);
		when(profileService.getAllProfiles()).thenReturn(profileList);
		when(template.send("topic", "string")).thenReturn(null);
		ResponseEntity<?> profileList1 = profilecontroller.getAllProfiles();
		assertEquals(200, profileList1.getStatusCodeValue());
	}

	@Test
	void testGetProfileById() {
		when(profileService.getProfileById("profileId")).thenReturn(profile);
		when(template.send("topic", "toString")).thenReturn(null);
		ResponseEntity<?> profile1 = profilecontroller.getProfileById("profileId");
		assertEquals(200, profile1.getStatusCodeValue());
	}

	@Test
	void testUpdateProfile() {
		when(profileService.getProfileById("profileId")).thenReturn(profile);
		when(template.send("topic", "toString")).thenReturn(null);
		ResponseEntity<?> profile2 = profilecontroller.updateProfiles("profileId", profile);
		assertEquals(200, profile2.getStatusCodeValue());
	}

	@Test
	void testAddProfile() {
		when(profileService.addProfiles(profile)).thenReturn(profile);
		when(template.send("topic", "toString")).thenReturn(null);
		ResponseEntity<?> profile1 = profilecontroller.addProfiles(profile);
		assertEquals(200, profile1.getStatusCodeValue());
	}

	@Test
	void testDeleteProfile() {
		when(profileService.getProfileById("profileId")).thenReturn(profile);
		when(template.send("topic", "messageSpecs.toString")).thenReturn(null);
		ResponseEntity<?> string = profilecontroller.deleteProfileById("profileId");

	}

	@Test
	void testConsumeMsg() {
		assertEquals(profilecontroller.consumeMsg(), messages);
	}

	@Test
	void testGetMsgFromTopic1() {
		messages.add("data");
		assertEquals(profilecontroller.getMsgFromTopic1("data"), messages);
	}
}