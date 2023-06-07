package com.ensode.jakartaee8book.jakartarestintro.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("customer")
public class CustomerResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getCustomer() {
    //in a "real" RESTful service, we would retrieve data from a database
    //then return a JSON representation of the data.

    System.out.println("--- " + this.getClass().getCanonicalName()
            + ".getCustomer() invoked");

    //Using a text block for readability
    //requires Java 15 or newer
    return """
           {
             "customer": {
               "id": 123,
               "firstName": "Joseph",
               "middleName": "William",
               "lastName": "Graystone"
             }
           }
           """;
  }

  /**
   * Create a new customer
   *
   * @param customerJson representation of the customer to create
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void createCustomer(String customerJson) {
    //in a "real" RESTful service, we would parse the JSON
    //received in the customer JSON parameter, then insert
    //a new row into the database.

    System.out.println("--- " + this.getClass().getCanonicalName()
            + ".createCustomer() invoked");

    System.out.println("customerJson = " + customerJson);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateCustomer(String customerJson) {
    //in a "real" RESTful service, we would parse the JSON
    //received in the customer JSON parameter, then update
    //a row in the database.

    System.out.println("--- " + this.getClass().getCanonicalName()
            + ".updateCustomer() invoked");

    System.out.println("customerJson = " + customerJson);
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  public void deleteCustomer(String customerJson) {
    //in a "real" RESTful service, we would parse the JSON
    //received in the customer JSON parameter, then delete
    //a row in the database.

    System.out.println("--- " + this.getClass().getCanonicalName()
            + ".deleteCustomer() invoked");

    System.out.println("customerJson = " + customerJson);
  }
}
