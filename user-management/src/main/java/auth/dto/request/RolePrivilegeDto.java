package auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RolePrivilegeDto {

    @NotEmpty
    private List<UUID> privilegeUUIDs = new ArrayList<>();
}
