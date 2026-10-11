package kz.onestep;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiOrderRepository
        extends JpaRepository<TaxiOrder, Long> {
}