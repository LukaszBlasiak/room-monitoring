package pl.blasiak.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Model representing authorization request credentials.")
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    @Schema(description = "Account username.")
    private String username;
    @Schema(description = "Account password.")
    private String password;
}
