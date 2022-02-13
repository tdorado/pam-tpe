package com.td.wallendarbackend.controllers;

import com.td.wallendarbackend.dtos.requests.AddChargeRequest;
import com.td.wallendarbackend.dtos.requests.AddEventRequest;
import com.td.wallendarbackend.dtos.requests.AddMembersRequest;
import com.td.wallendarbackend.dtos.requests.AddPaymentRequest;
import com.td.wallendarbackend.dtos.responses.ChargeResponse;
import com.td.wallendarbackend.dtos.responses.EventResponse;
import com.td.wallendarbackend.services.ChargeService;
import com.td.wallendarbackend.services.EventService;
import com.td.wallendarbackend.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final ChargeService chargeService;
    private final PaymentService paymentService;

    @Autowired
    public EventController(EventService eventService, ChargeService chargeService, PaymentService paymentService) {
        this.eventService = eventService;
        this.chargeService = chargeService;
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody @Valid AddEventRequest addEventRequest) {
        EventResponse event = eventService.createEvent(addEventRequest);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getEventById(@PathVariable long id) {
        EventResponse event = eventService.findById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/allFromUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getEventsByApplicationUserId(@PathVariable long id) {
        List<EventResponse> events = eventService.findEventsByApplicationUserId(id);
        if (events != null) {
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/addMembers")
    @ResponseBody
    public ResponseEntity<?> addMembers(@PathVariable long id, @RequestBody @Valid AddMembersRequest addMembersRequest) {
        boolean addMembersSuccess = eventService.addMembers(id, addMembersRequest);
        if (addMembersSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/addCharge")
    @ResponseBody
    public ResponseEntity<?> addCharge(@PathVariable long id, @RequestBody @Valid AddChargeRequest addChargeRequest) {
        ChargeResponse charge = chargeService.addCharge(id, addChargeRequest);
        if (charge != null) {
            return new ResponseEntity<>(charge, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/addPayment")
    @ResponseBody
    public ResponseEntity<?> addPayment(@PathVariable long id, @RequestBody @Valid AddPaymentRequest addPaymentRequest) {
        boolean paymentSuccess = paymentService.addPayment(id, addPaymentRequest);
        if (paymentSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
