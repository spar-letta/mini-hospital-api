package com.javenock.doctor_service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.model.Booking;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import com.javenock.doctor_service.model.vo.User;
import com.javenock.doctor_service.service.BookingService;
import com.javenock.doctor_service.views.BaseView;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingRestController {
    private final BookingService bookingService;

    @PostMapping
    @JsonView(BaseView.DoctorView.class)
    public Booking createBooking(@RequestBody BookingCreateRequest bookingCreateRequest) throws BadRequestException {
        return bookingService.createBooking(bookingCreateRequest);
    }

    @GetMapping
    @JsonView({BaseView.DoctorView.class})
    public List<User> getBooking() {
        return bookingService.getAllUsers();
    }
}
