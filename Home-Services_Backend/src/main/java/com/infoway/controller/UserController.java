package com.infoway.controller;

import com.infoway.entity.Furniture;
import com.infoway.entity.User;
import com.infoway.mailsender.JavaMailSenderAPI;
import com.infoway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderAPI javaMailSenderAPI;
    @PostMapping(value = "/signup", consumes = {"application/json"})
    public ResponseEntity<String> signup(@RequestBody User user) {
        String msg = userService.saveUser(user);
        String mail = user.getEmail();
        // Get the user's name from the User object
        String userName = user.getFirstName();
        // Send a welcome email with the user's name
        javaMailSenderAPI.sendMail(mail,
                "Welcome to Home Services - Account Confirmation",
                "Dear " + userName + ",\n\n" +
                        "Thank you for signing up with Home Services! We are delighted to welcome you to our community.\n\n" +
                        "Your registration is now complete, and you can start enjoying the benefits of our services. Log in to our official website to explore a wide range of services and easily make bookings.\n\n" +
                        "If you have any questions or need assistance, feel free to reach out to our customer support team. We are here to ensure your experience with Home Services is seamless and enjoyable.\n\n" +
                        "Best regards,\n" +
                        "The Home Services Team"
        );

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }


    @GetMapping(value="/signin", produces = {"application/json"})
    public ResponseEntity<User> signin(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.getUserByMailAndPassword(email, password);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/getall", produces = {"application/json"})
    public ResponseEntity<List<User>> getAll(){
        List<User> list=userService.getAllUser();
      //  System.out.println(list);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }



}
