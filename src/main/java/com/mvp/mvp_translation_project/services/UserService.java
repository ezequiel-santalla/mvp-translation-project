package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.UserNotFoundException;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.UserDTO;
import com.mvp.mvp_translation_project.models.UserRequestDTO;
import com.mvp.mvp_translation_project.repositories.UserRepository;
import com.mvp.mvp_translation_project.types.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUsersByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public Boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
    public UserDTO registerUser(UserRequestDTO registrationDTO) {
        // Mapeo del DTO a la entidad
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setBirthDate(registrationDTO.getBirthDate());
        user.setIdentityNumber(registrationDTO.getIdentityNumber());
        user.setCellphone(registrationDTO.getCellphone());
        user.setRole(RoleType.TRANSLATOR);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setActive(true); // Activar usuario por defecto

        // Guardar en la base de datos
        User savedUser = userRepository.save(user);

        // Retornar como DTO
        return mapToResponseDTO(savedUser);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private UserDTO mapToResponseDTO(User user) {
        UserDTO responseDto = new UserDTO();
        responseDto.setName(user.getName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setRole(user.getRole());
        responseDto.setCellphone(user.getCellphone());
        responseDto.setIdentityNumber(user.getIdentityNumber());
        responseDto.setBirthDate(user.getBirthDate());
        return responseDto;
    }

}