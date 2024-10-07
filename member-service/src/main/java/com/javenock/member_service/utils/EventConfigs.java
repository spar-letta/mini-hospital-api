package com.javenock.member_service.utils;

import com.javenock.member_service.config.EventConfig;
import com.javenock.member_service.model.Patient;
import com.javenock.member_service.model.dto.PatientEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventConfigs {

//    @RabbitListener(queues = EventConfig.AUDIT_QUEUE)
//    public void audit(PatientEventDto patient) {
//        log.info("===========Audit patient {}", patient.toString());
//    }
}
