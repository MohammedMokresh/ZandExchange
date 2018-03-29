package com.sarafizand.sarafizand.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohammed on 11/2/2018.
 */

public class Booking {
    private Integer id;
    private Integer currency_id;
    private Curencies currency = null;
    private Integer expiration;
    private Integer status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Integer currency_id) {
        this.currency_id = currency_id;
    }

    public Curencies getCurrency() {
        return currency;
    }

    public void setCurrency(Curencies currency) {
        this.currency = currency;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
