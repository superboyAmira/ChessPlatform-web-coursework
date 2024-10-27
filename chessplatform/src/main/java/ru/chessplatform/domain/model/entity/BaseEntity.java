package ru.chessplatform.domain.model.entity;


import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    UUID id;

    @Id
	@GeneratedValue(generator = "org.hibernate.id.UUIDGenerator")
	public UUID getId() {
		return id;
	}

    public void setId(UUID id) {
		this.id = id;
	}
}
