package mg.scuderia.car_show.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "motor_types")
public class MotorTypes {

    @Id
    @Column(name = "motor_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int motorTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}