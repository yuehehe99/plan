package com.example.myplan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "name can not be blank!")
    private String name;

    @JsonIgnore
    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleted;

    @JsonIgnore
    @Column(columnDefinition = "TINYINT(1)")
    private boolean gender;
//
//    @OneToMany(targetEntity = Todo.class, cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
//    @JoinColumn(name = "users_id", referencedColumnName = "id")
//    private List<Todo> todo;

}
