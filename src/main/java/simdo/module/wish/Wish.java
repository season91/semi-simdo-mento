package simdo.module.wish;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "USER_WISHMV")
public class Wish {

    @Id
    @Column(nullable = false, name = "WISH_NO")
    private long wishNo;

    @Column(nullable = false)
    private String mvNo;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private LocalDate wishRegDate;

    @Column(nullable = false)
    private long userNo;

}
