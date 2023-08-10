package com.system.volleyball.Service;

import com.system.volleyball.Pojo.VolleyballPojo;
import com.system.volleyball.entity.Volleyball;

import java.io.IOException;
import java.util.List;

public interface VolleyballService {
    VolleyballPojo savevolleyball(VolleyballPojo volleyballPojo) throws IOException;

    Volleyball fetchById(Integer id);

    List<Volleyball> fetchAll();

    void deleteById(Integer id);
}
