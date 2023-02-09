package com.example.codebase.Model.Dao;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "share")
@Getter
@Setter
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Share {
    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //회원가입시 1 2 3 자동 추가
    private Long uuid;

    @Column(name = "uri")
    private String uri;
}
