package com.example.MuseumTicketing.Controller.AdminScanner;

import com.example.MuseumTicketing.DTO.AdminScanner.CategoryVisitorDTO;
import com.example.MuseumTicketing.DTO.DetailsRequest;
import com.example.MuseumTicketing.Service.AdminScanner.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/currentDayList")
    public ResponseEntity<List<DetailsRequest>> getCurrentDayDetails() {
        List<DetailsRequest> currentDayDetails = dashboardService.getCurrentDayDetails();
        return ResponseEntity.ok(currentDayDetails);
    }

    @GetMapping("/totalVisitors/currentDate")
    public ResponseEntity<List<CategoryVisitorDTO>> getTotalVisitorsForCurrentDate() {
        List<CategoryVisitorDTO> visitorDTOs = dashboardService.getTotalVisitorsForCurrentDate();
        return ResponseEntity.ok(visitorDTOs);
    }

    // Controller method for getting total visitors for the current week
    @GetMapping("/totalVisitors/week")
    public ResponseEntity<List<CategoryVisitorDTO>> getTotalVisitorsForWeek() {
        List<CategoryVisitorDTO> visitorDTOs = dashboardService.getTotalVisitorsForWeek();
        return ResponseEntity.ok(visitorDTOs);
    }

    // Controller method for getting total visitors for a custom week
    @GetMapping("/totalVisitors/week/custom")
    public ResponseEntity<List<CategoryVisitorDTO>> getTotalVisitorsForCustomWeek(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CategoryVisitorDTO> visitorDTOs = dashboardService.getTotalVisitorsForWeek(startDate, endDate);
        return ResponseEntity.ok(visitorDTOs);
    }


}
