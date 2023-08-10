package com.system.volleyball.Service.Impl;


import com.system.volleyball.Pojo.VolleyballPojo;
import com.system.volleyball.Repo.VolleyballRepo;
import com.system.volleyball.Service.VolleyballService;
import com.system.volleyball.entity.Volleyball;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolleyballServiceImpl implements VolleyballService {

    private final VolleyballRepo volleyballRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images/";


    @Override
    public VolleyballPojo savevolleyball(VolleyballPojo volleyballPojo) throws IOException {
        Volleyball volleyball = new Volleyball();
        if (volleyballPojo.getFid()!= null){
            volleyball.setFut_salId(volleyballPojo.getFid());
        }
        volleyball.setVolleyballname(volleyballPojo.getFname());
        volleyball.setVolleyballprice(volleyballPojo.getFprice());
        volleyball.setVolleyballcontact(volleyballPojo.getFcontact());
        volleyball.setVolleyballlocation(volleyballPojo.getFlocation());
        volleyball.setDescription(volleyballPojo.getDescription());



        if(volleyballPojo.getImage1()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, volleyballPojo.getImage1().getOriginalFilename());
            fileNames.append(volleyballPojo.getImage1().getOriginalFilename());
            Files.write(fileNameAndPath, volleyballPojo.getImage1().getBytes());

            volleyball.setVolleyballimage1(volleyballPojo.getImage1().getOriginalFilename());
        }
        if(volleyballPojo.getImage2()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, volleyballPojo.getImage2().getOriginalFilename());
            fileNames.append(volleyballPojo.getImage2().getOriginalFilename());
            Files.write(fileNameAndPath, volleyballPojo.getImage2().getBytes());

            volleyball.setVolleyballimage2(volleyballPojo.getImage2().getOriginalFilename());
        }
        if(volleyballPojo.getImage()!=null){
//            System.out.println(UPLOAD_DIRECTORY);
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, volleyballPojo.getImage().getOriginalFilename());
            fileNames.append(volleyballPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, volleyballPojo.getImage().getBytes());

            volleyball.setVolleyballimage(volleyballPojo.getImage().getOriginalFilename());
        }
        volleyballRepo.save(volleyball);
        return new VolleyballPojo(volleyball);
    }

    @Override
    public Volleyball fetchById(Integer id) {
        Volleyball volleyball = volleyballRepo.findById(id).orElseThrow(()-> new RuntimeException("Couldnot find"));
        volleyball = Volleyball.builder()
                .fut_salId(volleyball.getFut_salId())
                .imageBase64(getImageBase64(volleyball.getVolleyballimage()))
                .image1Base64(getImageBase64(volleyball.getVolleyballimage1()))
                .image2Base64(getImageBase64(volleyball.getVolleyballimage2()))
                .volleyballname(volleyball.getVolleyballname())
                .volleyballcontact(volleyball.getVolleyballcontact())
                .volleyballprice(volleyball.getVolleyballprice())
                . volleyballlocation(volleyball.getVolleyballlocation())
                .Description(volleyball.getDescription())
                .build();
        return volleyball;
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

    @Override
    public List<Volleyball> fetchAll() {
        return volleyballRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        volleyballRepo.deleteById(id);
    }

}
