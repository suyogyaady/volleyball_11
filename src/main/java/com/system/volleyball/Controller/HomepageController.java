package com.system.volleyball.Controller;

import com.system.volleyball.Service.VolleyballService;
import com.system.volleyball.Service.UserService;
import com.system.volleyball.entity.Volleyball;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomepageController {
    private final VolleyballService volleyballService;
    private final UserService userService;

@GetMapping("/homepage")
public String getAllVolleyball(Model model, Principal principal , Authentication authentication ){
    if (authentication!=null){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("Admin")) {
                return "redirect:/admin/dashboard";
            }
        }
    }

    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
        return "redirect:/user/login";
    }

    List<Volleyball> hvolleyball = volleyballService.fetchAll();
    model.addAttribute("userdata",userService.findByEmail(principal.getName()));
    model.addAttribute("volleyballs", hvolleyball.stream().map(volleyball ->
            Volleyball.builder()
                    .fut_salId(volleyball.getFut_salId())
                    .volleyballname(volleyball.getVolleyballname())
                    .Description(volleyball.getDescription())
                    .imageBase64(getImageBase64(volleyball.getVolleyballimage()))
                    .build()
    ));
    return "Homepage";
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

