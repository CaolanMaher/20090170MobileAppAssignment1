package com.example._20090170mobileappassignment1.controllers

import tornadofx.Controller
import java.sql.DriverManager
import java.sql.ResultSet

class MainController: Controller() {

    fun connectToDatabase() {
        try {
            val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car-rental", "root", "")
            val st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
            val rs = st.executeQuery("SELECT * FROM `cars`")

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

    fun add() {

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