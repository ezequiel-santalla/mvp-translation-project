package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.DataAccessRuntimeException;
import com.mvp.mvp_translation_project.exceptions.InvalidPasswordException;
import com.mvp.mvp_translation_project.exceptions.UserNotFoundException;
import com.mvp.mvp_translation_project.models.*;
import com.mvp.mvp_translation_project.repositories.UserRepository;
import com.mvp.mvp_translation_project.types.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return userRepository.findByActiveTrue().stream()
                    .map(this::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("No se pudo recuperar la lista de usuarios", e);
        }
    }


    public UserDto getUser(Long id) {

        return userRepository.findById(id).map(this::mapToDto).orElseThrow(()
                -> new UserNotFoundException(id));
    }


    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public UserDto updateUserByDto(String email, UserUpdateDto userUpdateDto) {
        // Buscar el usuario por correo electrónico
        Optional<User> optionalUser  = userRepository.findUserByEmail(email);

        // Verificar si el usuario existe
        if (optionalUser .isEmpty()) {
            throw new UserNotFoundException();
        }

        // Obtener el usuario existente
        User user = optionalUser.get();

        // Mapear los campos del DTO al usuario existente
        if(userUpdateDto.getLastName()!=null){
            user.setLastName(userUpdateDto.getLastName());
        }
        if(userUpdateDto.getBirthDate()!=null){
            user.setBirthDate(userUpdateDto.getBirthDate());
        }
        if(userUpdateDto.getCellphone()!=null){
            user.setCellphone(userUpdateDto.getCellphone());
        }
        if(userUpdateDto.getAddress()!=null){
            user.setAddress(userUpdateDto.getAddress());
        }

        // Guardar el usuario actualizado
        User updatedUser  = userRepository.save(user);

        // Retornar el DTO del usuario actualizado
        return mapToDto(updatedUser );
    }

    public User updateUser(User user) {
        // Valida que el usuario exista antes de actualizarlo
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }

        return userRepository.save(user);
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



    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }


    public UserDto findUserByEmail(String email) {

        return this.mapToDto(userRepository.findUserByEmailAndActiveTrue(email)
                .orElseThrow(UserNotFoundException::new));
    }


    public Long findIdUserByEmail(String email) {

        return userRepository.findIdByEmail(email).orElseThrow(UserNotFoundException::new);
    }


    public boolean emailExists(String email) {
        return userRepository.findUserByEmailAndActiveTrue(email).isPresent();
    }

    //Sirve para no dar del alta un usuario con el email duplicado si existe en la base de datos y fue borrado
    public boolean emailExistsDeleted(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


    public UserDto registerUser(UserRequestDto userRequestDto) {
        // Mapea el UserRequestDto a una entidad User
        User user = mapRequestToUser(userRequestDto);

        // Verificar si el correo ya existe, incluso si está marcado como eliminado
        Optional<Long> existingUserId = userRepository.findIdByEmail(user.getEmail());
        if (existingUserId.isPresent() && emailExistsDeleted(user.getEmail())) {
            // Si el correo estuvo registrado anteriormente, reactiva el registro
            user.setId(existingUserId.get());
            updateUser(user); // Actualiza los datos del usuario existente
        } else {
            // Si no existe, guarda el nuevo usuario
            user = userRepository.save(user);
        }

        // Retorna un DTO con la información del usuario
        return mapToDto(user);
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


    private UserDto mapToDto(User user) {

        UserDto userDto = new UserDto();

        // Mapeo de la entidad al DTO
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setCellphone(user.getCellphone());
        userDto.setIdentityNumber(user.getIdentityNumber());
        userDto.setBirthDate(user.getBirthDate());

        return userDto;
    }

    private User mapRequestToUser(UserRequestDto userRequestDto) {

        User user = new User();

        // Mapeo del DTO a la entidad
        user.setName(userRequestDto.getName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setBirthDate(userRequestDto.getBirthDate());
        user.setIdentityNumber(userRequestDto.getIdentityNumber());
        user.setCellphone(userRequestDto.getCellphone());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(RoleType.TRANSLATOR);
        user.setActive(true); // Activa el usuario por defecto

        return user;
    }


}