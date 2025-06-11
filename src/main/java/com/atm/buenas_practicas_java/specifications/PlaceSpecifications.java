package com.atm.buenas_practicas_java.specifications;

import com.atm.buenas_practicas_java.entities.Place;
import org.springframework.data.jpa.domain.Specification;

public class PlaceSpecifications {

    public static Specification<Place> filterBy(String name, String address) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (address != null && !address.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
