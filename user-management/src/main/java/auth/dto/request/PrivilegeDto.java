package auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrivilegeDto {

    @NotBlank
    private String name;
}
