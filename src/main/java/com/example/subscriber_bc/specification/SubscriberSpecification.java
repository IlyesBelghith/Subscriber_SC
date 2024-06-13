package com.example.subscriber_bc.specification;

import com.example.subscriber_bc.model.Subscriber;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubscriberSpecification {
    public static Specification<Subscriber> getSpecifications(Map<String, String> criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            criteria.forEach((key, value) -> {
                switch (key) {
                    case "subscriberId":
                        predicates.add(criteriaBuilder.equal(root.get("subscriberId"), Long.parseLong(value)));
                        break;
                    case "fname":
                        predicates.add(criteriaBuilder.equal(root.get("fname"), value));
                        break;
                    case "lname":
                        predicates.add(criteriaBuilder.equal(root.get("lname"), value));
                        break;
                    case "mail":
                        predicates.add(criteriaBuilder.equal(root.get("mail"), value));
                        break;
                    case "phone":
                        predicates.add(criteriaBuilder.equal(root.get("phone"), value));
                        break;
                    case "active":
                        predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.parseBoolean(value)));
                        break;
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
