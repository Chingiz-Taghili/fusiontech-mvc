package com.multishop.fusiontech.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();
}
