package MJ.bank.service;


import MJ.bank.entity.Backup;
import MJ.bank.entity.BackupStatus;
import MJ.bank.storage.BackupStorage;
import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

  @Scheduled
  public void run(){
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      String query = "SELECT COUNT(*) FROM update_log";

      try (PreparedStatement pstmt = conn.prepareStatement(query);
          ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          int currentRowCount = rs.getInt(1);
          if (currentRowCount > previousRowCount) {

            backupStorage.creatCSV("Backup","System", LocalDateTime.now(),
                 BackupStatus.Processing);

          }
          else{

            backupStorage.creatCSV("Backup","System", LocalDateTime.now(),
                BackupStatus.Skip);
          }
          previousRowCount = currentRowCount;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void create(){
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      String query = "SELECT COUNT(*) FROM update_log";
      try (PreparedStatement pstmt = conn.prepareStatement(query);
          ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          int currentRowCount = rs.getInt(1);
          if (currentRowCount > previousRowCount) {

            backupStorage.creatCSV("Backup",InetAddress.getLocalHost().getHostAddress(), LocalDateTime.now(),
                BackupStatus.Processing);
          }
          previousRowCount = currentRowCount;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
