package com.ensode.javaee8book.qualifiers.beans;

import java.util.logging.Logger;
import com.ensode.javaee8book.qualifiers.Premium;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CustomerController {

    private static final Logger logger = Logger.getLogger(
            CustomerController.class.getName());
    @Inject
    @Premium
    private Customer customer;

    public String saveCustomer() {

        PremiumCustomer premiumCustomer = (PremiumCustomer) customer;

        logger.info("Saving the following information \n"
                + premiumCustomer.getFirstName() + " "
                + premiumCustomer.getLastName()
                + ", discount code = "
                + premiumCustomer.getDiscountCode());

        return "confirmation";
    }
}
