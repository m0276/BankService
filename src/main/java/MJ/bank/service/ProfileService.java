package MJ.bank.service;


import MJ.bank.dto.ProfileDto;
import MJ.bank.entity.Profile;
import MJ.bank.mapper.ProfileMapper;
import MJ.bank.repository.ProfileRepository;
import MJ.bank.storage.ProfileStorage;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final ProfileStorage profileStorage;
  private final ProfileMapper mapper;

  public ProfileDto save(MultipartFile file) throws IOException {


      Profile profile = new Profile();
      profile.setFileSize(file.getSize());
      profile.setFileName(file.getName());
      profile.setFileType(file.getContentType());

      profileStorage.saveFile(profile,file.getBytes());

      return mapper.toDto(profile);


  }

  public void delete(Long id){
    profileRepository.deleteById(id);
  }

  public void update(Long id){
    Profile profile;
    if(profileRepository.findById(id).isEmpty()){
      profile = new Profile();
    }
    else profile = profileRepository.findById(id).get();

    profileRepository.save(profile);
  }

}
