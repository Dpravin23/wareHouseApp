package in.nit.spec;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import in.nit.model.OrderMethod;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
//generate dynamic where condition

@SuppressWarnings("serial")
public class OrderMethodSpec implements Specification<OrderMethod> {

	private OrderMethod filter;

	public OrderMethodSpec(OrderMethod filter) {
		this.filter = filter;
	}
	
	    @Override
	    public Predicate toPredicate(Root<OrderMethod> root,
	                                 CriteriaQuery<?> query,
	                                 CriteriaBuilder cb) {

			List<Predicate> predicates = new ArrayList<>(); 
	    	

	        if (filter.getOrderCode() != null && !filter.getOrderCode().isBlank()) {
	        	predicates.add(cb.like(root.get("orderCode"), "%" + filter.getOrderCode() + "%"));
	        }
	        if (filter.getDescription() != null && !filter.getDescription().isBlank()) {
	        	predicates.add(cb.like(root.get("description"), "%" + filter.getDescription() + "%"));
	        }
	        if (filter.getOrderMode() != null && !filter.getOrderMode().isBlank()) {
	        	predicates .add(cb.like(root.get("orderMode"), "%" + filter.getOrderMode() + "%"));
	        }
	        // Add more conditions here as needed
	        // e.g., if (filter.getDescription() != null) { ... }

			 return cb.and(predicates.toArray(new Predicate[0])); 
	        
	    }
	}
