package com.makeathon.sehad.dao;

import com.makeathon.sehad.models.db.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDao extends JpaRepository<Authentication, Long> {

    Authentication findByEmail(String email);
}
