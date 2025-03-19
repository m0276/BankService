package MJ.bank.component;


import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CursorResult<T> {
  private List<T> values;
  private Boolean hasNext;

  public CursorResult(List<T> values, Boolean hasNext) {
    this.values = values;
    this.hasNext = hasNext;
  }
}
