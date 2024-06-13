package mg.scuderia.car_show.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "power", nullable = false)
    private int power;

    @Column(name = "place_number", nullable = false)
    private int placeNumber;

    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Images> images;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "car_type_id")
    private CarTypes carTypes;

    @ManyToOne
    @JoinColumn(name = "motor_type_id")
    private MotorTypes motorTypes;

}