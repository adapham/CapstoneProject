package vn.com.edu.fpt.capstoneproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.edu.fpt.capstoneproject.config.security.CurrentUser;
import vn.com.edu.fpt.capstoneproject.config.security.UserPrincipal;
import vn.com.edu.fpt.capstoneproject.exception.ResourceNotFoundException;
import vn.com.edu.fpt.capstoneproject.model.User;
import vn.com.edu.fpt.capstoneproject.repository.UserRepository;

@RestController
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {

    UserRepository userRepository;
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
