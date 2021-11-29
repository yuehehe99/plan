package com.example.myplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "name can not be blank!")
    private String name;

    @NotBlank(message = "password can not be blank!")
    private String password;

    @JsonIgnore
    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleted;

    @JsonIgnore
    @Column(columnDefinition = "TINYINT(1)")
    private boolean gender;

    @OneToMany(cascade = ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id")
    private List<Authority> authorities;
}
