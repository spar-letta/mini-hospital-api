package com.javenock.notification_service.service;

import com.javenock.notification_service.events.dto.PatientEventDto;
import com.javenock.notification_service.model.AuditForm;
import com.javenock.notification_service.model.dto.EntityType;
import com.javenock.notification_service.repository.AuditRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRepository auditRepository;

    @Override
    public void saveReportRecord(PatientEventDto subject) {
        AuditForm auditForm = new AuditForm();
        auditForm.setAction(subject.getAction());
        auditForm.setComment(subject.getComment());
        auditForm.setDescription(subject.getDescription());
        auditForm.setActionBy(subject.getActionBy());
        auditForm.setEntityId(subject.getEntityId());
        auditForm.setEntityType(subject.getEntityType());
        auditForm.setEventDate(LocalDateTime.now());
        AuditForm saved = auditRepository.save(auditForm);
        log.info("====================save record=={}", saved);
    }

    @Override
    public Page<AuditForm> fetchAuditReport(UUID actionedBy, EntityType entityType, String action, LocalDate eventDate, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuditForm> cq = cb.createQuery(AuditForm.class);
        Root<AuditForm> root = cq.from(AuditForm.class);

        cq.distinct(true);

        final List<Predicate> andPredicates = new ArrayList<>();

        if (actionedBy != null) {
            Predicate newPredicate = cb.equal(root.get("actionBy"), actionedBy);
            andPredicates.add(newPredicate);
        }

        if (entityType != null) {
            Predicate newPredicate = cb.equal(root.get("entityType"), entityType);
            andPredicates.add(newPredicate);
        }

        if (eventDate != null) {
            LocalDateTime startOfDay = eventDate.atStartOfDay();
            LocalDateTime endOfDay = eventDate.plusDays(1).atStartOfDay();
            Predicate newPredicate = cb.between(root.get("eventDate"), startOfDay, endOfDay);
            andPredicates.add(newPredicate);
        }

        if (action != null && action.trim().length() >= 3) {
            final List<Predicate> orPredicates = new ArrayList<>();
            orPredicates.add(cb.like(cb.upper(root.get("action")), "%" + action.toUpperCase() + "%"));
            Predicate p = cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
            andPredicates.add(p);
        }

        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()])).orderBy(cb.desc(root.get("id")));

        TypedQuery<AuditForm> query = entityManager.createQuery(cq).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<AuditForm> queryResultList = query.getResultList();

        return new PageImpl<>(queryResultList, pageable, queryResultList.size());
    }
}
