package auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PrivilegeDto {

    @NotBlank
    private String name;

//    private UUID roleId;

    public PrivilegeDto(String name) {
        this.name = name;
    }

}
