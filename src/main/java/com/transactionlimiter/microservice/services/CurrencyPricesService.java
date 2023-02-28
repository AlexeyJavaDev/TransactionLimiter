package com.transactionlimiter.microservice.services;

import com.transactionlimiter.microservice.models.CurrencyPrice;
import com.transactionlimiter.microservice.repositories.CurrencyPricesRepository;
import com.transactionlimiter.microservice.util.ZonedDateTimeCreator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Transactional(readOnly = true)
public class CurrencyPricesService {
    private final CurrencyPricesRepository currencyPricesRepository;
    private static final String url = "https://api.twelvedata.com/price?symbol=AAPL&apikey=demo";  // API for USD/RUB pair
    private static final String limitCurrency = "USD";
    private CurrencyPrice currencyPrice;
    private final ZonedDateTimeCreator zonedDateTimeCreator;
    @Autowired
    public CurrencyPricesService(CurrencyPrice currencyPrice, CurrencyPricesRepository currencyPricesRepository, ZonedDateTimeCreator zonedDateTimeCreator) {
        this.currencyPrice = currencyPrice;
        this.currencyPricesRepository = currencyPricesRepository;
        this.zonedDateTimeCreator = zonedDateTimeCreator;
    }
    public double convertToUSD(double rubles) {
        return rubles / currencyPrice.getPrice();
    }
    @Transactional
    @EventListener(ApplicationReadyEvent.class) // Run with application
    public void runPriceUpdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        Date openTime = zonedDateTimeCreator.createOpeningTime();
        Date closeTime = zonedDateTimeCreator.createClosingTime();

        currencyPrice = getActualCurrencyPrice();   // Set actual price
        if(currencyPrice == null)
            currencyPrice = createNewPriceAndAddIntoDB();

        Date priceDate = currencyPrice.getDatetime();
        Date currentDate = new Date();

        if(!dateFormat.format(priceDate).equals(dateFormat.format(currentDate)) ||  // Check price by date
                (dateFormat.format(priceDate).equals(dateFormat.format(currentDate)) && currentDate.after(closeTime))) {
            currencyPrice = createNewPriceAndAddIntoDB();
        }

        Timer timer = new Timer();
        TimerTask taskOpen = new TimerTask() {
            @Override
            public void run() {
                currencyPrice = createNewPriceAndAddIntoDB();
            }
        };
        TimerTask taskClose = new TimerTask() {
            @Override
            public void run() {
                currencyPrice = createNewPriceAndAddIntoDB();
            }
        };
        timer.schedule(taskOpen, openTime, 86400000);
        timer.schedule(taskClose, closeTime, 86400000);
    }
    @Transactional
    private CurrencyPrice createNewPriceAndAddIntoDB() {
        CurrencyPrice newCurrencyPrice = new CurrencyPrice(new Date(), requestRealTimePrice());
        currencyPricesRepository.save(newCurrencyPrice);
        return newCurrencyPrice;
    }
    private CurrencyPrice getActualCurrencyPrice() { // Find price on db, if null - create new price with request to twelvedata
        return currencyPricesRepository.findActualPrice().orElse(null);
    }
    private double requestRealTimePrice() { // Request for actual price to twelvedata
        double price = 0;
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject response = restTemplate.getForObject(url, JSONObject.class);
            price = Double.parseDouble(response.get("price").toString());
        } catch (NullPointerException e) {
            System.out.println("Twelvedata did not return the price value");
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            System.out.println("No connection to Twelvedata");
            e.printStackTrace();
        }
        return price;
    }

    public static String getLimitCurrency() {
        return limitCurrency;
    }
}

