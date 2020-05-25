package com.brennaswitzer.foodinger.model;

import com.brennaswitzer.foodinger.util.IdUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@SequenceGenerator(name = "id_seq",
        sequenceName = "id_seq")
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "id_seq")
    private Long id;

    @NotNull
    @Column(updatable = false)
    private final Long _eqkey = IdUtils.next(getClass());

    @NotNull
    private Instant createdAt;

    @NotNull
    @Version
    private Instant updatedAt;

    @PrePersist
    protected void onPersist() {
        setCreatedAt(Instant.now());
    }

    /**
     * I indicate object equality, which in this case means an assignable type
     * and the same {@link #get_eqkey()}. Using the {@code _eqkey} (which is
     * database-persisted) instead of the object's memory location allows for
     * proper operation across the persistence boundary. Using an assignable
     * type (instead of type equality) allows for proper operation across
     * persistence proxies. It has the side effect of allow subtypes to be
     * considered equal, but the {@code _eqkey} generator embeds type info which
     * will break such ties in the normal case.
     *
     * @param object The object to check for equality with this one
     * @return Whether the passed object is equal to this one
     */
    @Override
    public final boolean equals(Object object) {
        if (object == null) return false;
        if (!getClass().isAssignableFrom(object.getClass())) return false;
        return this.get_eqkey().equals(((BaseEntity) object).get_eqkey());
    }

    @Override
    public final int hashCode() {
        return this.get_eqkey().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + getId();
    }

}
