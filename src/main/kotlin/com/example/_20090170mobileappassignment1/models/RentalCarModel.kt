package com.example._20090170mobileappassignment1.models

import java.time.LocalDate

data class RentalCarModel(
    var id: Int = 0,
    var brand: String = "",
    var year: String = "",
    var registration: String = "",
    var rate: Double = 0.0,
    //var isAvailable: Boolean = false,
    var isAvailable: String = "N",
    var dateRented: LocalDate = LocalDate.parse("1111-11-11"),
    var dateReturn: LocalDate = LocalDate.parse("1111-11-11"),
    var fuelSource: String = ""
)