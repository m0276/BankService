package MJ.bank.storage;

import MJ.bank.entity.BackupLog;
import MJ.bank.entity.BackupStatus;
import MJ.bank.repository.BackupLogRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackupStorage {
  private final String path = "C:\\Users\\user\\IdeaProjects\\study\\bank\\src\\main\\resources";
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final BackupLogRepository backupLogRepository;

  public void creatCSV(String title,String worker, LocalDateTime start, BackupStatus backupStatus){
    Long fileNumber = Long.parseLong(title.substring(5));

    try{

      BufferedWriter fw = new BufferedWriter(new FileWriter(path + title + ".csv",true));

      fw.write(worker+ "," + start.format(formatter) + ","+ LocalDateTime.now().format(formatter) + "," + BackupStatus.Complete);
      fw.newLine();
      fw.flush();
      fw.close();

      BackupLog backup = new BackupLog(worker,start,LocalDateTime.now(),BackupStatus.Complete,fileNumber);

      backupLogRepository.save(backup);

    } catch (IOException e){
        backupStatus = BackupStatus.Fail;
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(path + title + ".csv", true))) {
          fw.write(worker + "," + start.format(formatter) + "," + LocalDateTime.now().format(formatter) + "," + backupStatus);
          fw.newLine();
          fw.flush();
          fw.close();
          BackupLog backup = new BackupLog(worker,start,LocalDateTime.now(),BackupStatus.Fail,fileNumber);

          backupLogRepository.save(backup);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
  }


  public List<List<Object>> CSVReader(String title) {
    List<List<Object>> returnList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(path + title + ".csv"))) {
      String line;
      List<Object> list = new ArrayList<>();

      while ((line = br.readLine()) != null) {

        String[] values = line.split(",");

        for(int i = 0 ; i < values.length ; i++){
          if(i == 1 || i == 2){
            list.add(LocalDateTime.parse(values[i],formatter));
          }
          else if(i == 3){
            list.add(BackupStatus.valueOf(values[i]));
          }
          else{
            list.add(values[i]);
          }
        }

        returnList.add(list);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return returnList;
  }


}
