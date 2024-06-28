package com.fbl.app.attendance.client.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRecordJPARepository
        extends JpaRepository<AttendanceRecordEntity, Integer>, JpaSpecificationExecutor<AttendanceRecordEntity> {

    Optional<AttendanceRecordEntity> findByName(String name);
}