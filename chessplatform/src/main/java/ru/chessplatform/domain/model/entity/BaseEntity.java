package ru.chessplatform.domain.model.entity;


import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    UUID id;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	public UUID getId() {
		return id;
	}

    public void setId(UUID id) {
		this.id = id;
	}
}
