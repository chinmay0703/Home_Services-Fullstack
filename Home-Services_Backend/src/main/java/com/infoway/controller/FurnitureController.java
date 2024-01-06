package com.infoway.controller;

import com.infoway.entity.Cleaning;
import com.infoway.entity.Furniture;
import com.infoway.mailsender.JavaMailSenderAPI;
import com.infoway.repository.CleaningRepository;
import com.infoway.repository.FurnitureRepository;
import com.infoway.service.CleaningService;
import com.infoway.service.FurnitureService;
import com.infoway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("user/furniture")
public class FurnitureController {

    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private UserService userService;

    @Autowired
    private CleaningService cleaningService;

    @Autowired
    private CleaningRepository cleaningRepository;

    @Autowired
    private JavaMailSenderAPI javaMailSenderAPI;

    @PostMapping(value="/bookfurniture", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> bookUserRequest(@RequestBody Furniture furniture) {
        System.out.println(furniture);
        String msg = furnitureService.saveFurniture(furniture);
        String firstName = furniture.getUser().getFirstName();
        String mail = furniture.getUser().getEmail();

        // Additional details for the booking
        String mobileNumber = furniture.getMobile();
        int largeFurnitures = furniture.getTotalLargeFurnitures();
        int mediumFurnitures = furniture.getTotalMediumFurnitures();
        int smallFurniture=furniture.getTotalSmallFurnitures();
        String address = furniture.getAddress();

        System.out.println(firstName);

        javaMailSenderAPI.sendMail(mail,
                "Booking Details",
                "Dear " + firstName + ",\n\n" +
                        "Thank you for booking furniture cleaning service with Home Services! Below are the details of your booking:\n\n" +
                        "Mobile Number: " + mobileNumber + "\n" +
                        "Large-furniture: " + largeFurnitures + "\n" +
                        "Medium-Furnitures: " + mediumFurnitures + "\n" +
                        "Small-Furnitures: " + smallFurniture + "\n" +
                        "Address: " + address + "\n\n" +
                        "If you have any questions or need assistance, feel free to reach out to our customer support team. We are here to ensure your experience with Home Services is seamless and enjoyable.\n\n" +
                        "Best regards,\n" +
                        "The Home Services Team"
        );

        return new ResponseEntity<>(msg , HttpStatus.CREATED);
    }

    @GetMapping(value="/getbyidfurniture", produces = {"application/json"})
    public ResponseEntity<List<Furniture>> getFurniture(@RequestParam("userId") Integer uid) {
        System.out.println(uid);
        List<Furniture> list = furnitureRepository.findUnapprovedFurnitureByUserId(uid);
        if(list != null)

            return new ResponseEntity<>(list, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);


    }




}
