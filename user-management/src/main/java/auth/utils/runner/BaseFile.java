package auth.utils.runner;

import auth.dto.request.CreateRoleDTO;
import auth.dto.request.PrivilegeDto;
import auth.dto.request.RolePrivilegeDto;
import auth.entity.Privilege;
import auth.entity.Role;
import auth.repository.PrivilegeRepository;
import auth.repository.RoleRepository;
import auth.service.PrivilegeService;
import auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BaseFile {
    private static final Logger log = LoggerFactory.getLogger(BaseFile.class);
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeService privilegeService;

    public Role createRolePrivileges(String roleName, List<String> privileges) throws Exception {
        Role role = createRole(roleName);
        Set<Privilege> privilegeList = createPrivileges(privileges, role);
        List<UUID> privilegeUUIDs = new ArrayList<>();
        privilegeList.forEach(privilege -> {
            privilegeUUIDs.add(privilege.getPublicId());
        });
        roleService.assignPrivilegeToRole(role.getPublicId(), new RolePrivilegeDto(privilegeUUIDs));
        return role;
    }

    private Role createRole(String roleName) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByNameIgnoreCase(roleName);
        if (!optionalRole.isPresent()) {
            Role role = roleService.createRole(new CreateRoleDTO(roleName, roleName + " created"));
            return role;
        }
        return optionalRole.get();
    }

    private Set<Privilege> createPrivileges(List<String> privileges, Role role) {
        if (privileges.isEmpty()) {
            return new HashSet<>();
        }
        Set<Privilege> privilegesList = new HashSet<>();
        privileges.forEach(privilege -> {
            Optional<Privilege> optionalPrivilege = privilegeRepository.findByNameIgnoreCaseAndDeletedIsFalse(privilege);
            if (optionalPrivilege.isPresent()) {
                privilegesList.add(optionalPrivilege.get());
            }
            Privilege savedPrivilege = privilegeService.createPrivilege(new PrivilegeDto(privilege));
            privilegesList.add(savedPrivilege);
        });
        return privilegesList;
    }
}
