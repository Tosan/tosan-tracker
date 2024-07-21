package com.tosan.tools.tracker.sample.setting.entity;

import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.ServiceEntity;
import com.tosan.tools.tracker.starter.model.TrackDataConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author M.khoshnevisan
 * @since 7/15/2023
 */
@Entity
@Table(name = "REQUEST_TRACK",
        indexes = {
                @Index(name = "PK_REQUEST_TRACK", columnList = "ID", unique = true),
                @Index(name = "IDX_REQUEST_TRACK_TOKEN_HASH", columnList = "TOKEN_HASH")})
@Setter
@Getter
public class RequestTrack implements RequestTrackEntity {

    private static final String sequenceName = "NGNQ_REQUEST_TRACK_ID";

    @Id
    @Column(name = "ID", columnDefinition = "NUMBER(19)")
    @SequenceGenerator(name = "RequestTrackSequenceGenerator", sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(generator = "RequestTrackSequenceGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "REQUEST_DATE", columnDefinition = "TIMESTAMP", nullable = false)
    private Date requestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICE_ID", columnDefinition = "NUMBER(19)", foreignKey = @ForeignKey(name = "FK_REQUEST_TRACK_SERVICE_ID"))
    private Service service;

    @Column(name = "X_REQUEST_ID", columnDefinition = "NVARCHAR2(40)")
    private String requestId;

    @Column(name = "REQUESTED_AMOUNT", columnDefinition = "NUMBER(19)")
    private BigDecimal requestedAmount;

    @Column(name = "INSTANCE_NUMBER", columnDefinition = "NUMBER(19)", nullable = false)
    private Long instanceNumber;

    @Column(name = "TOKEN_HASH", columnDefinition = "NVARCHAR2(50)")
    private String tokenHash;

    @Column(name = "CLIENT_ADDRESS", columnDefinition = "NVARCHAR2(50)")
    private String clientAddress;

    @OneToMany(mappedBy = "requestTrack", fetch = FetchType.LAZY)
    private List<ResponseTrack> responseTrackList;

    @Column(name = "TRACK_DATA", columnDefinition = "CLOB", updatable = false)
    @Lob
    @Convert(converter = TrackDataConverter.class)
    private Map<String, Object> trackData;

    public void setService(ServiceEntity service) {
        this.service = (Service) service;
    }

    public void setResponseTrackList(List<ResponseTrackEntity> responseTrackList) {
        this.responseTrackList = responseTrackList.stream().map(rt -> (ResponseTrack) rt).collect(Collectors.toList());
    }
}
