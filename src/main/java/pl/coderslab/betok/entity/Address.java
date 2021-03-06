package pl.coderslab.betok.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class to be used as a 'localisation' parameter, that can give us a possibility of additional options for user (for
 * ex. teams from same city (or near localisation - based on a postcode) will be auto added as favorities, current
 * matches in users city will be listed as first, national games will have additional bonus etc. At the moment class
 * have no use.
 */
@Entity
@Table(name = "addresses")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @NotBlank(message = "Please fill street name box.")
    private String streetName;

    @NotNull
    @Min(1)
    private int streetNumber;

    @NotBlank(message = "Please fill city name box.")
    private String city;

    @NotBlank(message = "Please fill country name box.")
    private String country;

    //for later - add a regex/or a verification to clear all chars but numbers
    @NotNull
    private String zipCode;

    public Address() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}