package com.system.volleyball.Repo;

import com.system.volleyball.entity.Volleyball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolleyballRepo extends JpaRepository <Volleyball, Integer>{
}
