package auth.service;

import auth.dto.request.CreateRoleDTO;
import auth.dto.request.RolePrivilegeDto;
import auth.entity.Role;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleService {
    Role createRole(CreateRoleDTO createRoleDTO) throws Exception;

    Page<Role> getAllRoles(Pageable pageable);

    Role assignPrivilegeToRole(UUID rolePublicId, @Valid RolePrivilegeDto request) throws Exception;
}
