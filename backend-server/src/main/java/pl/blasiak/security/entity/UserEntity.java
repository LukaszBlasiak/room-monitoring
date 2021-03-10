package pl.blasiak.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private boolean active;

    // According to the servlet legacy implementation there is no point of using char array instead of strings
    // due to String type usage in HTTP parameters, see: https://github.com/spring-projects/spring-security/issues/3238
    private String password;


}
