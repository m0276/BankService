package MJ.bank.service;


import MJ.bank.dto.ProfileDto;
import MJ.bank.entity.Profile;
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


//profile image를 어떻게 저장할것인지?
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final ProfileStorage profileStorage;

  public ResponseEntity<?> save(MultipartFile file , ProfileDto dto){

    try {
      Profile profile = new Profile(dto.employeeId());
      profile.setFileSize(file.getSize());
      profile.setFileName(file.getName());
      profile.setFileType(file.getContentType());

      profileStorage.saveFile(profile,file.getBytes());

      return ResponseEntity.status(HttpStatus.CREATED).body(profile);

    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
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
