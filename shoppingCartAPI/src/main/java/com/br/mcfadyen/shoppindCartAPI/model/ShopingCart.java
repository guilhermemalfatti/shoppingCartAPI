package com.br.mcfadyen.shoppindCartAPI.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopingCart{
    private static final Logger logger = LogManager.getLogger(ShopingCart.class);
    private List<CommerceItem> items;
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
    public List<CommerceItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<CommerceItem> items) {
        this.items = items;
    }

    /**
     * @param item the item to add
     */
    public void addItem(CommerceItem item){
        if(this.items != null){
            this.items.add(item);
        }else{
            this.items = new ArrayList<>();
            this.items.add(item);
        }
        
        calcAmount();
    }
    
    /**
     * @param item the item to remove
     */
    public Boolean rmItem(String commerceId){
        Boolean removed = this.items.removeIf(i -> i.getId().equals(commerceId));			
        
        calcAmount();

        return removed;
    }

    /**
     * Method to calculate the total amount of the shopping cart
     */
    private void calcAmount(){
        BigDecimal amount = null;
        if(this.items != null  && this.items.size() > 0){
            amount = this.items.stream().map(CommerceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        if(amount != null){
            setAmount(amount);
        }else{
            logger.error("Amount in the shopping acrt not calculated.");
        }
        
    }
}