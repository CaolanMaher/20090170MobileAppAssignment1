package com.example._20090170mobileappassignment1.controllers

import com.example._20090170mobileappassignment1.models.RentalCarDataStore
import com.example._20090170mobileappassignment1.models.RentalCarModel
import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import tornadofx.Controller
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Date

class MainController: Controller() {

    val carDataStore = RentalCarDataStore()

    lateinit var con : Connection
    lateinit var st : Statement
    lateinit var rs : ResultSet

    fun connectToDatabase() {
        carDataStore.connectToDatabase()
    }

    fun add(brand : String, year : String, registration : String, rate : Double, isAvailable : Boolean,
            dateRented : Date, dateReturn : Date, fuelSource : String) {

        val available : String = if (isAvailable) {
            "Y"
        } else {
            "N"
        }

        val aRentalCar = RentalCarModel(
            0, brand, year, registration, rate, available, dateRented.toLocalDate(), dateReturn.toLocalDate(), fuelSource
        )

        carDataStore.add(aRentalCar)
    }

    fun list() : List<RentalCarModel> {
        return carDataStore.findAll()
    }

    fun update(id: Int, newBrand : String, newYear : String, newRegistration : String, newRate : Double, newIsAvailable : Boolean,
               newDateRented : Date, newDateReturn : Date, newFuelSource : String) {

        val available : String = if (newIsAvailable) {
            "Y"
        } else {
            "N"
        }

        val aRentalCar = RentalCarModel(
            id, newBrand, newYear, newRegistration, newRate, available, newDateRented.toLocalDate(), newDateReturn.toLocalDate(), newFuelSource
        )

        carDataStore.update(aRentalCar)
    }

    fun search(id : Int) : RentalCarModel? {
        return carDataStore.findOne(id)
    }

    fun delete(id : Int) {
        carDataStore.delete(id)
    }

    fun filter(filterByText : String, filterText : String) : List<RentalCarModel> {
        return carDataStore.filter(filterByText, filterText)
    }
}