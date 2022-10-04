package com.example._20090170mobileappassignment1.views

import com.example._20090170mobileappassignment1.controllers.MainController
import javafx.geometry.Orientation
import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import tornadofx.*;
import javafx.scene.layout.Priority
import java.lang.Double.parseDouble
//import jdk.internal.math.FloatingDecimal.parseDouble
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
                action { mainController.add(brand.text, year.text, registration.text, parseDouble(rate.text), isAvailable.isSelected, Date.valueOf(dateRented.value), Date.valueOf(dateReturn.value), fuelSource.text) }
            }
        }
        form {
            fieldset("Delete / Update A Car") {
                field("ID") {
                    textfield()
                }
                hbox {
                    button("Search") {
                        action { println("Wrote to database!") }
                    }
                    button("Delete") {
                        action { println("Wrote to database!") }
                    }
                }
                field("Brand") {
                    textfield()
                }
                field("Year") {
                    datepicker()
                }
                field("Registration") {
                    textfield()
                }
                field("Rate") {
                    textfield()
                }
                field("Is Available") {
                    checkbox()
                }
                field("Date Rented") {
                    datepicker()
                }
                field("Date Return") {
                    datepicker()
                }
                field("Fuel Source") {
                    textfield()
                }
            }
            button("Update") {
                action { println("Wrote to database!") }
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