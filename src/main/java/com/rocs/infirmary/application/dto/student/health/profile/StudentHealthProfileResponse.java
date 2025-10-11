package com.rocs.infirmary.application.dto.student.health.profile;
import com.rocs.infirmary.application.domain.student.Student;
import jakarta.persistence.OneToOne;
import lombok.Data;
import jakarta.persistence.*;

@Data
public class StudentHealthProfileResponse {

    private Student student;


}
