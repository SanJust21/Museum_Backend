package com.example.MuseumTicketing.Service.AdminScanner;

import com.example.MuseumTicketing.DTO.AdminScanner.JwtAuthenticationResponse;
import com.example.MuseumTicketing.DTO.AdminScanner.RefreshTokenRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.SignInRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.SignUpRequest;
import com.example.MuseumTicketing.Model.*;
import com.example.MuseumTicketing.Repo.UsersRepo;
import com.example.MuseumTicketing.Service.Details.ForeignerDetailsService;
import com.example.MuseumTicketing.Service.Details.InstitutionDetailsService;
import com.example.MuseumTicketing.Service.Details.PublicDetailsService;
import com.example.MuseumTicketing.Service.Jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UsersRepo usersRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PublicDetailsService publicDetailsService;

    private final InstitutionDetailsService institutionDetailsService;

    private final ForeignerDetailsService foreignerDetailsService;

    private final String FOLDER_PATH = "C:/Users/azhym/Desktop/Museum Employees/";


    public Users signup(SignUpRequest signUpRequest){

        Users users = new Users();

        String employeeId = generateEmployeeId();
        users.setEmployeeId(employeeId);

        //String password = generateRandomPassword();
        String password = "password";
        users.setPassword(passwordEncoder.encode(password));
        //users.setPassword(password);


        users.setEmail(signUpRequest.getEmail());
        users.setName(signUpRequest.getName());
        //users.setEmployeeId(signUpRequest.getEmployeeId());
        users.setImage(signUpRequest.getImage());
        users.setPhoneNo(signUpRequest.getPhoneNo());
        users.setTempAddress(signUpRequest.getTempAddress());
        users.setPermAddress(signUpRequest.getPermAddress());
        users.setRole(Role.EMPLOYEE);
        //users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return usersRepo.save(users);

    }

    private String generateEmployeeId() {
        // Retrieve the latest employee ID from the database
        String lastEmployeeId = usersRepo.findMaxEmployeeId();

        if (lastEmployeeId == null || lastEmployeeId.isEmpty()) {
            return "EMP10001";
        }

        // Extract the numerical part of the employee ID
        String numericPart = lastEmployeeId.substring(3);

        // Convert the numerical part to an integer
        int lastNumericId = Integer.parseInt(numericPart);

        // Increment the latest employee ID
        lastNumericId++;

        // Construct the new employee ID
        return "EMP" + lastNumericId;
    }

    private String generateRandomPassword() {
        // Generate a random alphanumeric password with a length of 8 characters
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public List<Object> getAllTickets() {
        List<Object> allTickets = new ArrayList<>();

        // Retrieve public details and add to the list
        List<PublicDetails> publicDetails = publicDetailsService.getAllPublicDetails();
        allTickets.addAll(publicDetails);

        // Retrieve institution details and add to the list
        List<InstitutionDetails> institutionDetails = institutionDetailsService.getAllInstitutionDetails();
        allTickets.addAll(institutionDetails);

        // Retrieve foreigner details and add to the list
        List<ForeignerDetails> foreignerDetails = foreignerDetailsService.getAllForeignerDetails();
        allTickets.addAll(foreignerDetails);

        return allTickets;
    }

    public List<Users> getAllUsersByRole(Role role) {
        return usersRepo.findAllByRole(role);
    }


    public ResponseEntity<?> updateEmployee(String employeeId, SignUpRequest signUpRequest) {
        Users existingUser = usersRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        existingUser.setEmail(signUpRequest.getEmail());
        existingUser.setName(signUpRequest.getName());
        //existingUser.setImage(signUpRequest.getImage());
        existingUser.setPhoneNo(signUpRequest.getPhoneNo());
        existingUser.setTempAddress(signUpRequest.getTempAddress());
        existingUser.setPermAddress(signUpRequest.getPermAddress());

        Users updatedEmployee = usersRepo.save(existingUser);

        String message = "Employee details updated successfully!";

        Map<String, Object> response = new HashMap<>();
        response.put("Details", updatedEmployee);
        response.put("Message", message);

        return ResponseEntity.ok(response);
    }

    public String deleteEmployee(String employeeId) {
        Users existingUser = usersRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        usersRepo.delete(existingUser);
        return "Employee details deleted successfully!";
    }

    public String updateEmployeeRole(String employeeId, Role newRole, String newPassword) {
        Users user = usersRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
        user.setRole(newRole);

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        usersRepo.save(user);
        return "Role updated successfully!";
    }

    public String uploadImageToFileSystem(MultipartFile file, String employeeId) throws IOException {
        String fileName = employeeId + "_" + file.getOriginalFilename();
        String filePath = FOLDER_PATH + fileName;

        try{
        // Save the file path to the User entity
        Users user = usersRepo.findByEmployeeId(employeeId).orElse(null);
        if (user != null) {
            user.setImage(filePath);
            usersRepo.save(user);
        }

        file.transferTo(new File(filePath));

        return "File uploaded successfully: " + filePath;
    }
        catch (IOException e){
            return "Error uploading file: " + e.getMessage();
        }
    }


    public byte[] downloadImageFromFileSystem(String employeeId) throws IOException {
        Users user = usersRepo.findByEmployeeId(employeeId).orElse(null);
        if (user != null && user.getImage() != null) {
            byte[] imageBytes = Files.readAllBytes(Paths.get(user.getImage()));
            return imageBytes;
        }
        return null;
    }


    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmployeeId(), signInRequest.getPassword()));

            var user = usersRepo.findByEmployeeId(signInRequest.getEmployeeId()).orElseThrow(() ->new IllegalArgumentException("Invalid Name or password"));
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        Users user = usersRepo.findByEmployeeId(userName).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}