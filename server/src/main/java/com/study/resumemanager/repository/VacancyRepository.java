package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Vacancy findByPositionTitle(String positionTitle);
    Vacancy findByDescription(String description);
    List<Vacancy> findAllByAuthorUsername(String username);
    Vacancy findByIdAndAuthorUsername(Long id, String username);

}
