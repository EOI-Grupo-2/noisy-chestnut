package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GroupHasUserId implements Serializable {
    private Long groupId;
    private Long userId;
}
