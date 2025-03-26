package MJ.bank.repository;

import MJ.bank.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  void deleteById(Long id);

  Optional<Profile> findById(Long id);
}
