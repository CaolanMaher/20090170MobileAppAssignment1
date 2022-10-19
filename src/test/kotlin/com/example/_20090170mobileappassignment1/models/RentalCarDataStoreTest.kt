package com.example._20090170mobileappassignment1.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class RentalCarDataStoreTest {

    var rentalCarDataStore = RentalCarDataStore()

    @Test
    fun testCreate() {
        val aRentalCar = RentalCarModel(
            0, "BMW", "2000",
            "12-C-2000", 100.0, "Y",
            LocalDate.of(2022, 10, 10),
            LocalDate.of(2022, 10, 15),
            "Petrol"
        )

        rentalCarDataStore.create(aRentalCar)

        assertEquals("BMW", aRentalCar.brand)
    }

    @Test
    fun testFindAll() {
        val aRentalCar = RentalCarModel(
            0, "BMW", "2000",
            "12-C-2000", 100.0, "Y",
            LocalDate.of(2022, 10, 10),
            LocalDate.of(2022, 10, 15),
            "Petrol"
        )
        val aRentalCar2 = RentalCarModel(
            1, "Tesla", "2022",
            "12-C-2022", 125.5, "N",
            LocalDate.of(2022, 10, 18),
            LocalDate.of(2022, 10, 20),
            "Electric"
        )

        rentalCarDataStore.create(aRentalCar)
        rentalCarDataStore.create(aRentalCar2)

        val list = rentalCarDataStore.findAll()

        assertEquals("BMW", list[0].brand)
        assertEquals("Tesla", list[1].brand)
    }

    @Test
    fun testWrongInputs() {
        val aRentalCar = RentalCarModel()

        aRentalCar.brand = "BMW"

        rentalCarDataStore.create(aRentalCar)

        val list = rentalCarDataStore.findAll()

        assertEquals("BMW", list[0].brand) // checking input value
        assertEquals("", list[0].registration) // checking default value
    }
}