package pl.blasiak.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class JwtModel implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final String type;

}
