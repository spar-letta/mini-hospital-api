package auth.service;

import auth.dto.request.CreateRoleDTO;
import auth.dto.request.RolePrivilegeDto;
import auth.entity.Privilege;
import auth.entity.Role;
import auth.repository.PrivilegeRepository;
import auth.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Role createRole(CreateRoleDTO createRoleDTO) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByNameIgnoreCase(createRoleDTO.getName());
        if (optionalRole.isPresent()) {
            throw new Exception("Role already exists");
        }
        Role role = new Role();
        role.setName(createRoleDTO.getName());
        role.setDescription(createRoleDTO.getDescription());

        return roleRepository.save(role);
    }

    @Override
    public Page<Role> getAllRoles(Pageable pageable) {
        List<Role> roleList = roleRepository.findAllAndDeletedIsFalse();
        return new PageImpl<>(roleList, pageable, roleList.size());
    }

    @Override
    public Role assignPrivilegeToRole(UUID rolePublicId, RolePrivilegeDto request) throws Exception {
        Role role = validateRole(rolePublicId);
        List<Privilege> allPrivileges = role.getPrivileges();
        request.getPrivilegeUUIDs().forEach(itemId -> {
            Privilege privilege = privilegeRepository.findByPublicId(itemId);
            if(privilege != null) {
                allPrivileges.add(privilege);
            }
        });

        if(allPrivileges != null) {
            role.setPrivileges(allPrivileges);
        }
        return roleRepository.save(role);
    }

    private Role validateRole(UUID rolePublicId) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByPublicIdAndDeletedIsFalse(rolePublicId);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }else {
            throw new Exception("Role does not exist");
        }
    }
}
