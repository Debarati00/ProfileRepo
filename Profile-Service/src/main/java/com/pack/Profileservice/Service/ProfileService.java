package com.pack.Profileservice.Service;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.pack.Profileservice.Entity.Profile;
public interface ProfileService {

	public List<Profile> getAllProfiles();
	public Profile getProfileById(@PathVariable("profileId") String profileId);
	public Profile addProfiles(Profile profile);
	public Profile updateProfiles(@PathVariable("profileId") String profileId,Profile profile) ;
	public void deleteProfileById(@PathVariable("profileId") String profileId);
	
}
