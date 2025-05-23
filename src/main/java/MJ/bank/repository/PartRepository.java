package MJ.bank.repository;

import MJ.bank.entity.Part;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part,String>,PartRepositoryCustom {

  Optional<Part> findByPartName(String partName);

  Slice<Part> findAllByPartName(String partName, Pageable pageable);

  Boolean existsPartByPartName(String partName);
}
