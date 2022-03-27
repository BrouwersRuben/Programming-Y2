package be.kdg.java2.project.domain;

import javax.persistence.*;

@Entity
@Table(name = "APPLICATION_USERS")
public class User extends EntityClass{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "USER_USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Column(name = "USER_ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @ManyToOne()
    @JoinColumn(name = "architectFirm_id", nullable = false)
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
