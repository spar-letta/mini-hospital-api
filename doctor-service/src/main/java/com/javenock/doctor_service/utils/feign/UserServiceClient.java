package com.javenock.doctor_service.utils.feign;

import com.javenock.doctor_service.model.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user-management-service")
public interface UserServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/auth/internal/user/{userId}")
    Optional<User> fetchUserDoctor(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> fetchUserList();

}
