package com.system.volleyball.Controller;

import com.system.volleyball.Pojo.BookingPojo;
import com.system.volleyball.Pojo.VolleyballPojo;
import com.system.volleyball.Service.BookingService;
import com.system.volleyball.Service.VolleyballService;
import com.system.volleyball.Service.UserService;
import com.system.volleyball.entity.Volleyball;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ball")
public class VolleyballController {
    private final VolleyballService volleyballService;
    private final UserService userService;
    private final BookingService bookingService;
//    @GetMapping("/product")
//    public String product() {
//
//        return "bookvolleyball";
//    }

    @GetMapping("/product/{id}")
    public String getVolleyballProfiile(@PathVariable("id") Integer id, Model model, Principal principal){
        Volleyball volleyball = volleyballService.fetchById(id);
        model.addAttribute("userdata",userService.findByEmail(principal.getName()));

        model.addAttribute("savebooking", new BookingPojo() );

        model.addAttribute("volleyballs", new VolleyballPojo(volleyball));
//
        model.addAttribute("clickedvolleyball", volleyball);

        return "bookvolleyball";
    }

    @PostMapping("/sbooking")
    public String savebooking(@Valid BookingPojo bookingPojo){
        if (!bookingService.bookedTime(Date.valueOf(bookingPojo.getDate()), bookingPojo.getFid()).contains(bookingPojo.getStarting())) {
            bookingService.saveOrder(bookingPojo);
            return "redirect:/home/homepage";
        } else return "redirect:/ball/product/"+bookingPojo.getFid();
    }

    @GetMapping("/addvolleyball")
    public String createVolleyball(Model model) {
        model.addAttribute("volleyball", new VolleyballPojo());

        return "volleyball";
    }
    @PostMapping("/save")
    public String savevolleyball(@Valid VolleyballPojo volleyballPojo, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException{
        Map<String, String> requestError = validateRequest(bindingResult);
        if (requestError != null) {
            redirectAttributes.addFlashAttribute("requestError", requestError);
            return "redirect:/admin/addvolleyball";
        }
        volleyballService.savevolleyball(volleyballPojo);
        redirectAttributes.addFlashAttribute("successMsg", "User saved successfully");
        return "redirect:/admin/dashboard";
    }

    private Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
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












}
