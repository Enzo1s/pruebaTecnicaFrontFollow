package com.api.vital.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
public class User implements Serializable{
	
	private static final long serialVersionUID = -2355318913825683991L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

	private String username;

    private String password;
    
    @Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
    
    @PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
    
    @Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(!(obj instanceof User)) {
			return false;
		}
		User p = (User) obj;
		return this.id != null && this.id.equals(p.getId());
	}

}
