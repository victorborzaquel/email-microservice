package com.ms.email.repositories;

import com.ms.email.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepostirory extends JpaRepository<Email, Long> {
}
