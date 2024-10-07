package com.javenock.notification_service.repository;

import com.javenock.notification_service.model.AuditForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditForm, Long> {
}
