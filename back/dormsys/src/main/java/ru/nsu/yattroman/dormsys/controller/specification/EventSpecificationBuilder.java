package ru.nsu.yattroman.dormsys.controller.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.yattroman.dormsys.entity.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventSpecificationBuilder {
    private final List<SearchCriteria> params;

    public EventSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public EventSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Event> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Event>> specs = params.stream().map(EventSpecification::new).collect(Collectors.toList());
        Specification<Event> result = specs.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(specs.get(i));
        }
        return result;
    }
}
