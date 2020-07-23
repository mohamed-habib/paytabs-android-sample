package com.example.paytabssample.dto

class Address() {
    var address: String = ""
    var city: String = ""
    var state: String = ""
    var country: String = ""
    var postalCode: String = ""

    constructor(
        address: String,
        city: String,
        state: String,
        country: String,
        postalCode: String
    ) : this() {
        this.address = address
        this.city = city
        this.state = state
        this.country = country
        this.postalCode = postalCode
    }
}