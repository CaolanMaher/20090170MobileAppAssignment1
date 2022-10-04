package com.example._20090170mobileappassignment1.controllers

import tornadofx.Controller
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

    fun list() {

    }

    fun update() {

    }

    fun search() {

    }

    fun delete() {

    }
}