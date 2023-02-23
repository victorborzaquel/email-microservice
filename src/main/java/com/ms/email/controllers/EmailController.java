package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.Email;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send-email")
    @ResponseStatus(HttpStatus.CREATED)
    public Email sendEmail(
            @RequestBody @Valid EmailDto emailDto
    ) {
        final Email email = new Email();

        BeanUtils.copyProperties(emailDto, email);
        emailService.sendEmail(email);

        return email;
    }

    @GetMapping("/emails")
    @ResponseStatus(HttpStatus.OK)
    public Page<Email> getAllEmails(
            @PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC
            ) Pageable pageable){
        return emailService.findAll(pageable);
    }

    @GetMapping("/emails/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value="emailId") UUID emailId){
        Optional<Email> emailModelOptional = emailService.findById(emailId);
        if(!emailModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(emailModelOptional.get());
        }
    }
}
