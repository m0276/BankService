package MJ.bank.service;


import MJ.bank.CheckNullField;
import MJ.bank.dto.PartDto;
import MJ.bank.dto.request.PartCreateRequest;
import MJ.bank.dto.request.PartUpdateRequest;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.entity.Part;
import MJ.bank.mapper.PartMapper;
import MJ.bank.repository.PartRepository;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class PartService {

  private final PartRepository partRepository;
  private final PartMapper partMapper;
  private final CheckNullField checkNullField;

  public ResponseEntity<?> creat(PartCreateRequest createRequest){
    if(checkNullField.hasNullFields(createRequest).hasBody()) return checkNullField.hasNullFields(createRequest);

    Part part = new Part(createRequest.partName(),createRequest.explanation(),createRequest.establish());
    partRepository.save(part);

    return ResponseEntity.status(HttpStatus.CREATED).body(partMapper.toDto(part));
  }

  public Object update(PartUpdateRequest updateRequest){
    if(partRepository.findByPartName(updateRequest.partName()).isEmpty()){
      return new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.",updateRequest.partName()+"을 찾을 수 없습니다.");
    }

    Part part = partRepository.findByPartName(updateRequest.partName()).get();

    String newPartName = updateRequest.newPartName();
    String explanation = updateRequest.explanation();
    LocalDate establish = updateRequest.establish();

    if(newPartName != null){
      if(partRepository.findByPartName(newPartName).isEmpty()) part.setPartName(newPartName);
      else{
        return new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.",updateRequest.newPartName()+"가 이미 존재합니다.");
      }
    }

    if(explanation != null) part.setExplanation(explanation);

    if(establish != null) part.setEstablish(establish);

    partRepository.save(part);

    return partMapper.toDto(part);
  }

  public ResponseEntity<?> delete(String partName){
    if(partRepository.findByPartName(partName).isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",partName+"을 찾을 수 없습니다.")
    );
    }

    Part part = partRepository.findByPartName(partName).get();
    if(!part.getEmployees().isEmpty()) return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.PRECONDITION_FAILED.value(), "잘못된 요청입니다.",partName+"에 직원이 존재합니다.")
    );

    partRepository.delete(part);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public List<PartDto> findInfo(String finding){
    List<Part> allPartList = partRepository.findAll();
    List<PartDto> result = new ArrayList<>();

    for(Part part : allPartList){
      if(part.getPartName().contains(finding) || part.getExplanation().contains(finding)) {
        result.add(partMapper.toDto(part));
      }
    }

    return result;
  }

  Part findPart(String partName){
    if(partRepository.findByPartName(partName).isEmpty()) return null;

    return partRepository.findByPartName(partName).get();
  }

}
