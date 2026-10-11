package kz.onestep;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class TaxiController {

    private final TaxiOrderRepository repository;

    public TaxiController(TaxiOrderRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public TaxiOrder createOrder(@RequestBody OrderRequest request) {

        TaxiOrder order = new TaxiOrder();

        order.setPassengerName("Клиент");
        order.setPickupAddress(request.from());
        order.setDestinationAddress(request.to());
        order.setTariff(request.tariff());
        order.setStatus("Қабылданды");

        return repository.save(order);
    }

    @GetMapping
    public List<TaxiOrder> getOrders() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TaxiOrder getOrder(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Тапсырыс табылмады: " + id));
    }

    @PutMapping("/{id}/status")
    public TaxiOrder updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        TaxiOrder order = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Тапсырыс табылмады: " + id));

        order.setStatus(status);

        return repository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public record OrderRequest(
            String from,
            String to,
            String tariff,
            Integer passengers
    ) {}
}