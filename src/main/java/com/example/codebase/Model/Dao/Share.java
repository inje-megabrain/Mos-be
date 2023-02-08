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
    private String uuid;

    @Column(name = "uri")
    private String uri;
}
