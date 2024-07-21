package com.tosan.tools.tracker.sample.setting.entity;

import com.tosan.tools.tracker.starter.model.RequestTrackEntity;
import com.tosan.tools.tracker.starter.model.ResponseTrackEntity;
import com.tosan.tools.tracker.starter.model.TrackDataConverter;
import com.tosan.tools.tracker.starter.model.TrackType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author F.Ebrahimi
 * @since 12/11/2023
 */
@Entity
@Table(name = "RESPONSE_TRACK",
        indexes = {
                @Index(name = "PK_RESPONSE_TRACK", columnList = "ID, REQUEST_DATE, INSTANCE_NUMBER", unique = true),
                @Index(name = "FK_RESPONSE_TRACK_REQUEST_ID", columnList = "REQUEST_ID")})
@Setter
@Getter
public class ResponseTrack implements ResponseTrackEntity {
    private static final String sequenceName = "NGNQ_RESPONSE_TRACK_ID";

    @Id
    @Column(name = "ID", columnDefinition = "NUMBER(19)")
    @SequenceGenerator(name = "ResponseTrackSequenceGenerator", sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(generator = "ResponseTrackSequenceGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "REQUEST_DATE", columnDefinition = "TIMESTAMP", nullable = false)
    private Date requestDate;

    @Column(name = "RESPONSE_DATE", columnDefinition = "TIMESTAMP", nullable = false)
    private Date responseDate;

    @Column(name = "TRACKING_NUMBER", columnDefinition = "NVARCHAR2(50)")
    private String trackingNumber;

    @Column(name = "EXCEPTION_NAME", columnDefinition = "NVARCHAR2(150)")
    private String exceptionName;

    @Column(name = "TRACK_TYPE", columnDefinition = "NVARCHAR2(20)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TrackType trackType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REQUEST_ID", columnDefinition = "NUMBER(19)", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RESPONSE_TRACK_REQUEST_ID"))
    private RequestTrack requestTrack;

    @Column(name = "REQUEST_ID", insertable = false, updatable = false)
    private Long requestTrackId;

    @Column(name = "INSTANCE_NUMBER", columnDefinition = "NUMBER(19)", nullable = false)
    private Long instanceNumber;

    @Column(name = "TRACK_DATA", columnDefinition = "CLOB", updatable = false)
    @Lob
    @Convert(converter = TrackDataConverter.class)
    private Map<String, Object> trackData;

    public void setRequestTrack(RequestTrackEntity requestTrack) {
        this.requestTrack = (RequestTrack) requestTrack;
    }
}
