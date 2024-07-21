package com.tosan.tools.tracker.sample.setting.entity;

import com.tosan.tools.tracker.starter.model.ServiceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
@Entity
@Table(name = "SERVICE", indexes = @Index(name = "PK_SERVICE", columnList = "ID", unique = true))
@Setter
@Getter
public class Service implements ServiceEntity {

    @Id
    @Column(name = "ID", columnDefinition = "NUMBER(19)")
    private Long id;

    @Column(name = "SERVICE_NAME", columnDefinition = "NVARCHAR2(100)", nullable = false, unique = true)
    private String serviceName;

    @Column(name = "TITLE", columnDefinition = "NVARCHAR2(150)", nullable = false)
    private String title;
}
