package com.api.vaccinationmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass //để có thể xế thừa các column
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseModel {
    @Id //Not null. PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Tự động tăng id
    @Setter(AccessLevel.NONE)
    private Integer id;
}
