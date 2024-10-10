package com.javenock.doctor_service.service;

import com.javenock.doctor_service.model.Booking;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import com.javenock.doctor_service.model.vo.User;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingCreateRequest bookingCreateRequest, Authentication loggedInUser) throws BadRequestException;

    Page<Booking> getAllUsers(Pageable pageable, Authentication loggedInUser);
}
