package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Profile {
    @Id
    Long id;
    Long employeeId;
    String imageUrl;

    public Profile(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Profile() {

    }
}
