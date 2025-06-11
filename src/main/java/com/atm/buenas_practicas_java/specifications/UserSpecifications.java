package com.atm.buenas_practicas_java.specifications;

import com.atm.buenas_practicas_java.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> filterBy(String username, String email, String name) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction(); // Siempre true inicialmente

            if (username != null && !username.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
