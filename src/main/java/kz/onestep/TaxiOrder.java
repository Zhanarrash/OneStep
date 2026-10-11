package kz.onestep;

import jakarta.persistence.*;

@Entity
@Table(name = "taxi_orders")
public class TaxiOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private String pickupAddress;
    private String destinationAddress;
    private String tariff;
    private String status;

    public TaxiOrder() {
    }

    public TaxiOrder(String passengerName,
                     String pickupAddress,
                     String destinationAddress,
                     String tariff) {
        this.passengerName = passengerName;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.tariff = tariff;
        this.status = "Қабылданды";
    }

    public Long getId() {
        return id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}