package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.enums.UserRole;
import com.github.reenatobruno.parts_api.util.UserDomainValidation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    private static final int MAX_NAME_LENGTH = 60;

    private static final int MAX_CPF_LENGTH = 11;

    private static final int MAX_EMAIL_LENGTH = 150;

    private static final int MAX_PASSWORD_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_name", nullable = false, length = MAX_NAME_LENGTH)
    private String userName;

    @Column(name = "user_cpf", nullable = false, unique = true, length = MAX_CPF_LENGTH)
    private String userCpf;

    @Column(name = "user_email", nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = MAX_PASSWORD_LENGTH)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "account_non_blocked", nullable = false)
    private boolean accountNonBlocked = true;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(name = "account_enabled", nullable = false)
    private boolean accountEnabled = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    public UserEntity(String userName, String userCpf, String userEmail, String userPassword) {

        setUserName(userName);
        setUserCpf(userCpf);
        setUserEmail(userEmail);
        setPassword(userPassword);
        this.userRole = UserRole.USER;
        this.accountNonBlocked = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountEnabled = true;
    }

    public UUID getUserId() {return userId; }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonBlocked() {
        return accountNonBlocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    private void setUserName(String userName) {
        String normalizedUserName = UserDomainValidation.normalize(userName);
        this.userName = UserDomainValidation.requireNonBlank(normalizedUserName, "User name", MAX_NAME_LENGTH);
    }

    private void setUserCpf(String userCpf) {
        String normalizedCpf = UserDomainValidation.normalize(userCpf);
        this.userCpf = UserDomainValidation.requireCpf(normalizedCpf, "CPF", MAX_CPF_LENGTH);
    }

    private void setUserEmail(String userEmail) {
        String normalizedEmail = UserDomainValidation.normalize(userEmail);
        String lowerCaseEmail = normalizedEmail != null ? normalizedEmail.toLowerCase() : null;
        this.userEmail = UserDomainValidation.requireEmail(lowerCaseEmail, "E-mail", MAX_EMAIL_LENGTH);
    }

    private void setPassword(String userPassword) {
        this.userPassword = UserDomainValidation.requireNonBlank(userPassword, "Password", MAX_PASSWORD_LENGTH);
    }

    public void disable() {
        this.accountEnabled = false;
    }

    public void updateFields(String userName, String userEmail) {

        setUserName(userName);
        setUserEmail(userEmail);
    }

    public void changePassword(String newEncodedPassword) {
        setPassword(newEncodedPassword);
    }

    public void promoteToAdmin() {
        this.userRole = UserRole.ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity user = (UserEntity) o;
        return userCpf != null && userCpf.equals(user.getUserCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCpf);
    }
}
