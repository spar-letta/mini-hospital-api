package auth.service;

import auth.application.exception.ApplicationOperationException;
import auth.dto.request.*;
import auth.entity.*;
import auth.repository.RoleRepository;
import auth.repository.UserRepository;
import auth.dto.datatypes.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PrincipleService principleService;

    @Override
    public User createUser(CreateUserDTO createUserDTO) {
        if (ObjectUtils.isEmpty(createUserDTO.getContactEmail()) && ObjectUtils.isEmpty(createUserDTO.getContactPhonenumber())) {
            throw new ApplicationOperationException("User missing at least one contact info");
        }

        if (createUserDTO.getUserName().contains(" ")) {
            throw new ApplicationOperationException("remove white spaces from username");
        }

        var optionalUser = userRepository.findByUserNameIgnoreCase(createUserDTO.getUserName());

        if (optionalUser.isPresent()) {
            throw new ApplicationOperationException("User with name: " + createUserDTO.getUserName() + " already exists");
        }
        String clearPassword = createUserDTO.getPassword();
        var user = User.builder()
                .userName(createUserDTO.getUserName())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .otherName(createUserDTO.getOtherName())
                .contactEmail(createUserDTO.getContactEmail())
                .contactPhonenumber(createUserDTO.getContactPhonenumber())
                .verifiedEmail(false)
                .verifiedPhonenumber(false)
                .status(UserStatus.Active)
                .password(passwordEncoder.encode(clearPassword))
                .clearPassword(clearPassword)
                .changePassword(true)
                .build();

        return userRepository.save(user);
    }

    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public User getUserByPublicId(UUID publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(() ->
                new ApplicationOperationException("User not found with publicId:" + publicId));
    }

    @Override
    public User updateUser(CreateUserDTO createUserDTO, UUID publicId) {
        var user = userRepository.findByPublicId(publicId).orElseThrow(() ->
                new ApplicationOperationException("User not found with publicId:" + publicId));

        var optionalUser = userRepository.findByUserNameIgnoreCase(createUserDTO.getUserName());

        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(user.getId())) {
            throw new ApplicationOperationException("User with name: " + createUserDTO.getUserName() + " already exists");
        }

        if (ObjectUtils.isEmpty(createUserDTO.getContactEmail()) && ObjectUtils.isEmpty(createUserDTO.getContactPhonenumber())) {
            throw new ApplicationOperationException("User missing at least one contact info");
        }

        user.setUserName(createUserDTO.getUserName());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setOtherName(createUserDTO.getOtherName());
        user.setContactEmail(createUserDTO.getContactEmail());
        user.setContactPhonenumber(createUserDTO.getContactPhonenumber());

        return userRepository.save(user);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public User getUserProfile() {
        return principleService.getLoggedInUser();
    }

    @Override
    public User updateUserProfile(UserProfileDto userProfileDto) {
        User user = principleService.getLoggedInUser();
        if (ObjectUtils.isEmpty(userProfileDto.getContactEmail()) && ObjectUtils.isEmpty(userProfileDto.getContactPhonenumber())) {
            throw new ApplicationOperationException("User missing at least one contact info");
        }
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        user.setOtherName(userProfileDto.getOtherName());
        if (user.isVerifiedEmail()) {
            user.setVerifiedEmail(ObjectUtils.nullSafeEquals(user.getContactEmail(), userProfileDto.getContactEmail()));
        }
        if (user.isVerifiedPhonenumber()) {
            user.setVerifiedPhonenumber(ObjectUtils.nullSafeEquals(user.getContactPhonenumber(), userProfileDto.getContactPhonenumber()));
        }
        user.setContactEmail(userProfileDto.getContactEmail());
        user.setContactPhonenumber(userProfileDto.getContactPhonenumber());

        return userRepository.save(user);
    }

    @Override
    public User assignRoleToUser(RoleDto request, UUID publicId) {
        Optional<Role> optionalRole = roleRepository.findByNameIgnoreCase(request.getName());
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            Optional<User> optionalUser = userRepository.findByPublicId(publicId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.getRoles().add(role);
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public User getOneUser(String username) {
        if(username != null) {
            Optional<User> optionalUser = userRepository.findByUserNameIgnoreCase(username);
            if(optionalUser.isPresent()) {
                return optionalUser.get();
            }
        }
        return null;
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) {
            return false; // Check for minimum length
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecialChar = true;
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
}

