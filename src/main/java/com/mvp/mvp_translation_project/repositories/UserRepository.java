package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
