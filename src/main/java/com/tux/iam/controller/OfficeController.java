package com.tux.iam.controller;

import com.tux.iam.dto.UpdateDTO;
import com.tux.iam.entity.Office;
import com.tux.iam.repository.UserRepository;
import com.tux.iam.service.OfficeService;
import com.tux.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/iam/v1/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;


    @GetMapping("/get_all_office")
    public ResponseEntity<List<Office>> getAllOffice(){
        return  ResponseEntity.ok(officeService.getAllOffice());
    }

    @PostMapping("/create/office")
    public  ResponseEntity<String> createOffice(@RequestBody Office office){
        return  ResponseEntity.ok(officeService.createOffice(office));
    }
}
