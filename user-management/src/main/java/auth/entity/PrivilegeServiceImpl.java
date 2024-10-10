package auth.entity;

import auth.dto.request.PrivilegeDto;
import auth.repository.PrivilegeRepository;
import auth.repository.RoleRepository;
import auth.service.PrivilegeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private static final Logger log = LoggerFactory.getLogger(PrivilegeServiceImpl.class);
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    @Override
    public Privilege createPrivilege(PrivilegeDto privilegeDto) {
        Optional<Privilege> optionalPrivilege = privilegeRepository.findByNameIgnoreCaseAndDeletedIsFalse(privilegeDto.getName());
        if (!optionalPrivilege.isPresent()) {
            Privilege privilege = new Privilege();
            privilege.setName(privilegeDto.getName());
            return privilegeRepository.save(privilege);
        }
        return optionalPrivilege.get();
    }

    @Override
    public Page<Privilege> fetchAllPrivileges(Pageable pageable) {
        List<Privilege> all = privilegeRepository.findAllPrivileges();
        return new PageImpl<>(all, pageable, all.size());
    }
}
