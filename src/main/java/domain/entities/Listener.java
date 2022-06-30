package domain.entities;

import java.time.Instant;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Listener {

    @PrePersist
    void preCreate(AbstractEntity pre) {
        Instant now = Instant.now();
        pre.created_at = now;
        pre.modified_at = now;
    }

    @PreUpdate
    void preUpdate(AbstractEntity pre) {
        Instant now = Instant.now();
        pre.modified_at = now;
    }
}
