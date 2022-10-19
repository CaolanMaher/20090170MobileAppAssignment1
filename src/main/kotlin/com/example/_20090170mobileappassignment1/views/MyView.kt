package com.example._20090170mobileappassignment1.views

import com.example._20090170mobileappassignment1.controllers.MainController
import com.example._20090170mobileappassignment1.models.RentalCarDataStore
import com.example._20090170mobileappassignment1.models.RentalCarModel
import javafx.geometry.Orientation
import javafx.scene.control.Alert
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import tornadofx.*
import java.lang.Double.parseDouble
import java.sql.Date

class MyView: View() {

    private val mainController : MainController by inject()

    private var brand: TextField by singleAssign()
    private var year: TextField by singleAssign()
    private var registration: TextField by singleAssign()
    private var rate: TextField by singleAssign()
    private var isAvailable: CheckBox by singleAssign()
    private var dateRented: DatePicker by singleAssign()
    private var dateReturn: DatePicker by singleAssign()
    private var fuelSource: TextField by singleAssign()

    private var filter: TextField by singleAssign()

    private var tableView : TableView<RentalCarModel> by singleAssign()

    private var filterBox : ComboBox<String> by singleAssign()

    private var filterList = mutableListOf<RentalCarModel>().asObservable()

    private var carList = RentalCarDataStore()

    private val filterOptions = observableListOf("Brand", "Fuel Source")

    private fun connectToDatabase() {
        mainController.connectToDatabase()
    }

    private fun add(brand : String, year : String, registration : String,
                    rate : Double, isAvailable : Boolean, dateRented : Date, dateReturn : Date,
                    fuelSource : String) {

        mainController.add(brand, year, registration, rate, isAvailable, dateRented, dateReturn, fuelSource)
    }

    private fun list() : List<RentalCarModel>{
        return mainController.list()
    }

    private fun update(id: Int, brand : String, year : String, registration : String,
                       rate : Double, isAvailable : Boolean, dateRented : Date, dateReturn : Date,
                       fuelSource : String) {

        mainController.update(id, brand, year, registration, rate, isAvailable, dateRented, dateReturn, fuelSource)
    }

    private fun search(id : Int) : RentalCarModel? {
        return mainController.search(id)
    }

    private fun delete(id : Int) {
        mainController.delete(id)
    }

    private fun filter(filterByText : String, filterText : String) : List<RentalCarModel> {
        return mainController.filter(filterByText, filterText)
    }

    override val root = hbox(20) {

        connectToDatabase()

        form {
            fieldset("Find A Car to Delete / Update") {
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
                            action {
                                try {
                                    parseDouble(rate.text)

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
                                catch(e : Exception) {
                                    Alert(Alert.AlertType.ERROR, "Invalid Input").show()
                                }
                            }
                        }
                        button("Update") {
                            action {
                                try {
                                    parseDouble(rate.text)

                                    update(
                                        tableView.selectedValue.toString().toInt(),
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
                                catch(e : Exception) {
                                    Alert(Alert.AlertType.ERROR, "Invalid Input").show()
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
                        field("Filter By Brand or Fuel Source") {
                            filterBox = combobox(values = filterOptions)
                            filter = textfield()
                            button("Filter") {
                                action {
                                    try {
                                        filterList = filter(filterBox.value, filter.text).asObservable()
                                        tableView.items = filterList
                                    }
                                    catch(e : Exception) {
                                        Alert(Alert.AlertType.ERROR, "Invalid Input, Please Check the drop down and text field").show()
                                    }
                                }
                            }
                        }
                    }
                    hbox {
                        button("Search") {
                            action {
                                try {
                                    val aRentalCar = search(tableView.selectedValue.toString().toInt())

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
                                catch(e : Exception) {
                                    Alert(Alert.AlertType.ERROR, "Please Select an ID from the table before continuing").show()
                                }
                            }
                        }
                        button("Delete") {
                            action {
                                try {
                                    delete(tableView.selectedValue.toString().toInt())
                                }
                                catch(e : Exception) {
                                    Alert(Alert.AlertType.ERROR, "Please Select an ID from the table before continuing").show()
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