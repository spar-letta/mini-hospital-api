package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Counter;
import com.javenock.doctor_service.model.dataType.CounterType;

public interface CounterService {
    Integer getNextCounter(CounterType counterType);
}
