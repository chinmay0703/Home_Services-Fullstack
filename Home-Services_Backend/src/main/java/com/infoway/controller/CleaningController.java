package com.infoway.controller;
import com.infoway.entity.Approved;
import com.infoway.entity.Cleaning;

import com.infoway.entity.Furniture;
import com.infoway.entity.User;
import com.infoway.mailsender.JavaMailSenderAPI;
import com.infoway.repository.CleaningRepository;
import com.infoway.service.CleaningService;

import com.infoway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("user/cleaning")
public class CleaningController {

    @Autowired
    private JavaMailSenderAPI javaMailSenderAPI;
    @Autowired
    private CleaningService cleaningService;

    @Autowired
    private UserService userService;

    @Autowired
    private CleaningRepository cleaningRepository;
    @PostMapping(value="/bookfurniture",consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity<String> bookUserRequest(@RequestBody Cleaning cleaning) {
        System.out.println(cleaning);
        String msg = cleaningService.saveCleaning(cleaning);
        String name=cleaning.getUser().getFirstName();
        String mail=cleaning.getUser().getEmail();
        String mobileNumber=cleaning.getMobilenumber();
        String rooms=cleaning.getRooms();
        String date=cleaning.getDate();
        String address=cleaning.getAddress();
        javaMailSenderAPI.sendMail(mail,
                "Booking Details",
                "Dear " + name + ",\n\n" +
                        "Thank you for booking home cleaning service with Home Services! Below are the details of your booking:\n\n" +
                        "Mobile Number: " + mobileNumber + "\n" +
                        "No of Rooms: " + rooms + "\n" +
                        "Date: " + date + "\n" +
                        "Address: " + address + "\n\n" +
                        "If you have any questions or need assistance, feel free to reach out to our customer support team. We are here to ensure your experience with Home Services is seamless and enjoyable.\n\n" +
                        "Best regards,\n" +
                        "The Home Services Team"
        );
        return  new ResponseEntity<>(msg , HttpStatus.CREATED);
    }
    @GetMapping(value = "/getbyidcleaning", produces = "application/json")
    public ResponseEntity<List<Cleaning>> getCleaning(@RequestParam("userId") Integer uid) {
        System.out.println("Request received for user ID: " + uid);
        List<Cleaning> list = cleaningRepository.findUnapprovedFurnitureByUserId(uid);
        if (list != null)
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(value="/approve",consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity<String> getApproved(@RequestBody Approved approved){
        Integer userId=approved.getUserId();
        Cleaning cleaning = cleaningRepository.getById(userId);
        if(cleaning!=null) {
            cleaning.setStatus(approved.getStatus());
            cleaningRepository.save(cleaning);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }
        return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
    }
}
