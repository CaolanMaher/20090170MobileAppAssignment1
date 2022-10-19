package com.example._20090170mobileappassignment1.models

interface RentalCarStore {
    fun findAll(): List<RentalCarModel>
    fun findOne(id: Int): RentalCarModel?
    fun add(rentalCar: RentalCarModel)
    fun create(rentalCar: RentalCarModel)
    fun update(rentalCar: RentalCarModel)
    fun delete(id: Int)
    fun filter(filterText: String) : List<RentalCarModel>
}