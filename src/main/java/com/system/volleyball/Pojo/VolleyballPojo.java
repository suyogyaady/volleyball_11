package com.system.volleyball.Pojo;

import com.system.volleyball.entity.Volleyball;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VolleyballPojo {
    private Integer fid;
    private String fname;
    private String fcontact;
    private  String fprice;
    private String flocation;
    private MultipartFile image;
    private MultipartFile image1;
    private MultipartFile image2;
    private String Description;


    public VolleyballPojo(Volleyball volleyball) {
        this.fid = volleyball.getFut_salId();
        this.fname = volleyball.getVolleyballname();
        this.fcontact= volleyball.getVolleyballcontact();
        this.fprice = volleyball.getVolleyballprice();
        this.flocation = volleyball.getVolleyballlocation();
        this.Description = volleyball.getDescription();

    }
}