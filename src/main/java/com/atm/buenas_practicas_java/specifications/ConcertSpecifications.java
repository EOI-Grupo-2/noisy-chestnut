package com.atm.buenas_practicas_java.specifications;

import com.atm.buenas_practicas_java.entities.Concert;
import org.springframework.data.jpa.domain.Specification;

public class ConcertSpecifications {

    public static Specification<Concert> filterBy(String name, String description) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (description != null && !description.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
