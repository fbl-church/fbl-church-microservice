package com.fbl.app.user.client.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jul 29, 2024
 */
@Repository
public interface UserJPARepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
}