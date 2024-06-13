package com.example.subscriber_bc.service;

import com.example.subscriber_bc.exception.SubscriberNotFoundException;
import com.example.subscriber_bc.model.Subscriber;
import com.example.subscriber_bc.repository.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriberServiceTest {
    @InjectMocks
    private SubscriberService subscriberService;

    @Mock
    private SubscriberRepository subscriberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSubscriber_ShouldCreateSubscriber_WhenNotExists() {
        // Given
        Subscriber subscriber = new Subscriber();
        subscriber.setMail("test@gmail.com");

        when(subscriberRepository.findByMailOrPhone(subscriber.getMail(), subscriber.getPhone())).thenReturn(Optional.empty());
        when(subscriberRepository.save(subscriber)).thenReturn(subscriber);

        // When
        Subscriber createdSubscriber = subscriberService.createSubscriber(subscriber);

        // Then
        assertNotNull(createdSubscriber);
        assertEquals("test@gmail.com", createdSubscriber.getMail());
        verify(subscriberRepository, times(1)).save(subscriber);
    }

    @Test
    void createSubscriber_ShouldReturnNull_WhenSubscriberExists() {
        // Given
        Subscriber subscriber = new Subscriber();
        subscriber.setMail("test@gmail.com");
        when(subscriberRepository.findByMailOrPhone(subscriber.getMail(), subscriber.getPhone())).thenReturn(Optional.of(subscriber));

        // When
        Subscriber createdSubscriber = subscriberService.createSubscriber(subscriber);

        //Then
        assertNull(createdSubscriber);
        verify(subscriberRepository, never()).save(any(Subscriber.class));
    }

    @Test
    void getSubscribersByCriteria_ShouldReturnAllSubscribers_WhenNoCriteria() {
        // Given
        Subscriber subscriber1 = new Subscriber();
        subscriber1.setMail("test@gmail.com");
        subscriber1.setPhone("123456789");
        subscriber1.setFname("Peter");
        subscriber1.setLname("Pan");
        subscriber1.setActive(true);

        Subscriber subscriber2 = new Subscriber();
        subscriber2.setMail("test2@gmail.com");
        subscriber2.setPhone("123456798");
        subscriber2.setFname("Pep");
        subscriber2.setLname("Pan");
        subscriber2.setActive(false);

        when(subscriberRepository.findAll(any(Specification.class))).thenReturn(List.of(subscriber1, subscriber2));

        //When
        List<Subscriber> result = subscriberService.getSubscribersByCriteria(null);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subscriberRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void updateSubscriber_ShouldUpdateSubscriber_WhenExists() {
        //Given
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setSubscriberId(1L);
        existingSubscriber.setMail("testV0@gmail.com");
        existingSubscriber.setPhone("123456798");
        existingSubscriber.setFname("tom");
        existingSubscriber.setLname("t");

        Subscriber updatedSubscriber = new Subscriber();
        updatedSubscriber.setMail("testV1@gmail.com");
        updatedSubscriber.setPhone("122256222");
        updatedSubscriber.setFname("Peter");
        updatedSubscriber.setLname("Pan");

        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(existingSubscriber));
        when(subscriberRepository.save(existingSubscriber)).thenReturn(existingSubscriber);

        // When
        Subscriber result = subscriberService.updateSubscriber(1L, updatedSubscriber);

        // Then
        assertNotNull(result);
        assertEquals("testV1@gmail.com", result.getMail());
        assertEquals("122256222", result.getPhone());
        assertEquals("Peter", result.getFname());
        assertEquals("Pan", result.getLname());
        verify(subscriberRepository, times(1)).findById(1L);
        verify(subscriberRepository, times(1)).save(existingSubscriber);
    }

    @Test
    void updateSubscriber_ShouldReturnNull_WhenUpdatedMailOrPhoneAlreadyExists() {
        // Given
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());
        Subscriber subscriber = new Subscriber();
        subscriber.setMail("test@gmail.com");
        subscriber.setSubscriberId(2L);

        when(subscriberRepository.findByMailOrPhone(subscriber.getMail(), subscriber.getPhone())).thenReturn(Optional.of(subscriber));
        Subscriber updatedSubscriber = new Subscriber();
        updatedSubscriber.setMail("test@gmail.com");

        // When
        Subscriber result = subscriberService.updateSubscriber(1L, updatedSubscriber);

        // Then
        assertNull(result);
        verify(subscriberRepository, never()).findById(anyLong());
        verify(subscriberRepository, never()).save(any(Subscriber.class));
    }

    @Test
    void updateSubscriber_ShouldThrowException_WhenSubscriberNotFound() {
        //Given
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        Subscriber updatedSubscriber = new Subscriber();

        // Then (When)
        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.updateSubscriber(1L, updatedSubscriber);
        });
    }

    @Test
    void deactivateSubscriber_ShouldDeactivateSubscriber_WhenExists() {
        //Given
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setSubscriberId(1L);
        existingSubscriber.setActive(true);

        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(existingSubscriber));
        when(subscriberRepository.save(existingSubscriber)).thenReturn(existingSubscriber);

        //When
        subscriberService.deactivateSubscriber(1L);

        //Then
        assertFalse(existingSubscriber.isActive());
        verify(subscriberRepository, times(1)).findById(1L);
        verify(subscriberRepository, times(1)).save(existingSubscriber);
    }

    @Test
    void deactivateSubscriber_ShouldThrowException_WhenSubscriberNotFound() {
        //Given
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        // Then (When)
        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.deactivateSubscriber(1L);
        });
        verify(subscriberRepository, times(1)).findById(1L);
        verify(subscriberRepository, never()).save(any(Subscriber.class));
    }
}
