package simdo.module.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "user")
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    private LocalDateTime joinedAt;

    private String generatedEmailToken;

    private LocalDateTime emailTokenGeneratedAt;

    private boolean isValid;


    public void processJoin(){
        generateEmailToken();
        enterJoinedAtTime();
    }


    public void generateEmailToken() {
        this.generatedEmailToken = UUID.randomUUID().toString();
        this.emailTokenGeneratedAt = LocalDateTime.now();
    }

    public void enterJoinedAtTime(){
        this.joinedAt = LocalDateTime.now();
    }

}
