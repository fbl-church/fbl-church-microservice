package com.fbl.app.attendance.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jun 28, 2024
 */
@Entity
@Table(name = "attendance_records")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private ChurchGroup type;

    @Column(name = "unit_session")
    private String unitSession;

    @OneToMany
    @JoinTable(name = "attendance_record_workers", joinColumns = @JoinColumn(name = "attendance_record_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AttendanceWorkerEntity> workers;

    @Column(name = "active_date")
    private LocalDate activeDate;

    @Column(name = "started_by_user_id")
    private Integer startedByUserId;

    @Column(name = "closed_by_user_id")
    private Integer closedByUserId;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;
}
