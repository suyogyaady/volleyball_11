package com.system.volleyball.entity;



import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Volleyball")

public class Volleyball {
    @Id
    @SequenceGenerator(name = "shb_product_seq_gen", sequenceName = "shb_product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "shb_product_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer fut_salId;

    private String volleyballname;

    private String volleyballprice;

    private String volleyballcontact;

    private String volleyballlocation;

    private String volleyballimage;
    private String volleyballimage1;
    private String volleyballimage2;

    private String Description;

    @Transient
    private String imageBase64;

    @Transient
    private String image1Base64;


    @Transient
    private String image2Base64;

}
