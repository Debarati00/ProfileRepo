package com.pack.Profileservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pack.Profileservice.Dao.ProfileRepository;
import com.pack.Profileservice.Entity.CollectionRules;
import com.pack.Profileservice.Entity.Orderss;
import com.pack.Profileservice.Entity.Profile;
import com.pack.Profileservice.Service.ProfileServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceTest {

	@InjectMocks
	ProfileServiceImpl profileservice;

	@Mock
	ProfileRepository profileRepository;

	Profile profile = new Profile();
	CollectionRules rules = new CollectionRules();
	Orderss orders1 = new Orderss();

	List<CollectionRules> collectionRules;
	List<Orderss> orderss;

	@BeforeEach
	public void setUp() throws Exception {

		rules.setDescription("description");
		rules.setFraudCheckEnabled(true);
		rules.setPaymentProcessingEnabled(true);
		rules.setResourcingEnabled(true);
		rules.setProfile(profile);
		List<CollectionRules> collectionRules = new ArrayList<>();
		collectionRules.add(rules);

		orders1.setOrderProfileID("od1");
		orders1.setSourceChannel("sourceChannel");
		orders1.setOrderLineType("orderLineType");
		orders1.setOrderFulfillmentType("orderFulfillmentType");
		orders1.setOrderProfileID("o");
		orders1.setProfile(profile);
		List<Orderss> orderss = new ArrayList<>();
		orderss.add(orders1);

		profile.setCollectionRules(collectionRules);
		profile.setCreatedBy("createdBy");
		profile.setLastUpdatedBy("lastUpdatedBy");
		profile.setOrderss(orderss);
		profile.setProfileId("profileId");
		profile.setProfileStatus("profileStatus");
		profile.setProfileType("profileType");
	}

	@Test
	void testGetAllProfiles() {
		List<Profile> profileList = new ArrayList<>();
		profileList.add(profile);
		when((List<Profile>) profileRepository.findAll()).thenReturn(profileList);
		assertThat(profileservice.getAllProfiles()).isEqualTo(profileList);

	}

	@Test
	void testGetProfileById() {
		when(profileRepository.findById("profileId")).thenReturn(Optional.of(profile));
		assertThat(profileservice.getProfileById("profileId")).isEqualTo(profile);

	}

	@Test
	void testUpdateProfiles() {

		when(profileRepository.save(profile)).thenReturn(profile);
		assertThat(profileservice.updateProfiles("profileId", profile));
	}

	@Test
	void testDeleteProfileById() {
		when(profileRepository.findById("profileId")).thenReturn(Optional.of(profile));
		profileservice.deleteProfileById("profileId");

	}

	@Test
	void testaddProfiles() {

		when(profileRepository.save(profile)).thenReturn(profile);
		assertThat(profileservice.addProfiles(profile));

	}
}