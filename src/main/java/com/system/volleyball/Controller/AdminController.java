package com.system.volleyball.Controller;

import com.system.volleyball.Pojo.BookingPojo;
import com.system.volleyball.Pojo.VolleyballPojo;
import com.system.volleyball.Service.BookingService;
import com.system.volleyball.Service.ContactService;
import com.system.volleyball.Service.VolleyballService;
import com.system.volleyball.Service.UserService;
import com.system.volleyball.entity.Booking;
import com.system.volleyball.entity.Contact;
import com.system.volleyball.entity.Volleyball;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final VolleyballService volleyballService;
    private final BookingService bookingService;
    private final ContactService contactService;

    @GetMapping("/dashboard")
    public String fetchAllbooking(Model model){
        List<Booking> adminbooking = bookingService.fetchAll();
        model.addAttribute("book", adminbooking.stream().map(booking ->
                Booking.builder()
                        .bookId(booking.getBookId())
                        .date(booking.getDate())
                        .starting(booking.getStarting())
                        .user(booking.getUser())
                        .volleyball(booking.getVolleyball())
                        .email(booking.getEmail())
                        .build()
        ));

        model.addAttribute("books", new BookingPojo());



        return "dashboard";
    }

    @GetMapping("/contact")
    public String createcontact(Model model) {
        List<Contact> admincontact = contactService.fetchAll();
        model.addAttribute("contact", admincontact.stream().map(contact ->
                Contact.builder()
                        .contactId(contact.getContactId())
                        .contactname(contact.getContactname())
                        .contactemail(contact.getContactemail())
                        .contactsubject(contact.getContactsubject())
                        .contactmessage(contact.getContactmessage())
                        .build()
        ));
        return "viewreview";
    }

    @GetMapping("/view")
    public String fetchAllVolleyballpool(Model model){
        List<Volleyball> adminvolleyball = volleyballService.fetchAll();
        model.addAttribute("volleyballs", adminvolleyball.stream().map(volleyball ->
                volleyball.builder()
                        .fut_salId(volleyball.getFut_salId())
                        .volleyballname(volleyball.getVolleyballname())
                        .volleyballprice(volleyball.getVolleyballprice())
                        .volleyballcontact(volleyball.getVolleyballcontact())
                        .volleyballlocation(volleyball.getVolleyballlocation())
                        .Description(volleyball.getDescription())
                        .imageBase64(getImageBase64(volleyball.getVolleyballimage()))
                        .image1Base64(getImageBase64(volleyball.getVolleyballimage1()))
                        .image2Base64(getImageBase64(volleyball.getVolleyballimage2()))
                        .build()
        ));
        return "viewvolleyball";
    }


    @GetMapping("/del/{id}")
    public String deletereview(@PathVariable("id") Integer id) {
        contactService.deleteById(id);
        return "redirect:/admin/dashboard";
    }


    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/images/";
        File file = new File(filePath + fileName);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

//    @GetMapping("/review")
//    public String review() {
//
//        return "viewreview";
//    }
    @GetMapping("/report")
    public String report() {

        return "report";
    }

    @GetMapping("/product/{id}")
    public String getVolleyballProfiile(@PathVariable("id") Integer id, Model model ){
        Volleyball volleyball = volleyballService.fetchById(id);
        model.addAttribute("volleyballs", new VolleyballPojo(volleyball));

        model.addAttribute("clickedvolleyball", volleyball);
        return "editvolleyball";
    }
    @GetMapping("/edit/{id}")
    public String editvolleyball(@PathVariable("id") Integer id, Model model){
        Volleyball volleyball =volleyballService.fetchById(id);
        model.addAttribute("clickedvolleyball", new VolleyballPojo(volleyball));
        return "redirect:/admin/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        volleyballService.deleteById(id);
        return "redirect:/admin/view";
    }


    @PostMapping("/changepassword")
    public String changepassword(@RequestParam("email") String email, Model model, @Valid BookingPojo bookingPojo){
        bookingService.processPasswordResetRequest(bookingPojo.getEmail());
        model.addAttribute("message","Your new password has been sent to your email Please " +
                "check your inbox");
        return "redirect:/admin/report";
    }






}