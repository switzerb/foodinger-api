package com.brennaswitzer.foodinger.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {

    @Id
    private Long id;

    @NonNull
    @Column(unique = true)
    private String username;

}
