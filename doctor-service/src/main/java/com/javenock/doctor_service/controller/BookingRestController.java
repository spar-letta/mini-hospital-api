package com.javenock.doctor_service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.docs.Examples;
import com.javenock.doctor_service.model.Booking;
import com.javenock.doctor_service.model.dto.BookingCreateRequest;
import com.javenock.doctor_service.model.vo.User;
import com.javenock.doctor_service.service.BookingService;
import com.javenock.doctor_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "bookings")
public class BookingRestController {
    private final BookingService bookingService;

    @PreAuthorize("hasAuthority('CREATE_BOOKING')")
    @PostMapping
    @Operation(summary = "Create Booking", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.BOOKING_CREATE_RESPONSE)))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {@ExampleObject(name = "booking creation request", value = Examples.CREATE_BOOKING_REQUEST)})))
    @JsonView(BaseView.DoctorView.class)
    public Booking createBooking(@RequestBody BookingCreateRequest bookingCreateRequest,
                                 @Parameter(hidden = true) Authentication loggedInUser
    ) throws BadRequestException {
        return bookingService.createBooking(bookingCreateRequest, loggedInUser);
    }

    @PreAuthorize("hasAuthority('READ_BOOKINGS')")
    @GetMapping
    @Operation(summary = "Get All Bookings", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.BOOKINGS_RESPONSE)))})
    @JsonView({BaseView.DoctorView.class})
    public Page<Booking> getBookings(@Parameter(hidden = true) Pageable pageable,
                                     @Parameter(hidden = true) Authentication loggedInUser) {
        return bookingService.getAllUsers(pageable, loggedInUser);
    }
}
