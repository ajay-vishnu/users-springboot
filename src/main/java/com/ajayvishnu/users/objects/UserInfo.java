package com.ajayvishnu.users.objects;

import com.ajayvishnu.users.attribute.DefaultColumns;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "UserInfo")
@Table(
        name = "userinfo",
        uniqueConstraints = @UniqueConstraint(name = "unique_username_fk", columnNames = "username")
)
@Getter @Setter @NoArgsConstructor
public class UserInfo extends DefaultColumns {
    @Column(
            name = "username",
            nullable = false
    )
    private String username;
    @Column(
            name = "firstname",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "middlename",
            nullable = false
    )
    private String middleName;
    @Column(
            name = "lastname",
            nullable = false
    )
    private String lastName;
    @Column(
            name = "address"
    )
    private String address;
    @Column(
            name = "phone",
            nullable = false
    )
    private String phone;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    public UserInfo(String username,
                    String firstName,
                    String middleName,
                    String lastName,
                    String address,
                    String phone,
                    String email,
                    String createdBy) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.updatedBy = createdBy;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return username.equals(userInfo.username) && firstName.equals(userInfo.firstName) && Objects.equals(middleName, userInfo.middleName) && lastName.equals(userInfo.lastName) && address.equals(userInfo.address) && phone.equals(userInfo.phone) && email.equals(userInfo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, middleName, lastName, address, phone, email);
    }
}
