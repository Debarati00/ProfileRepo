package com.pack.Profileservice.Entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CollectionRules")
@Entity
public class CollectionRules implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String description;
	private boolean fraudCheckEnabled;
	private boolean paymentProcessingEnabled;
	private boolean resourcingEnabled;
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profileId")
	private Profile profile;

}
