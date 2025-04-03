package MJ.bank.service;


import MJ.bank.dto.UpdateLogDto;
import MJ.bank.entity.UpdateLog;
import MJ.bank.entity.UpdateType;
import MJ.bank.mapper.UpdateLogMapper;
import MJ.bank.repository.UpdateLogRepository;
import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateLogService {
  private final UpdateLogRepository updateLogRepository;
  private final UpdateLogMapper updateLogMapper;

  public void save(Long employeeId, String propertyName,UpdateType updateType, Object prevContent, Object currContent, String memo){
    try{
        UpdateLog updateLog = new UpdateLog(updateType,employeeId,propertyName,prevContent.toString(),currContent.toString(),memo,
            InetAddress.getLocalHost().getHostAddress(),
            LocalDateTime.now());

        updateLogRepository.save(updateLog);

    } catch (UnknownHostException e){
      e.printStackTrace();
    }
  }

  public UpdateLogDto findById(Long id){
    if(updateLogRepository.findById(id).isEmpty()) throw new NoSuchElementException("해당하는 업데이트 로그가 없습니다.");
    return updateLogMapper.toDto(updateLogRepository.findById(id).get());
  }

  public Integer getSize(){
    return updateLogRepository.findAll().size();
  }
}
