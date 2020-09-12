package ar.com.porloschicos.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class UserDao {

    public UserDao(String username, String password, String email, int du) {
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || du == 0) {
            throw new IllegalArgumentException("REQUEST_INCOMPLETE");
        }
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

    public long getId() { return id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getDu() { return du; }

    public void setDu(int du) { this.du = du; }

    public Timestamp getCreated_at() { return created_at; }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }


    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                '}';
    }
}
