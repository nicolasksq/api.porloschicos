package ar.com.porloschicos.backend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserDao {

    public UserDao() {}

    public UserDao(String username, String password, String email, int du) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.du = du;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @Column
    @JsonIgnore
    @NotNull
    private String password;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private int du;

    @Column
    private Timestamp created_at;

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                '}';
    }
}
