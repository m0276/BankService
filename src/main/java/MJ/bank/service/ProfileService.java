package MJ.bank.service;


import MJ.bank.entity.Profile;
import MJ.bank.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;

  public void save(String email, String url){
    Profile profile = new Profile(email,url);
    profileRepository.save(profile);
  }

  public void delete(String email){
    profileRepository.deleteByEmployeeEmail(email);
  }

  public void update(String email, String url){
    Profile profile;
    if(profileRepository.findByEmployeeEmail(email).isEmpty()){
      profile = new Profile(email,url);
    }
    else profile = profileRepository.findByEmployeeEmail(email).get();
    profile.setImageUrl(url);

    profileRepository.save(profile);
  }

}
