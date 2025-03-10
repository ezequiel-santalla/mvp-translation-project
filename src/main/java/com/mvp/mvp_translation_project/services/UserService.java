package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.*;
import com.mvp.mvp_translation_project.models.*;
import com.mvp.mvp_translation_project.models.dtos.languages.LanguagePairDto;
import com.mvp.mvp_translation_project.models.dtos.projects.ProjectDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserRequestDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserUpdateDto;
import com.mvp.mvp_translation_project.repositories.UserRepository;
import com.mvp.mvp_translation_project.types.RoleType;
import com.mvp.mvp_translation_project.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {

        try {
            return userRepository.findAll().stream()
                    .map(MapperUtils::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve user list", e);
        }
    }

    public List<UserDto> getActiveUsers() {

        try {
            return userRepository.findByActiveTrue().stream()
                    .map(MapperUtils::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve active user list", e);
        }
    }

    public List<User> getUsers() {

        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve user list", e);
        }
    }

    public UserDto getUser(Long id) {

        return userRepository.findById(id).map(MapperUtils::mapToDto).orElseThrow(()
                -> new UserNotFoundException(id));
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public UserDto updateUserByDto(String email, UserUpdateDto userUpdateDto) {

        // Busca el usuario por email o lanza una excepcion si no existe
        User user = userRepository.findUserByEmail(email).orElseThrow(()
                -> new UserNotFoundException(email));
        // Actualiza los campos del user con los nuevos datos
        updateUserFields(user, userUpdateDto);
        User updatedUser = userRepository.save(user);
        return MapperUtils.mapToDto(updatedUser);
    }

    public UserDto updateUserRole(String email, RoleType role) {

        // Busca el usuario por email o lanza una excepcion si no existe
        User user = userRepository.findUserByEmail(email).orElseThrow(()
                -> new UserNotFoundException(email));
        // Actualiza los campos del user con los nuevos datos
        user.setRole(role);
        User updatedUser = userRepository.save(user);
        return MapperUtils.mapToDto(updatedUser);
    }

    private void updateUserFields(User user, UserUpdateDto userUpdateDto) {

        // Si el campo contiene un dato, lo actualiza
        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getBirthDate() != null) {
            user.setBirthDate(userUpdateDto.getBirthDate());
        }
        if (userUpdateDto.getCellphone() != null) {
            user.setCellphone(userUpdateDto.getCellphone());
        }
        if (userUpdateDto.getAddress() != null) {
            user.setAddress(userUpdateDto.getAddress());
        }
    }


    public User updateUser(User user) {
        // Valida que el usuario exista antes de actualizarlo
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }

        return userRepository.save(user);
    }

    public void updateAddress(String email, Address address) {

        if (address == null) {
            throw new InvalidDataException("Address cannot be null");
        }

        User user = userRepository.findUserByEmail(email).orElseThrow(()
                -> new UserNotFoundException(email));
        user.setAddress(address);
        userRepository.save(user);
    }

    public void changePassword(String email, String currentPassword, String newPassword) {

        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
        // Validar la contraseña actual
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        // Hash de la nueva contraseña
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
    }

    public void hardResetPassword(String email, String newPassword) {

        User user = userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);

        // Hash de la nueva contraseña
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
    }



    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }


    public UserDto findUserByEmail(String email) {

        return MapperUtils.mapToDto(userRepository.findUserByEmailAndActiveTrue(email)
                .orElseThrow(UserNotFoundException::new));
    }


    public Long findIdUserByEmail(String email) {

        return userRepository.findIdByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public List<ProjectDto> findProjectsByEmail(String email) {
        return userRepository.findProjectsByEmail(email)
                .map(projects -> projects.stream()
                        .map(MapperUtils::mapProjectToDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<LanguagePairDto> findLanguagesByEmail(String email) {
        return userRepository.findLanguagePairsByEmail(email)
                .map(projects -> projects.stream()
                        .map(MapperUtils::mapToLanguagePairDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public boolean emailExists(String email) {
        return userRepository.findUserByEmailAndActiveTrue(email).isPresent();
    }

    //Sirve para no dar del alta un usuario con el email duplicado si existe en la base de datos y fue borrado
    public boolean emailExistsDeleted(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


    public UserDto registerUser(UserRequestDto userRequestDto, RoleType roleType) {
        // Mapea el UserRequestDto a una entidad User
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = MapperUtils.mapRequestToUser(userRequestDto, roleType);

        // Verificar si el correo ya existe, incluso si está marcado como eliminado
        Optional<Long> existingUserId = userRepository.findIdByEmail(user.getEmail());
        if (existingUserId.isPresent() && emailExistsDeleted(user.getEmail())) {
            // Si el correo estuvo registrado anteriormente, reactiva el registro
            user.setId(existingUserId.get());
            user = updateUser(user); // Actualiza los datos del usuario existente
        } else {
            // Si no existe, guarda el nuevo usuario
            user = userRepository.save(user);
        }

        // Retorna un DTO con la información del usuario
        return MapperUtils.mapToDto(user);
    }


    public void softDeleteUser(Long id) {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Marca como inactivo
            user.setActive(false);
            // Guarda los cambios
            userRepository.save(user);
        } else {
            // Usuario no encontrado
            throw new UserNotFoundException(id);
        }
    }


    public boolean changeUserEmail(String oldEmail, String newEmail) {
        // Validar que los correos no sean nulos o vacíos
        if (oldEmail == null || oldEmail.isBlank() || newEmail == null || newEmail.isBlank()) {
            throw new InvalidDataException("Emails cannot be null or empty");
        }

        // Validar que el nuevo correo no sea igual al anterior
        if (oldEmail.equals(newEmail)) {
            throw new InvalidDataException("The new email cannot be the same as the current email.");
        }

        // Verificar si el nuevo correo ya está en uso
        if (emailExists(newEmail)) {
            throw new UserAlreadyExistsException("The email " + newEmail + " is already in use.");
        }

        // Buscar el usuario por el correo electrónico anterior
        User user = userRepository.findUserByEmail(oldEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email " + oldEmail + " not found"));

        // Cambiar el correo electrónico
        user.setEmail(newEmail);

        // Guardar los cambios en la base de datos
        userRepository.save(user);

        return true;
    }

    public Address getAddress(String email) {

        return userRepository.findAddressByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {

        return userRepository.findUserByEmail(email).orElseThrow(()
                -> new UserNotFoundException(email));
    }

    public List<User> getUsersByLanguagePair(LanguagePair languagePair) {

        return userRepository.findByLanguagePairsContains(languagePair).orElseThrow();
    }



}