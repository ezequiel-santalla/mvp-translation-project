package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.Address;
import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //devuelve solo los usuarios activos (NO borrados)
    List<User> findByActiveTrue();

    //devuelve el usuario con el email aunque aunque haya sido borrado
    Optional<User> findUserByEmail(String email);

    //devuelve el usuario con el email solo si esta activo
    Optional<User> findUserByEmailAndActiveTrue(String email);

    //devuelve el id de un usuario por medio de su email
    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Long> findIdByEmail(@Param("email") String email);

    @Query("SELECT u.address FROM User u WHERE u.email = :email")
    Optional<Address> findAddressByEmail(String email);

    @Query("SELECT u.projects FROM User u WHERE u.email = :email")
    Optional<List<Project>> findProjectsByEmail(String email);

    @Query("SELECT u.languagePairs FROM User u WHERE u.email = :email")
    Optional<List<LanguagePair>> findLanguagePairsByEmail(String email);

    Optional<List<User>> findByLanguagePairsContains(LanguagePair languagePair);
}

