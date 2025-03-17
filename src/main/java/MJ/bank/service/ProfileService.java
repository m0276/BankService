package MJ.bank.service;


import MJ.bank.entity.Profile;
import MJ.bank.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//profile image를 어떻게 저장할것인지?
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;

  public void save(Long id){
    Profile profile = new Profile(id);
    profileRepository.save(profile);
  }

  public void delete(Long id){
    profileRepository.deleteByEmployeeId(id);
  }

  public void update(Long id){
    Profile profile;
    if(profileRepository.findByEmployeeId(id).isEmpty()){
      profile = new Profile(id);
    }
    else profile = profileRepository.findByEmployeeId(id).get();

    profileRepository.save(profile);
  }

}
