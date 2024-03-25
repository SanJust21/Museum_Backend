package com.example.MuseumTicketing.Controller.AdminScanner;

import com.example.MuseumTicketing.DTO.AdminScanner.ScanRequest;
import com.example.MuseumTicketing.Model.ScannedDetails;
import com.example.MuseumTicketing.Model.Users;
import com.example.MuseumTicketing.Repo.UsersRepo;
import com.example.MuseumTicketing.Service.AdminScanner.ScannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/scanner")
@RequiredArgsConstructor
public class ScannerController {

    private final ScannerService scannerService;

    private final UsersRepo usersRepo;


    @GetMapping("/hello/{employeeId}")
    public ResponseEntity<String> sayHello(@PathVariable String employeeId) {

        Optional<Users> user = usersRepo.findByEmployeeId(employeeId);

        if (user.isPresent()) {
            Users emp = user.get();
            return ResponseEntity.ok("Hello " + emp.getName());
        } else {
            return ResponseEntity.ok("Hello Scanner");
        }
    }


    @PostMapping("/scan")
    public ResponseEntity<?> scanTicket(@RequestBody ScanRequest scanRequest) {
        return scannerService.identifyUserAndGetDetails(scanRequest.getTicketId(), scanRequest.getScannedTime());
//        ResponseEntity<?> response = scannerService.identifyUserAndGetDetails(scanRequest.getTicketId(), scanRequest.getScannedTime());
//        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Visit date is different.");
//        }
//        return response;
    }


    @GetMapping("/scannedList")
    public ResponseEntity<List<ScannedDetails>> getScannedDetailsForToday() {
        // Retrieve scanned details for today from the service
        List<ScannedDetails> scannedDetailsList = scannerService.getScannedDetailsForToday();

        // Sort the scanned details based on scanned time
        scannedDetailsList.sort(Comparator.comparing(ScannedDetails::getScannedTime));

        return ResponseEntity.ok(scannedDetailsList);
    }
}
