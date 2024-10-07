package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Booking;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import com.javenock.doctor_service.model.vo.User;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingCreateRequest bookingCreateRequest) throws BadRequestException;

    List<User> getAllUsers();
}
