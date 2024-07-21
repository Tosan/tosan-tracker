package com.tosan.tools.tracker.sample.setting.repository;

import com.tosan.tools.tracker.sample.setting.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author F.Ebrahimi
 * @since 12/12/2023
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findByServiceName(String serviceName);
}
