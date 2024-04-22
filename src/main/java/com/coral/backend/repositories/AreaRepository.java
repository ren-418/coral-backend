package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
  Area findAreaById(long id);
  Optional<Area> findAreaByName(String name);
}
