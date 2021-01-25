package com.pack.Profileservice.Service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.pack.Profileservice.Dao.ProfileRepository;
import com.pack.Profileservice.Entity.Profile;
import com.pack.Profileservice.error.ProfileListEmptyException;
import com.pack.Profileservice.error.ProfileNotFoundException;
import com.pack.Profileservice.error.ProfileSpecificationsMissingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class ProfileServiceImpl implements ProfileService{
	@Autowired 
	private ProfileRepository profileRepository;
	
	@Override
	@Transactional
	//@Cacheable(value="profile")
	public List<Profile> getAllProfiles()
	{
		List<Profile> profileList = (List<Profile>) profileRepository.findAll();
		if (profileList.size() == 0) {
			throw new ProfileListEmptyException();
		}
		else
		return profileList;
	}
	
	
	@Override
	@Transactional
	@Cacheable(value="profile",key="#profileId")
	public Profile getProfileById(@PathVariable("profileId") String profileId) 
	{
		Profile profile = profileRepository.findById(profileId).orElse(null);
		//List<Profile> profileList = (List<Profile>) profileRepository.findAll();
		//Profile profileFound = profileList.stream().filter(p -> p.getProfileId() == (profileId)).findFirst().get();
		if (profile != null) {
			log.info("PROFILE BY ID FETCHED FROM DB");
			return profile;
		}
		else {
			log.info("PROFILE NOT FOUND");
			throw new ProfileNotFoundException();
	}
	}
	
	
	
	
	@Override
	@Transactional
	@CachePut(value="profile",key="#profile.profileId")
	public Profile addProfiles(Profile profile) 
	{
		if ((profile.getProfileId().isEmpty())||(profile.getProfileStatus().isEmpty())||(profile.getProfileType().isEmpty())||(profile.getCreatedBy().isEmpty())||
		(profile.getLastUpdatedBy().isEmpty())||(profile.getOrderss().isEmpty())||(profile.getCollectionRules().isEmpty()))
		{
			throw new ProfileSpecificationsMissingException();
		}
		else
		profileRepository.save(profile);
		return profile;
	}
	
	@Override
	@Transactional
	@CachePut(value="profile",key="#profileId")
	public Profile updateProfiles(@PathVariable("profileId") String profileId,Profile profile) 
	{
		if ((profile.getProfileId().isEmpty())||(profile.getProfileStatus().isEmpty())||(profile.getProfileType().isEmpty())||(profile.getCreatedBy().isEmpty())||
				(profile.getLastUpdatedBy().isEmpty())||(profile.getOrderss().isEmpty())||(profile.getCollectionRules().isEmpty())){
			throw new ProfileSpecificationsMissingException();}
			else
		profileRepository.save(profile);
		return profile;
	}
	
	@Override
	@Transactional
	@CacheEvict(value="profile",allEntries = true)
	public void deleteProfileById(@PathVariable("profileId") String profileId) 
	{
		Profile profile = profileRepository.findById(profileId).orElse(null);
		if (profile == null) {
			throw new ProfileNotFoundException();
		}
		else
		profileRepository.deleteById(profileId);
	}
	
}
