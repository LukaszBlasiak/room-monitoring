package pl.blasiak.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class JwtModel implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	@Schema(description = "JWT token.")
	private final String token;
	@Schema(description = "JWT type.")
	private final String type;

}
