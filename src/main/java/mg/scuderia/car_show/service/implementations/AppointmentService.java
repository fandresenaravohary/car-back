package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.Appointment;
import mg.scuderia.car_show.repository.AppointmentRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService extends GenericService<Appointment, Integer> {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        super(appointmentRepository);
        this.appointmentRepository = appointmentRepository;
    }

    public Page<Appointment> searchAppointment(Specification<Appointment> specification, Pageable pageable) {
        return appointmentRepository.findAll(specification, pageable);
    }

}