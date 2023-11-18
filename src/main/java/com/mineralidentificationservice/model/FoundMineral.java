package com.mineralidentificationservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FoundMineral")
public class FoundMineral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MINERAL_ID", columnDefinition = "VARCHAR(70)")
    private String mineralId;
    @Column(name = "ACCOUNT_ID", columnDefinition = "VARCHAR(10)")
    private String accountId;
    @Column(name = "COMMENT_ID", columnDefinition = "VARCHAR(30)")
    private BigDecimal commentId;
    @Column(name = "DISCOVERY_PLACE", columnDefinition = "VARCHAR(30)")
    private LocalDateTime discoveryPlace;

}
