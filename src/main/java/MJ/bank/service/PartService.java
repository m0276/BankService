package MJ.bank.service;


import MJ.bank.component.CheckNullField;
import MJ.bank.dto.PartDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.request.PartCreateRequest;
import MJ.bank.dto.request.PartUpdateRequest;
import MJ.bank.dto.response.CursorPageResponsePartDto;
import MJ.bank.entity.Employee;
import MJ.bank.entity.Part;
import MJ.bank.mapper.PartMapper;
import MJ.bank.repository.PartRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class PartService {

  private final PartRepository partRepository;
  private final PartMapper partMapper;
  private final CheckNullField checkNullField;

  public PartDto creat(PartCreateRequest createRequest){
    if(checkNullField.hasNullFields(createRequest) != null) throw new NullPointerException(
        checkNullField.hasNullFields(createRequest));

    Part part = new Part(createRequest.partName(),createRequest.explanation(),createRequest.establish());
    partRepository.save(part);

    return partMapper.toDto(part);
  }

  public PartDto update(PartUpdateRequest updateRequest){
    if(partRepository.findByPartName(updateRequest.partName()).isEmpty()){
      throw new NoSuchElementException(updateRequest.partName()+"을/를 찾을 수 없습니다.");
    }

    Part part = partRepository.findByPartName(updateRequest.partName()).get();

    String newPartName = updateRequest.newPartName();
    String explanation = updateRequest.explanation();
    LocalDate establish = updateRequest.establish();

    if(newPartName != null){
      if(partRepository.findByPartName(newPartName).isEmpty()) part.setPartName(newPartName);
      else{
        throw new EntityExistsException(newPartName + "은/는 이미 존재합니다.");
      }
    }

    if(explanation != null) part.setExplanation(explanation);

    if(establish != null) part.setEstablish(establish);

    partRepository.save(part);

    return partMapper.toDto(part);
  }

  public void delete(String partName){
    if(partRepository.findByPartName(partName).isEmpty()){
      throw new NoSuchElementException(partName+"을/를 찾을 수 없습니다.");
    }

    Part part = partRepository.findByPartName(partName).get();
    if(!part.getEmployees().isEmpty()) throw new IllegalArgumentException("해당 부서에 존재하는 직원이 있습니다.");
      //return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.PRECONDITION_FAILED.value(), "잘못된 요청입니다.",partName+"에 직원이 존재합니다."));

    partRepository.delete(part);
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

  public CursorPageResponsePartDto getParts(CursorPageRequest request, Pageable page) {
    final List<PartDto> parts = getPart(request, page);
    final String lastIdOfList = parts.isEmpty() ?
        null : parts.getLast().partName();


    return new CursorPageResponsePartDto(parts, lastIdOfList
        ,lastIdOfList,10,(long) parts.size(),hasNextInBackup(lastIdOfList));
  }

  private List<PartDto> getPart(CursorPageRequest request, Pageable page) {
    List<PartDto> list = new ArrayList<>();
    if(request.getCursorId() == null){
      list = partRepository.searchAll(request,page).getContent();
    }
    else{
      Slice<Part> parts = partRepository.findAllByPartName(request.getPartName(), page);
      for(Part p : parts){
        list.add(partMapper.toDto(p));
      }
    }

    //if(list.isEmpty()) throw  new NoSuchElementException("파트를 찾을 수 없습니다.");

    return list;
  }

  private Boolean hasNextInBackup(String id) {
    if (id == null) return false;
    return partRepository.existsPartByPartName(id);
  }

  public List<Part> findAll(){
    return partRepository.findAll();
  }

//  public void joinEmployee(Employee employee,String partName){
//    if(partRepository.findByPartName(partName).isEmpty()){
//      throw new NoSuchElementException(partName+"을/를 찾을 수 없습니다.");
//    }
//    Part part = partRepository.findByPartName(partName).get();
//    List<Employee> list = part.getEmployees();
//
//    list.add(employee);
//    part.setEmployees(list);
//    partRepository.save(part);
//  }

}
