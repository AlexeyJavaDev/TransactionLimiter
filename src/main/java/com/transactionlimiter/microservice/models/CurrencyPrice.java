package com.transactionlimiter.microservice.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Entity
@Table(name = "currency_price")
public class CurrencyPrice {
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datetime")
    private Date datetime;
    @Column(name = "price")
    private double price;

    public CurrencyPrice() {
    }

    public CurrencyPrice(Date datetime, double price) {
        this.datetime = datetime;
        this.price = price;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
