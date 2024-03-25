package com.example.MuseumTicketing.Controller.AdminScanner;

import com.example.MuseumTicketing.DTO.AdminScanner.JwtAuthenticationResponse;
import com.example.MuseumTicketing.DTO.AdminScanner.SignInRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.SignUpRequest;
import com.example.MuseumTicketing.DTO.AdminScanner.UpdateRoleRequest;
import com.example.MuseumTicketing.DTO.PriceRequest;
import com.example.MuseumTicketing.Model.Role;
import com.example.MuseumTicketing.Model.Users;
import com.example.MuseumTicketing.Service.AdminScanner.AuthenticationService;
import com.example.MuseumTicketing.Service.BasePrice.PriceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;

    private final PriceRequestService priceRequestService;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hai Admin");
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/addEmployee")
    public ResponseEntity<Users> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/employees")
    public ResponseEntity<List<Users>> getAllEmployees() {
        List<Users> employees = authenticationService.getAllUsersByRole(Role.EMPLOYEE);
        employees.sort(Comparator.comparingLong(Users::getId));
        return ResponseEntity.ok(employees);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/scanners")
    public ResponseEntity<List<Users>> getAllScanners() {
        List<Users> scanners = authenticationService.getAllUsersByRole(Role.SCANNER);
        scanners.sort(Comparator.comparingLong(Users::getId));
        return ResponseEntity.ok(scanners);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/allTickets")
    public ResponseEntity<List<Object>> getAllTickets() {
        List<Object> allTickets = authenticationService.getAllTickets();
        return ResponseEntity.ok(allTickets);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/uploadImg/{employeeId}")
    public ResponseEntity<?> uploadImageToFIleSystem(@PathVariable String employeeId, @RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = authenticationService.uploadImageToFileSystem(file, employeeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/downloadImg/{employeeId}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String employeeId) throws IOException {
        byte[] imageData = authenticationService.downloadImageFromFileSystem(employeeId);
        if (imageData != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found for user ID: " + employeeId);
        }
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId, @RequestBody SignUpRequest signUpRequest) {
        return authenticationService.updateEmployee(employeeId, signUpRequest);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        String  message = authenticationService.deleteEmployee(employeeId);
        return ResponseEntity.ok(message);
    }

//    @CrossOrigin(origins = "http://localhost:8081")
//    @PutMapping("/updateRole/{employeeId}")
//    public ResponseEntity<String> updateEmployeeRole(@PathVariable String employeeId, @RequestParam Role newRole) {
//        String message = authenticationService.updateEmployeeRole(employeeId, newRole);
//        return ResponseEntity.ok(message);
//    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/updateRole/{employeeId}")
    public ResponseEntity<String> updateEmployeeRole(@PathVariable String employeeId, @RequestBody UpdateRoleRequest updateRequest) {
        Role newRole = updateRequest.getNewRole();
        String newPassword = updateRequest.getNewPassword();
        String message = authenticationService.updateEmployeeRole(employeeId, newRole, newPassword);
        return ResponseEntity.ok(message);
    }
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/addPrice")
    public PriceRequest addPrice(@RequestBody PriceRequest priceRequest) {
        return priceRequestService.addPrice(priceRequest);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/deletePrice/{id}")
    public void deletePrice(@PathVariable Integer id) {
        priceRequestService.deletePriceById(id);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/updatePrice/{id}")
    public PriceRequest updatePrice(@PathVariable Integer id, @RequestBody PriceRequest priceRequest) {
        return priceRequestService.updatePrice(id, priceRequest);
    }

}
