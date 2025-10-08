package com.rocs.infirmary.application.dto.student.health.profile;
import com.rocs.infirmary.application.domain.guardian.Guardian;
import com.rocs.infirmary.application.domain.medicalHistory.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;

@Data
public class StudentHealthProfileDTO {


    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    private Guardian guardian;

    @OneToOne(cascade = CascadeType.ALL)
    private MedicalHistory medicalHistory;


}
