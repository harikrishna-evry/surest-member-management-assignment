package com.surest.member.entity;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Version
    @Column(name = "version")
    private Long version;

}
