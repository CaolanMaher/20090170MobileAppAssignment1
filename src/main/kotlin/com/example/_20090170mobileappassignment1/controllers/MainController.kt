package com.example._20090170mobileappassignment1.controllers

import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import tornadofx.Controller
import java.lang.Double.parseDouble
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Date
//import java.time.LocalDate

class MainController: Controller() {

    lateinit var con : Connection
    lateinit var st : Statement
    lateinit var rs : ResultSet

    fun connectToDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car-rental", "root", "")
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
            rs = st.executeQuery("SELECT * FROM `cars`")

            /*
            if(rs.next()) {
                println(rs.getString("id"))
                println(rs.getString("brand"))
                println(rs.getString("year"))
                println(rs.getString("registration"))
                println(rs.getString("rate"))
                println(rs.getString("isAvailable"))
                println(rs.getString("dateRented"))
                println(rs.getString("dateReturn"))
                println(rs.getString("fuelSource"))
            }

             */
        }
        catch (e: Exception) {

        }
    }

    fun add(brand : String, year : String, registration : String, rate : Double, isAvailable : Boolean,
            dateRented : Date, dateReturn : Date, fuelSource : String) {

        val available : String = if (isAvailable) {
            "Y"
        } else {
            "N"
        }

        rs.moveToInsertRow()

        rs.updateString("brand", brand)
        rs.updateString("year", year)
        rs.updateString("registration", registration)
        rs.updateDouble("rate", rate)
        rs.updateString("isAvailable", available)
        rs.updateDate("dateRented", dateRented)
        rs.updateDate("dateReturn", dateReturn)
        rs.updateString("fuelSource", fuelSource)

        rs.insertRow()
        rs.moveToInsertRow()
    }

    fun list(textArea : TextArea) {
        rs = st.executeQuery("SELECT * FROM `cars`")

        textArea.clear()

        while(rs.next()) {
            textArea.appendText("ID: " + rs.getString("id"))
            textArea.appendText("\nBrand: " + rs.getString("brand"))
            textArea.appendText("\nYear: " + rs.getString("year"))
            textArea.appendText("\nRegistration: " + rs.getString("registration"))
            textArea.appendText("\nRate: " + rs.getDouble("rate"))
            textArea.appendText("\nIs Available: " + rs.getString("isAvailable"))
            textArea.appendText("\nDate Rented: " + rs.getDate("dateRented"))
            textArea.appendText("\nDate Return: " + rs.getDate("dateReturn"))
            textArea.appendText("\nFuel Source: " + rs.getString("fuelSource"))
            textArea.appendText("\n------------------------------------")
            textArea.appendText("\n")
        }
    }

    fun update(newBrand : String, newYear : String, newRegistration : String, newRate : Double, newIsAvailable : Boolean,
               newDateRented : Date, newDateReturn : Date, newFuelSource : String) {

        val available : String = if (newIsAvailable) {
            "Y"
        } else {
            "N"
        }

        rs.updateString("brand", newBrand)
        rs.updateString("year", newYear)
        rs.updateString("registration", newRegistration)
        rs.updateDouble("rate", newRate)
        rs.updateString("isAvailable", available)
        rs.updateDate("dateRented", newDateRented)
        rs.updateDate("dateReturn", newDateReturn)
        rs.updateString("fuelSource", newFuelSource)
        rs.updateRow()

    }

    fun search(id : Int, brandUpdate : TextField, yearUpdate : TextField, registrationUpdate : TextField,
               rateUpdate : TextField, isAvailableUpdate : CheckBox, dateRentedUpdate : DatePicker, dateReturnUpdate : DatePicker, fuelSourceUpdate : TextField) {
        rs = st.executeQuery("SELECT * FROM `cars` WHERE ID = " + id)

        /*
        if(rs.next()) {
            println(rs.getString("id"))
            println(rs.getString("brand"))
            println(rs.getString("year"))
            println(rs.getString("registration"))
            println(rs.getString("rate"))
            println(rs.getString("isAvailable"))
            println(rs.getString("dateRented"))
            println(rs.getString("dateReturn"))
            println(rs.getString("fuelSource"))
        }
         */

        if(rs.next()) {
            brandUpdate.text = rs.getString("brand")
            yearUpdate.text = rs.getString("year")
            registrationUpdate.text = rs.getString("registration")
            rateUpdate.text = rs.getString("rate")
            isAvailableUpdate.isSelected = rs.getString("isAvailable").equals("Y")
            dateRentedUpdate.value = rs.getDate("dateRented").toLocalDate()
            dateReturnUpdate.value = rs.getDate("dateReturn").toLocalDate()
            fuelSourceUpdate.text = rs.getString("fuelSource")
        }

        //return rs
    }

    fun delete(id : Int) {
        rs = st.executeQuery("SELECT * FROM `cars` WHERE ID = " + id)

        if(rs.next()) {
            rs.deleteRow()
        }
    }
}