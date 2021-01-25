package com.pack.Profileservice.model;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSpecs {
	private String profileId;
	private String operation;
	private LocalTime lastupdated;
	@Override
	public String toString() {
		return "profileId=" + profileId + ", operation=" + operation + ", lastupdated=" + lastupdated;
	}

}
