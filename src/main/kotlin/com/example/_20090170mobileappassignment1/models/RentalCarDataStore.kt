package com.example._20090170mobileappassignment1.models

import tornadofx.asObservable
import java.sql.*

class RentalCarDataStore : RentalCarStore{

    lateinit var con : Connection
    lateinit var st : Statement
    lateinit var rs : ResultSet

    var rentalCars = mutableListOf<RentalCarModel>().asObservable()

    fun connectToDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car-rental", "root", "")
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
            rs = st.executeQuery("SELECT * FROM `cars`")

            // fill array with existing data
            while (rs.next()) {

                val aRentalCar = RentalCarModel(rs.getString("id").toInt(),
                    rs.getString("brand"), rs.getString("year"),
                    rs.getString("registration"), rs.getString("rate").toDouble(),
                    rs.getString("isAvailable"), rs.getDate("dateRented").toLocalDate(),
                    rs.getDate("dateReturn").toLocalDate(), rs.getString("fuelSource"))

                create(aRentalCar)
            }
        }
        catch (e: Exception) {
            print(e)
        }
    }

    override fun findAll(): List<RentalCarModel> {
        return rentalCars
    }

    override fun findOne(id: Int): RentalCarModel? {
        rs = st.executeQuery("SELECT * FROM `cars` WHERE ID = " + id)

        var aRentalCar: RentalCarModel? = null

        if(rs.next()) {
             aRentalCar = RentalCarModel(
                rs.getInt("id"), rs.getString("brand"),
                rs.getString("year"), rs.getString("registration"),
                rs.getDouble("rate"), rs.getString("isAvailable"),
                rs.getDate("dateRented").toLocalDate(),
                rs.getDate("dateReturn").toLocalDate(), rs.getString("fuelSource")
            )
        }

        return aRentalCar
    }

    override fun add(rentalCar: RentalCarModel) {
        rs.moveToInsertRow()

        rs.updateString("brand", rentalCar.brand)
        rs.updateString("year", rentalCar.year)
        rs.updateString("registration", rentalCar.registration)
        rs.updateDouble("rate", rentalCar.rate)
        rs.updateString("isAvailable", rentalCar.isAvailable)
        rs.updateDate("dateRented", Date.valueOf(rentalCar.dateRented))
        rs.updateDate("dateReturn", Date.valueOf(rentalCar.dateReturn))
        rs.updateString("fuelSource", rentalCar.fuelSource)

        rs.insertRow()
        rs.moveToInsertRow()

        // get newly added to get id for local copy
        rs = st.executeQuery("SELECT * FROM `cars` ORDER BY id DESC")

        if(rs.next()) {
            rentalCar.id = rs.getString("id").toInt()
        }

        create(rentalCar)
    }

    override fun create(rentalCar: RentalCarModel) {
        rentalCars.add(rentalCar)
    }

    override fun update(rentalCar: RentalCarModel) {
        rs.updateString("brand", rentalCar.brand)
        rs.updateString("year", rentalCar.year)
        rs.updateString("registration", rentalCar.registration)
        rs.updateDouble("rate", rentalCar.rate)
        rs.updateString("isAvailable", rentalCar.isAvailable)
        rs.updateDate("dateRented", Date.valueOf(rentalCar.dateRented))
        rs.updateDate("dateReturn", Date.valueOf(rentalCar.dateReturn))
        rs.updateString("fuelSource", rentalCar.fuelSource)
        rs.updateRow()

        for (i in 0 until rentalCars.size) {
            if(rentalCars[i].id == rentalCar.id) {
                print("Found One" + rentalCars[i].brand + " " + rentalCar.brand)
                rentalCars[i].brand = rentalCar.brand
                rentalCars[i].year = rentalCar.year
                rentalCars[i].registration = rentalCar.registration
                rentalCars[i].rate = rentalCar.rate
                rentalCars[i].isAvailable = rentalCar.isAvailable
                rentalCars[i].dateRented = rentalCar.dateRented
                rentalCars[i].dateReturn = rentalCar.dateReturn
                rentalCars[i].fuelSource = rentalCar.fuelSource
                break
            }
        }
    }

    override fun delete(id: Int) {
        rs = st.executeQuery("SELECT * FROM `cars` WHERE ID = " + id)

        if(rs.next()) {
            rs.deleteRow()
        }

        for (i in 0 until rentalCars.size) {
            if(rentalCars[i].id == id) {
                rentalCars.removeAt(i)
                break
            }
        }
    }

    override fun filter(filterByText : String, filterText: String) : List<RentalCarModel>{

        if (filterByText == "Brand") {
            rs = st.executeQuery("SELECT * FROM `cars` WHERE brand = " + "'" + filterText + "'")
        }
        else if (filterByText == "Fuel Source") {
            rs = st.executeQuery("SELECT * FROM `cars` WHERE fuelSource = " + "'" + filterText + "'")
        }

        var carList = mutableListOf<RentalCarModel>()

        // add check if exists, if so, return object
        while(rs.next()) {
            var aRentalCar = RentalCarModel(
                rs.getInt("id"), rs.getString("brand"),
                rs.getString("year"), rs.getString("registration"),
                rs.getDouble("rate"), rs.getString("isAvailable"),
                rs.getDate("dateRented").toLocalDate(),
                rs.getDate("dateReturn").toLocalDate(), rs.getString("fuelSource")
            )
            carList.add(aRentalCar)
        }

        return carList
    }
}