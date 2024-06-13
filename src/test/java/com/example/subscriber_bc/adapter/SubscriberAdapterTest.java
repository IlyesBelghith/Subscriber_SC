package com.example.subscriber_bc.adapter;

import com.example.subscriber_bc.dto.SubscriberDTO;
import com.example.subscriber_bc.model.Subscriber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriberAdapterTest {

    @Test
    void toSubscriber_ShouldTransformDTOToSubscriber() {
        // Given
        SubscriberDTO dto = new SubscriberDTO();
        dto.setFname("il");
        dto.setLname("b");
        dto.setMail("il.b@test.com");
        dto.setPhone("123456789");

        // When
        Subscriber subscriber = SubscriberAdapter.toSubscriber(dto);

        // Then
        assertNotNull(subscriber);
        assertEquals("il", subscriber.getFname());
        assertEquals("b", subscriber.getLname());
        assertEquals("il.b@test.com", subscriber.getMail());
        assertEquals("123456789", subscriber.getPhone());
        assertTrue(subscriber.isActive());
    }

    @Test
    void toSubscriber_ShouldReturnNull_WhenDTOIsNull() {
        // Given
        SubscriberDTO dto = null;

        // When
        Subscriber subscriber = SubscriberAdapter.toSubscriber(dto);

        // Then
        assertNull(subscriber);
    }
}
