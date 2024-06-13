package mg.scuderia.car_show.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class GenericModelSpecification<T> {
    
    public Specification<T> hasString(String toFind, String fieldName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), toFind);
    }
    
    public Specification<T> hasInteger(Integer id, String fieldName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName).get("id"), id);
    }

    public Specification<T> hasBoolean(Boolean status, String fieldName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), status);
    }

    public Specification<T> hasLocalDate(LocalDate localDate, String fieldName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), localDate);
    }

    public Specification<T> hasBigDecimal(BigDecimal value, String fieldName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), value));
    }
    
}