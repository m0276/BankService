package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Profile { //메타 데이터
    @Id
    Long id;
    String fileName;
    String fileType;
    Long fileSize;

    public Profile() {

    }
}
