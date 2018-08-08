package com.br.mcfadyen.shoppindCartAPI.model;

import java.math.BigDecimal;
import java.util.List;

public class ShopingCart{
    private List items;
    private BigDecimal amount;

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the items
     */
    public List getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List items) {
        this.items = items;
    }
}