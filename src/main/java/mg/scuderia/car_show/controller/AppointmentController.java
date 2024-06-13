package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.Appointment;
import mg.scuderia.car_show.model.enums.AppointmentStatus;
import mg.scuderia.car_show.service.implementations.AppointmentService;
import mg.scuderia.car_show.specification.GenericModelSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/car_show/appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final GenericModelSpecification<Appointment> genericModelSpecification;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createAppointment = appointmentService.save(appointment);
        return ResponseEntity.ok(createAppointment);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> searchAppointment(
            @RequestParam(required = false) Integer appointmentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String contact,
            @RequestParam(required = false) LocalDate appointmentDate,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Integer carId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
    )
    {
        Specification<Appointment> specification = Specification.where(null);

        if (appointmentId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(appointmentId, "appointmentId"));
        }
        if (name != null) {
            specification = specification.and(genericModelSpecification.hasString(name, "name"));
        }
        if (firstName != null) {
            specification = specification.and(genericModelSpecification.hasString(firstName, "firstName"));
        }
        if (email != null) {
            specification = specification.and(genericModelSpecification.hasString(email, "email"));
        }
        if (message != null) {
            specification = specification.and(genericModelSpecification.hasString(message, "message"));
        }
        if (contact != null) {
            specification = specification.and(genericModelSpecification.hasString(contact, "contact"));
        }
        if (appointmentDate != null) {
            specification = specification.and(genericModelSpecification.hasLocalDate(appointmentDate, "appointmentDate"));
        }
        if (status != null) {
            specification = specification.and(genericModelSpecification.hasString(String.valueOf(status), "status"));
        }
        if (carId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(carId, "carId"));
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<Appointment> appointmentPage = appointmentService.searchAppointment(specification, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(appointmentPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(appointmentPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable int id,
            @RequestBody Appointment appointment
    ) {
        Appointment toUpdate = appointmentService.update(id, appointment);
        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}