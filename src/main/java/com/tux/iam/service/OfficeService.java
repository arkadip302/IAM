package com.tux.iam.service;

import com.tux.iam.entity.Office;
import com.tux.iam.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class OfficeService {

    Predicate<Authentication> authOffice = (authentication) -> authentication != null && (authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("SUPER_ADMIN"));

    @Autowired
    private OfficeRepository officeRepository;


    public List<Office> getAllOffice() {
        return officeRepository.findAll();
    }

    public String createOffice(Office office){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authOffice.test(authentication)) {
            officeRepository.save(office);
        }
        return "Office Created";
    }
}
