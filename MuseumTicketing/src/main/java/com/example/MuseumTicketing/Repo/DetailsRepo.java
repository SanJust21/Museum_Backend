package com.example.MuseumTicketing.Repo;

import com.example.MuseumTicketing.Model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailsRepo extends JpaRepository<Details, Long>{

    Optional<Details> findBySessionId(String sessionId);

    Details findByMobileNumber(String mobileNumber);
}
