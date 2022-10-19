package com.example._20090170mobileappassignment1.views

import com.example._20090170mobileappassignment1.controllers.MainController
import com.example._20090170mobileappassignment1.models.RentalCarDataStore
import com.example._20090170mobileappassignment1.models.RentalCarModel
import javafx.geometry.Orientation
import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import tornadofx.*;
import javafx.scene.layout.Priority
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.sql.Date

class MyView: View() {

    private val mainController : MainController by inject()

    var brand: TextField by singleAssign()
    var year: TextField by singleAssign()
    var registration: TextField by singleAssign()
    var rate: TextField by singleAssign()
    var isAvailable: CheckBox by singleAssign()
    var dateRented: DatePicker by singleAssign()
    var dateReturn: DatePicker by singleAssign()
    var fuelSource: TextField by singleAssign()

    var idToSearch : TextField by singleAssign()

    var filter: TextField by singleAssign()

    var tableView : TableView<RentalCarModel> by singleAssign()

    var filterList = mutableListOf<RentalCarModel>().asObservable()

    var carList = RentalCarDataStore()

    fun connectToDatabase() {
        mainController.connectToDatabase()
    }

    fun add(brand : String, year : String, registration : String,
            rate : Double, isAvailable : Boolean, dateRented : Date, dateReturn : Date,
            fuelSource : String) {

        mainController.add(brand, year, registration, rate, isAvailable, dateRented, dateReturn, fuelSource)
    }

    fun list() : List<RentalCarModel>{
        return mainController.list()
    }

    fun update(id: Int, brand : String, year : String, registration : String,
            rate : Double, isAvailable : Boolean, dateRented : Date, dateReturn : Date,
            fuelSource : String) {

        mainController.update(id, brand, year, registration, rate, isAvailable, dateRented, dateReturn, fuelSource)
    }

    fun search(id : Int) : RentalCarModel? {
        return mainController.search(id)
    }

    fun delete(id : Int) {
        mainController.delete(id)
    }

    fun filter(filterText : String) : List<RentalCarModel> {
        return mainController.filter(filterText)
    }

    override val root = hbox(20) {

        connectToDatabase()

        form {
            fieldset("Find A Car to Delete / Update") {
                field("ID") {
                    idToSearch = textfield()
                }
                hbox {
                    button("Search") {
                        //TODO Add Validation
                        action {
                            var aRentalCar = search(parseInt(idToSearch.text))

                            if (aRentalCar != null) {
                                brand.text = aRentalCar.brand
                                year.text = aRentalCar.year
                                registration.text = aRentalCar.registration
                                rate.text = aRentalCar.rate.toString()
                                isAvailable.isSelected = aRentalCar.isAvailable == "Y"
                                dateRented.value = aRentalCar.dateRented
                                dateReturn.value = aRentalCar.dateReturn
                                fuelSource.text = aRentalCar.fuelSource
                            }
                        }
                    }
                    button("Delete") {
                        //TODO Add Validation
                        action { delete(parseInt(idToSearch.text)) }
                    }
                }
                fieldset("Add/Update A Car") {
                    field("Brand") {
                        brand = textfield()
                    }
                    field("Year") {
                        year = textfield()
                    }
                    field("Registration") {
                        registration = textfield()
                    }
                    field("Rate") {
                        rate = textfield()
                    }
                    field("Is Available") {
                        isAvailable = checkbox()
                    }
                    field("Date Rented") {
                        dateRented = datepicker()
                    }
                    field("Date Return") {
                        dateReturn = datepicker()
                    }
                    field("Fuel Source") {
                        fuelSource = textfield()
                    }
                    hbox {
                        button("Add") {
                            //TODO Add Validation
                            action {
                                add(
                                    brand.text,
                                    year.text,
                                    registration.text,
                                    parseDouble(rate.text),
                                    isAvailable.isSelected,
                                    Date.valueOf(dateRented.value),
                                    Date.valueOf(dateReturn.value),
                                    fuelSource.text
                                )
                            }
                        }
                        button("Update") {
                            action {
                                //TODO Add Validation
                                update(
                                    parseInt(idToSearch.text),
                                    brand.text,
                                    year.text,
                                    registration.text,
                                    parseDouble(rate.text),
                                    isAvailable.isSelected,
                                    Date.valueOf(dateRented.value),
                                    Date.valueOf(dateReturn.value),
                                    fuelSource.text
                                )
                            }
                        }
                    }
                }
            }
        }
        form {
            fieldset("Data", labelPosition = Orientation.VERTICAL) {
                field("See Rental Cars Here", Orientation.VERTICAL) {
                    button("Show All Cars") {
                        action {
                            carList.rentalCars = list().asObservable()
                            tableView.refresh()
                            tableView.items = carList.rentalCars
                        }
                    }
                    hbox {
                        field("Filter By Brand") {
                            filter = textfield()
                            button("Filter") {
                                action {
                                    filterList = filter(filter.text).asObservable()
                                    tableView.items = filterList
                                }
                            }
                        }
                    }
                    tableView = tableview(carList.rentalCars) {
                        readonlyColumn("ID", RentalCarModel::id)
                        readonlyColumn("Brand", RentalCarModel::brand)
                        readonlyColumn("Year", RentalCarModel::year)
                        readonlyColumn("Registration", RentalCarModel::registration)
                        readonlyColumn("Rate", RentalCarModel::rate)
                        readonlyColumn("isAvailable", RentalCarModel::isAvailable)
                        readonlyColumn("Date Rented", RentalCarModel::dateRented)
                        readonlyColumn("Date Return", RentalCarModel::dateReturn)
                        readonlyColumn("Fuel Source", RentalCarModel::fuelSource)
                    }
                }
            }
        }
    }
}