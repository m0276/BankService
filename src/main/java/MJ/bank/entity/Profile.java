package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Profile {
    @Id
    Long id;

    String imageUrl;
}
