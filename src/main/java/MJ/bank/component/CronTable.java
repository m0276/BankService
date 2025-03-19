package MJ.bank.component;


import MJ.bank.service.BackupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CronTable {
  private final BackupService backupService;

  @Value("${schedule.use}")
  private boolean useSchedule;

  @Scheduled(cron = "${schedule.cron}")
  public void mainJob() {
    try {
      if (useSchedule) {
        backupService.run();
      }
    } catch (Exception e) {
      log.info("* Batch 시스템이 예기치 않게 종료되었습니다. Message: {}", e.getMessage());
    }
  }
}
