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
    String employeeEmail;
    String imageUrl;

    public Profile(String employeeEmail, String imageUrl) {
        this.employeeEmail = employeeEmail;
        this.imageUrl = imageUrl;
    }

    public Profile() {

    }
}
