package com.rocs.infirmary.application.repository.section;

import com.rocs.infirmary.application.domain.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * this is an interface of person repository
 * */
public interface SectionRepository extends JpaRepository<Section, Long> {
    /**
     * Finds a section by its unique ID.
     *
     * @param id the ID of the section
     * @return the Section entity if found, otherwise null
     */
    Section findSectionById(Long id);
}
