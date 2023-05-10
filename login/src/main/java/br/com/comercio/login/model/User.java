package br.com.comercio.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_COMERCIO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{
    @Id
    @Column(name="username")
    private String username;

    @Column(name="pass")
    private String password;

    @Column(name="keypass")
    private String keypass;

}
