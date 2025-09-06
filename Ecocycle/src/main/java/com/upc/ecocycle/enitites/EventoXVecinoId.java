package com.upc.ecocycle.enitites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EventoXVecinoId implements Serializable {
    private static final long serialVersionUID = -7451352356934470828L;
    @Column(name = "id_evento", nullable = false)
    private Integer idEvento;

    @Column(name = "id_vecino", nullable = false)
    private Integer idVecino;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventoXVecinoId entity = (EventoXVecinoId) o;
        return Objects.equals(this.idEvento, entity.idEvento) &&
                Objects.equals(this.idVecino, entity.idVecino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento, idVecino);
    }

}