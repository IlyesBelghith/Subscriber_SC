package com.example.subscriber_bc.controller;

import com.example.subscriber_bc.adapter.SubscriberAdapter;
import com.example.subscriber_bc.dto.SubscriberDTO;
import com.example.subscriber_bc.dto.validation.CreateValidationGroup;
import com.example.subscriber_bc.model.Subscriber;
import com.example.subscriber_bc.service.SubscriberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscribers")

public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @PostMapping()
    public ResponseEntity<?> createSubscriber(@Validated(CreateValidationGroup.class) @RequestBody SubscriberDTO subscriberDTO) {
        Subscriber subscriber = SubscriberAdapter.toSubscriber(subscriberDTO);
        Subscriber createdSubscriber = subscriberService.createSubscriber(subscriber);
        if (createdSubscriber == null) {
            return ResponseEntity.internalServerError().body("Un abonné existe déjà avec cette adresse mail et/ou ce numéro");
        }
        return ResponseEntity.ok(createdSubscriber);
    }

    @GetMapping()
    public ResponseEntity<?> getSubscriber(@RequestParam Map<String, String> criteria) {
        List<Subscriber> subscribers = subscriberService.getSubscribersByCriteria(criteria);
        return ResponseEntity.ok(subscribers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscriber(@PathVariable Long id, @Valid @RequestBody SubscriberDTO subscriberDTO) {
        Subscriber updatedSubscriber = subscriberService.updateSubscriber(id, SubscriberAdapter.toSubscriber(subscriberDTO));
        if (updatedSubscriber == null) {
            return ResponseEntity.internalServerError().body("Un abonné existe déjà avec cette adresse mail et/ou ce numéro");
        }
        return ResponseEntity.ok(updatedSubscriber);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateSubscriber(@PathVariable Long id) {
        subscriberService.deactivateSubscriber(id);
        return ResponseEntity.ok().build();
    }
}
