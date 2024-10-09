package auth.service;

import auth.dto.request.PrivilegeDto;
import auth.entity.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivilegeService {
    Privilege createPrivilege(PrivilegeDto privilegeDto);

    Page<Privilege> fetchAllPrivileges(Pageable pageable);
}
