package com.campaign.admission.util;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Requirement;
import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static com.campaign.admission.util.AdmissionValidator.validateMarks;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.rules.ExpectedException.none;

public class AdmissionValidatorTest {

    @Rule
    public ExpectedException expectedException = none();

    private final List<Exam> examsTrue = of(
            Exam.builder()
                    .withSubject("math")
                    .withMark(170)
                    .build(),
            Exam.builder()
                    .withSubject("eng")
                    .withMark(160)
                    .build(),
            Exam.builder()
                    .withSubject("phys")
                    .withMark(165)
                    .build())
            .collect(toList());

    private final List<Exam> examsFalse = of(
            Exam.builder()
                    .withSubject("math")
                    .withMark(170)
                    .build(),
            Exam.builder()
                    .withSubject("eng")
                    .withMark(155)
                    .build(),
            Exam.builder()
                    .withSubject("phys")
                    .withMark(165)
                    .build())
            .collect(toList());

    private final List<Requirement> requirements = of(
            new Requirement("math", 160),
            new Requirement("eng", 160),
            new Requirement("phys", 160))
            .collect(toList());

    private final List<Boolean> opensTrue = asList(true, true, true);

    private final List<Boolean> opensFalse = asList(true, false, true);

    @Test
    public void validateMarksShouldReturnMarksSum() {
        int result = examsTrue.stream().mapToInt(Exam::getMark).sum();

        assertThat(validateMarks(examsTrue, requirements), is(result));
    }

    @Test
    public void validateMarksShouldThrowException() {
        expectedException.expect(AdmissionValidatorRuntimeException.class);
        expectedException.expectMessage("Insufficient marks for specialty admission!");

        validateMarks(examsFalse, requirements);
    }

    @Test
    public void validateAdmissionOpenShouldReturnOpen() {
        assertThat(validateAdmissionOpen(opensTrue), is(true));
    }

    @Test
    public void validateAdmissionOpenShouldThrowException() {
        expectedException.expect(AdmissionValidatorRuntimeException.class);
        expectedException.expectMessage("Specialties' opens aren't synchronized!");

        validateAdmissionOpen(opensFalse);
    }
}
