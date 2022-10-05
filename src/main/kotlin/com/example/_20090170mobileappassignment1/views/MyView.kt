package com.example._20090170mobileappassignment1.views

import com.example._20090170mobileappassignment1.controllers.MainController
import javafx.geometry.Orientation
import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import tornadofx.*;
import javafx.scene.layout.Priority
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.sql.Date
import java.sql.ResultSet

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

    var brandUpdate: TextField by singleAssign()
    var yearUpdate: TextField by singleAssign()
    var registrationUpdate: TextField by singleAssign()
    var rateUpdate: TextField by singleAssign()
    var isAvailableUpdate: CheckBox by singleAssign()
    var dateRentedUpdate: DatePicker by singleAssign()
    var dateReturnUpdate: DatePicker by singleAssign()
    var fuelSourceUpdate: TextField by singleAssign()

    //var rs : ResultSet? = null

    override val root = hbox(20) {

        mainController.connectToDatabase()

        form {
            fieldset("Add A Car") {
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
            }
            button("Add") {
                //TODO Add Validation
                action { mainController.add(brand.text, year.text, registration.text, parseDouble(rate.text), isAvailable.isSelected, Date.valueOf(dateRented.value), Date.valueOf(dateReturn.value), fuelSource.text) }
            }
        }
        form {
            fieldset("Delete / Update A Car") {
                field("ID") {
                    idToSearch = textfield()
                }
                hbox {
                    button("Search") {
                        //TODO Add Validation
                        action { mainController.search(parseInt(idToSearch.text), brandUpdate, yearUpdate, registrationUpdate, rateUpdate, isAvailableUpdate, dateRentedUpdate, dateReturnUpdate, fuelSourceUpdate) }
                    }
                    button("Delete") {
                        //TODO Add Validation
                        action { mainController.delete(parseInt(idToSearch.text)) }
                    }
                }
                field("Brand") {
                    brandUpdate = textfield()
                }
                field("Year") {
                    yearUpdate = textfield()
                }
                field("Registration") {
                    registrationUpdate = textfield()
                }
                field("Rate") {
                    rateUpdate = textfield()
                }
                field("Is Available") {
                    isAvailableUpdate = checkbox()
                }
                field("Date Rented") {
                    dateRentedUpdate = datepicker()
                }
                field("Date Return") {
                    dateReturnUpdate = datepicker()
                }
                field("Fuel Source") {
                    fuelSourceUpdate = textfield()
                }
            }
            button("Update") {
                action {
                    //TODO Add Validation
                    mainController.update(brandUpdate.text, yearUpdate.text, registrationUpdate.text, parseDouble(rateUpdate.text), isAvailableUpdate.isSelected, Date.valueOf(dateRentedUpdate.value), Date.valueOf(dateReturnUpdate.value), fuelSourceUpdate.text)
                }
            }
        }
        form {
            fieldset("Data", labelPosition = Orientation.VERTICAL) {
                field("See Rental Cars Here", Orientation.VERTICAL) {
                    hbox {
                        field("Filter By Brand") {
                            textfield()
                        }
                        button("Filter") {
                            action { println("Wrote to database!") }
                        }
                        button("Show All Cars") {
                            action { println("Wrote to database!") }
                        }
                    }
                    textarea {
                        prefRowCount = 5
                        vgrow = Priority.ALWAYS
                        isEditable = false;
                    }
                }
            }
        }
    }

    /*
    private fun test(id : Long, brand : String, year : String, registration : String, rate : Double, isAvailable : Boolean, dateRented : Date, dateReturn : Date, fuelSource : String) {
        println("$id, $brand, $year, $registration, $rate, $isAvailable, $dateRented, $dateReturn, $fuelSource")
    }

     */
}