package com.github.reenatobruno.parts_api.entity;

import com.github.reenatobruno.parts_api.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    private static final int MAX_NAME_LENGTH = 60;

    private static final int MAX_DOC_LENGTH = 11;

    private static final int MAX_DOC_EMAIL_LENGTH = 150;

    private static final int MAX_PASSWORD_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name", nullable = false, length = MAX_NAME_LENGTH)
    private String userName;

    @Column(name = "user_Cpf", nullable = false, unique = true, length = MAX_DOC_LENGTH)
    private String userCpf;

    @Column(name = "user_email", nullable = false, unique = true, length = MAX_DOC_EMAIL_LENGTH)
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
    private Instant createdA;

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
        setEncodedPassword(userPassword);
        this.accountNonBlocked = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountEnabled = true;
    }

    private void setUserName(String userName) {
    }

    private void setUserCpf(String userDoc) {
    }

    private void setUserEmail(String userEmail) {
    }

    private void setEncodedPassword(String userPassword) {
    }

    public void disable() {
        this.accountEnabled = false;
    }

    public void updateFields(String userName, String userEmail) {

        setUserName(userName);
        setUserEmail(userEmail);
    }

    public void changePassword(String newEncodedPassword) {
        setEncodedPassword(newEncodedPassword);
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
