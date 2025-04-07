package MJ.bank.service;


import MJ.bank.dto.BackupDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.BackupStatus;
import MJ.bank.mapper.BackupLogMapper;
import MJ.bank.repository.BackupLogRepository;
import MJ.bank.storage.BackupStorage;
import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.beans.container.spi.BeanContainer.LifecycleOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BackupService {

  @Value("${spring.datasource.url}")
  String DB_URL;

  @Value("${spring.datasource.username}")
  String USER;

  @Value("${spring.datasource.password}")
  String PASS;

  private Integer previousRowCount;
  private final BackupStorage backupStorage;
  private final BackupLogRepository backupLogRepository;
  private final BackupLogMapper backupLogMapper;

  Long backupFileNumber = 1L;

  @Scheduled(cron = "0 0 * * * *")
  public void backup(){
    LocalDateTime start = LocalDateTime.now();
    BackupStatus backupStatus = null;
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      String query = "SELECT COUNT(*) FROM update_log";

      try (PreparedStatement pstmt = conn.prepareStatement(query);
          ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          int currentRowCount = rs.getInt(1);
          if(previousRowCount != null){
            if (currentRowCount > previousRowCount) {

              backupStorage.creatCSV("Backup" + backupFileNumber,"System", LocalDateTime.now(),
                  BackupStatus.Complete);
              backupStatus = BackupStatus.Complete;

            }
            else{

              backupStorage.creatCSV("Backup"+ backupFileNumber,"System", LocalDateTime.now(),
                  BackupStatus.Skip);
              backupStatus = BackupStatus.Skip;
            }
            previousRowCount = currentRowCount;

          }
          else{
            backupStorage.creatCSV("Backup" + backupFileNumber,"System", LocalDateTime.now(),
                BackupStatus.Complete);
          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      backupStatus = BackupStatus.Fail;
    }
    backupFileNumber += 1;
    BackupLog backupLog = new BackupLog("system",start,LocalDateTime.now(),backupStatus,backupFileNumber);
    backupLogRepository.save(backupLog);
  }

  public void create(){
    LocalDateTime start = LocalDateTime.now();
    BackupStatus backupStatus = null;
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      String query = "SELECT COUNT(*) FROM update_log";
      try (PreparedStatement pstmt = conn.prepareStatement(query);
          ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          int currentRowCount = rs.getInt(1);
          if(previousRowCount != null){
            if (currentRowCount > previousRowCount) {

              backupStorage.creatCSV("Backup" + backupFileNumber,InetAddress.getLocalHost().getHostAddress(), LocalDateTime.now(),
                  BackupStatus.Complete);
              backupStatus = BackupStatus.Complete;
            }
            else{
              backupStorage.creatCSV("Backup" + backupFileNumber,InetAddress.getLocalHost().getHostAddress(), LocalDateTime.now(),
                  BackupStatus.Skip);
              backupStatus = BackupStatus.Skip;
            }
            previousRowCount = currentRowCount;
          }
          else{
            backupStorage.creatCSV("Backup" + backupFileNumber,InetAddress.getLocalHost().getHostAddress(), LocalDateTime.now(),
                BackupStatus.Complete);
            backupStatus = BackupStatus.Complete;
          }
        }
        backupFileNumber += 1;
      }

    } catch (Exception e) {
      e.printStackTrace();
      backupStatus = BackupStatus.Fail;
    }

    BackupLog backupLog = new BackupLog("system",start,LocalDateTime.now(),backupStatus,backupFileNumber);
    backupLogRepository.save(backupLog);
  }

  public LocalDateTime lastBackupTime(){
    if(backupLogRepository.findAll().isEmpty()) return null;
    return backupLogRepository.findAll(Sort.by(Direction.ASC,"endTime")).getLast().getEndTime();
  }
}
