package com.example.MuseumTicketing.Service.BasePrice;

import com.example.MuseumTicketing.DTO.PriceRequest;
import com.example.MuseumTicketing.Model.Price;
import com.example.MuseumTicketing.Repo.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PriceRequestService {


    @Autowired
    private PriceRepo priceRepo;

    public List<PriceRequest> getAllPrice() {
        List<Price> priceList = priceRepo.findAll();

        // Creating a list to store UserTypeDTO objects
        List<PriceRequest> priceReq = new ArrayList<>();

        // Looping through each UserType and creating a UserTypeDTO
        for (Price userType : priceList) {
            PriceRequest transfer = new PriceRequest();
            transfer.setId(userType.getId());
            transfer.setPrice(userType.getPrice());
            transfer.setType(userType.getType());
            transfer.setCategory(userType.getCategory());


            priceReq.add(transfer);
        }

        return priceReq ;
    }


}

