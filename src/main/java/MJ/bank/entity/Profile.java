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
    String fileName;
    String fileType;
    Long fileSize;


    public Profile(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Profile() {

    }
}
