package com.pack.Profileservice.Controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.pack.Profileservice.model.MessageSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.pack.Profileservice.Entity.Profile;
import com.pack.Profileservice.Service.ProfileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private KafkaTemplate<String, Object> template;

	private String topic = "Test_Topics1";

	MessageSpecs messageSpecs = new MessageSpecs();
	LocalTime time = LocalTime.now();
	List<String> messages = new ArrayList<>();

	@GetMapping("/consumeStringMessage")
	public List<String> consumeMsg() {
		return messages;
	}
	
	
	@KafkaListener(groupId = "Test_Topics1", topics = "Test_Topics1", containerFactory = "kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic1(String data) {
		messages.add(data);
		return messages;
	}

	@GetMapping("/profile")
	public ResponseEntity<?> getAllProfiles() {
		List<Profile> profileList = profileService.getAllProfiles();
		if (profileList.size() > 0) {
			messageSpecs.setProfileId("ALL ID'S ARE DISPLAYED");
			messageSpecs.setOperation("SHOW ALL PROFILES");
			messageSpecs.setLastupdated(time);
			template.send(topic, messageSpecs.toString());
			log.info("ALL PROFILES ARE DISPLAYED");
			return new ResponseEntity<>(profileList, HttpStatus.OK);
		} else
			return new ResponseEntity<>("NO PROFILES FOUND", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/profile/{profileId}")
	public ResponseEntity<?> getProfileById(@PathVariable("profileId") String profileId) {
		Profile profile = profileService.getProfileById(profileId);
		if (profile != null) {
			messageSpecs.setProfileId(profile.getProfileId());
			messageSpecs.setOperation("SHOW PROFILE BY ID");
			messageSpecs.setLastupdated(time);
			template.send(topic, messageSpecs.toString());
			log.info("PROFILE BY ID DISPLAYED");
			return new ResponseEntity<>(profile, HttpStatus.OK);
		} 
		else
			log.error("PROFILE NOT FOUND BY THE ID");
		    return new ResponseEntity<>("INVALID PROFILE ID", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/profile")
	public ResponseEntity<?> addProfiles(@RequestBody Profile profile) {
		Profile profile2 = profileService.addProfiles(profile);
		if (profile != null) {
			log.info("PROFILE CREATED");
			messageSpecs.setProfileId(profile.getProfileId());
			messageSpecs.setOperation("CREATE");
			messageSpecs.setLastupdated(time);
			template.send(topic, messageSpecs.toString());
			return new ResponseEntity<>(profile2, HttpStatus.OK);
		} else
			log.error("PROFILE SPECIFICATIONS MISSING");
		return new ResponseEntity<>("PROFILE SPECIFICATIONS MISSING", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/profile/{profileId}")
	public ResponseEntity<?> updateProfiles(@PathVariable("profileId") String profileId, @RequestBody Profile profile) {
		Profile profile2 = profileService.getProfileById(profileId);
		if (profile2 != null) {
			Profile profileNew = profileService.updateProfiles(profileId, profile);
			log.info("PROFILE FOUND AND UPDATED");
			messageSpecs.setProfileId(profile.getProfileId());
			messageSpecs.setOperation("PROFILE UPDATED");
			messageSpecs.setLastupdated(time);
			template.send(topic, messageSpecs.toString());
			return new ResponseEntity<>(profileNew, HttpStatus.OK);
		} else
			log.info("PROFILE NOT FOUND");
		return new ResponseEntity<>("PROFILE NOT FOUND", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/profile/{profileId}")
	public ResponseEntity<?> deleteProfileById(@PathVariable("profileId") String profileId) {
		Profile profile = profileService.getProfileById(profileId);
		if (profile != null) {
			log.info("PROFILE FOUND AND DELETED");
			messageSpecs.setProfileId(profile.getProfileId());
			messageSpecs.setOperation("PROFILE DELETED");
			messageSpecs.setLastupdated(time);
			template.send(topic,  messageSpecs.toString());
			profileService.deleteProfileById(profileId);
			return new ResponseEntity<>("PROFILE DELETED", HttpStatus.BAD_REQUEST);
		} else
			log.info("PROFILE NOT FOUND");
		return new ResponseEntity<>("INVALID PROFILE", HttpStatus.BAD_REQUEST);
	}
}