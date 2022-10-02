package com.example._20090170mobileappassignment1.models

data class RentalCarModel(
    var id: Long = 0,
    var brand: String = "",
    var year: String = "",
    var registration: String = "",
    var rate: Double = 0.0,
    var isAvailable: Boolean = false,
    var dateRented: String = "",
    var dateToReturn: String = "",
    var fuelSource: String = ""
)