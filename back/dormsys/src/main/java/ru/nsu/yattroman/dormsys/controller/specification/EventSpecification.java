package ru.nsu.yattroman.dormsys.controller.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.yattroman.dormsys.entity.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@Slf4j
public class EventSpecification implements Specification<Event> {
    private SearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()){
            case ">" -> {
                return builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
            case "<" -> {
                return builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
            case ";" -> {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse((String) criteria.getValue());
                } catch (ParseException e) {
                    log.error("Invalid date:" + e.getMessage());
                    return null;
                }
                return builder.equal(root.get(criteria.getKey()), date);
            }
            case ":" -> {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        return null;
    }
}
