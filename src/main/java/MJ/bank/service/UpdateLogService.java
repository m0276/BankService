package MJ.bank.service;


import MJ.bank.entity.UpdateLog;
import MJ.bank.entity.UpdateType;
import MJ.bank.repository.UpdateLogRepository;
import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateLogService {
  private final UpdateLogRepository updateLogRepository;

  public void save(UUID employeeId, UpdateType updateType, Object prevContent, Object currContent, String memo){
    try{
      UpdateLog updateLog = new UpdateLog(updateType,employeeId,prevContent,currContent,memo,
          InetAddress.getLocalHost().getHostAddress(),
          LocalDateTime.now());

      updateLogRepository.save(updateLog);
    } catch (UnknownHostException e){
      e.printStackTrace();
    }
  }
}
