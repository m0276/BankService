package MJ.bank.storage;

import MJ.bank.entity.BackupLog;
import MJ.bank.entity.BackupStatus;
import MJ.bank.entity.Profile;
import MJ.bank.repository.BackupLogRepository;
import MJ.bank.repository.ProfileRepository;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ProfileStorage {
  private final String path = "C:\\Users\\user\\IdeaProjects\\study\\bank\\src\\main\\resources";
  private final ProfileRepository profileRepository;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Path.of(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Profile saveFile(Profile profile, byte[] fileData) throws IOException {
    Objects.requireNonNull(profile.getFileName(), "File name cannot be null");

    Path dirPath = Paths.get(path);
    if (!Files.exists(dirPath)) {
      Files.createDirectories(dirPath);
    }

    Path filePath = Paths.get(path, profile.getFileName());

    try {
      Files.write(filePath, fileData);

      return profileRepository.save(profile);
    } catch (IOException e) {

      throw new IOException("Failed to save file: " + filePath, e);
    }
  }


  public Optional<Profile> getFileMeta(Long id) {
    return profileRepository.findById(id);
  }

  public byte[] getFile(Long id) throws IOException {
    Optional<Profile> profile = getFileMeta(id);
    if (profile.isPresent()) {
      Path filePath = Paths.get(path + profile.get().getFileName());
      return Files.readAllBytes(filePath);
    }
    return null;
  }

  public void deleteFile(Long id){
    try{
      Optional<Profile> profile = getFileMeta(id);
      if(profile.isPresent()){
        Path filePath = Paths.get(path + profile.get().getFileName());
        Files.delete(filePath);
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }

}
