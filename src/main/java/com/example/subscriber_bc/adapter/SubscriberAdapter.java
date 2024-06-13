package com.example.subscriber_bc.adapter;


import com.example.subscriber_bc.dto.SubscriberDTO;
import com.example.subscriber_bc.model.Subscriber;

public class SubscriberAdapter {
    public static Subscriber toSubscriber(SubscriberDTO dto) {
        if (dto == null) {
            return null;
        }
        Subscriber subscriber = new Subscriber();
        subscriber.setFname(dto.getFname());
        subscriber.setLname(dto.getLname());
        subscriber.setMail(dto.getMail());
        subscriber.setPhone(dto.getPhone());
        subscriber.setActive(true);
        return subscriber;
    }
}
