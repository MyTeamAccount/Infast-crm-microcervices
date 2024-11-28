package com.example.crm_left_service.service;


import com.example.crm_left_service.client.FileStorageClient;
import com.example.crm_left_service.entity.User;
import com.example.crm_left_service.entity.UserDetails;
import com.example.crm_left_service.repository.UserRepository;
import com.example.crm_left_service.repository.UserRoleRepository;
import com.example.crm_left_service.request.RegisterRequest;
import com.example.crm_left_service.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.crm_left_service.exeption.user.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;

    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userRole(userRoleRepository.findByName("USER").orElseThrow(() -> new NotFoundException("userRole not found")))
                        .build();
        return userRepository.save(toSave);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return findUserById(id);
    }

    public User getUserByEmail(String email) {
        return findUserByEmail(email);
    }


    public User updateUserById(UserUpdateRequest request, MultipartFile file) {
        User toUpdate = findUserById(request.getId());

        request.setUserDetails(updateUserDetails(toUpdate.getUserDetails(), request.getUserDetails(), file));
        modelMapper.map(request, toUpdate);

        return userRepository.save(toUpdate);
    }

    public void deleteUserById(Long id) {
        User toDelete = findUserById(id);
        userRepository.deleteById(toDelete.getId());
    }

    protected User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private UserDetails updateUserDetails(UserDetails toUpdate, UserDetails request, MultipartFile file) {
        toUpdate = toUpdate == null ? new UserDetails() : toUpdate;

        if (file != null) {
            String profilePicture = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (profilePicture != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getPhotoPath());
                toUpdate.setPhotoPath(profilePicture);
            }
        }

        modelMapper.map(request, toUpdate);

        return toUpdate;
    }
}