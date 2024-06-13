package com.example.subscriber_bc.service;

import com.example.subscriber_bc.exception.SubscriberNotFoundException;
import com.example.subscriber_bc.model.Subscriber;
import com.example.subscriber_bc.repository.SubscriberRepository;
import com.example.subscriber_bc.specification.SubscriberSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber createSubscriber(Subscriber subscriber) {

        Optional<Subscriber> existingSubscriber = subscriberRepository.findByMailOrPhone(subscriber.getMail(), subscriber.getPhone());
        if (existingSubscriber.isPresent()) {
            return null;
        }
        subscriber.setActive(true);
        return subscriberRepository.save(subscriber);
    }


    public List<Subscriber> getSubscribersByCriteria(Map<String, String> criteria) {
        return subscriberRepository.findAll(SubscriberSpecification.getSpecifications(criteria));
    }

    public Subscriber updateSubscriber(Long id, Subscriber updatedSubscriber) {
        Optional<Subscriber> existingSubscriber = subscriberRepository.findByMailOrPhone(updatedSubscriber.getMail(), updatedSubscriber.getPhone());
        if (existingSubscriber.isPresent() && !id.equals(existingSubscriber.get().getSubscriberId())) {
            return null;
        }
        return subscriberRepository.findById(id)
                .map(subscriber -> {
                    if (updatedSubscriber.getMail() != null) {
                        subscriber.setMail(updatedSubscriber.getMail());
                    }
                    if (updatedSubscriber.getPhone() != null) {
                        subscriber.setPhone(updatedSubscriber.getPhone());
                    }
                    if (updatedSubscriber.getFname() != null) {
                        subscriber.setFname(updatedSubscriber.getFname());
                    }
                    if (updatedSubscriber.getLname() != null) {
                        subscriber.setLname(updatedSubscriber.getLname());
                    }
                    return subscriberRepository.save(subscriber);
                }).orElseThrow(() -> new SubscriberNotFoundException("L'abonné n'existe pas avec l'id: " + id));
    }

    public void deactivateSubscriber(Long id) {
        Subscriber subscriberToDeactivate = subscriberRepository.findById(id).orElseThrow(() -> new SubscriberNotFoundException("L'abonné n'existe pas avec l'id: " + id));
        subscriberToDeactivate.setActive(false);
        subscriberRepository.save(subscriberToDeactivate);

    }
}
