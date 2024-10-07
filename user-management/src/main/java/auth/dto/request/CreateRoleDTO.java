package auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoleDTO {

    @NotBlank
    private String name;

    private String description;
}
