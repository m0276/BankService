package MJ.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "profile")
public class Profile { //메타 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "file_name")
    String fileName;
    @Column(name = "file_type")
    String fileType;
    @Column(name = "file_size")
    Long fileSize;

    public Profile() {

    }
}
