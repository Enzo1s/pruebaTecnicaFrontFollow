package com.api.vital.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = -4797177332919608337L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private String id;
	
	private String name;
	
	private float price;
	
	@ElementCollection
	private List<String> type;
	
	private String description;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	private String pathFile;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(!(obj instanceof Product)) {
			return false;
		}
		Product p = (Product) obj;
		return this.id != null && this.id.equals(p.getId());
	}
}
