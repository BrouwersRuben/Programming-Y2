package be.kdg.java2.project.domain;

import javax.persistence.*;

@Entity
@Table(name = "application_user")
public class User extends EntityClass{
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "architectFirm_id")
    private Architect workingFirm;


    protected User() {
    }

    public User(String username, String email, Role role, String password, Architect workingFirm) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.workingFirm = workingFirm;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Architect getWorkingFirm() {
        return workingFirm;
    }

    public void setWorkingFirm(Architect workingFirm) {
        this.workingFirm = workingFirm;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
