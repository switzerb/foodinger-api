package com.brennaswitzer.foodinger.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk__eqkey", columnNames = "_eqkey"),
        @UniqueConstraint(name = "uk_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_provider_id", columnNames = {"provider", "providerId"})
})
// Spring wants `providerId` in the UK def, even though it should be `provider_id`
// I think it's trying to be clever? But it's violating rules.
@SuppressWarnings("JpaDataSourceORMInspection")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String imageUrl;

}
