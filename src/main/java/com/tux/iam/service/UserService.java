package com.tux.iam.service;

import com.tux.iam.entity.Office;
import com.tux.iam.entity.Role;
import com.tux.iam.dto.UpdateDTO;
import com.tux.iam.entity.User;
import com.tux.iam.exception.CustomServiceException;
import com.tux.iam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    Predicate<Authentication> authRole = (authentication) -> authentication != null && (authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("BDO") ||
            authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("SUPER_ADMIN"));

    Predicate<Authentication> authOffice = (authentication) -> authentication != null && (authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("BDO"));

    public String updateUserRole(UpdateDTO updateDTO){

        return updateUser(authRole, updateDTO);

    }

    public String assignOffice(UpdateDTO updateDTO){

        return updateUser(authOffice, updateDTO);

    }

    private String updateUser(Predicate<Authentication> authRole, UpdateDTO updateDTO) {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authRole.test(authentication)){
            if(updateDTO.getEmail() != null) {
                user = userRepository.findByEmail(updateDTO.getEmail()).orElseThrow(() -> new CustomServiceException("User Not Found"));
            }else if(updateDTO.getName() != null){
                user = userRepository.findByName(updateDTO.getName()).orElseThrow(() -> new CustomServiceException("User Not Found"));
            }
            if(updateDTO.getRole() != null) {
                user.setRole(Role.valueOf(updateDTO.getRole()));
            }else {
                User usr = (User)authentication.getPrincipal();
                user.setOffice(usr.getOffice());
            }
            userRepository.save(user);
            return "Update Successful";
        }else{
            throw  new CustomServiceException("User Does't Have Access To Update");
        }
    }


}
