package com.example.myplan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
@IdClass(Authority.AuthorityId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    private String username;
    @Id
    private String authority;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorityId implements Serializable {
        private String username;
        private String authority;
    }
}
