package com.example._20090170mobileappassignment1.models

interface RentalCarStore {
    fun findAll(): List<RentalCarModel>
    fun findOne(id: Long): RentalCarModel?
    fun create(rentalCar: RentalCarModel)
    fun update(rentalCar: RentalCarModel)
    fun delete(rentalCar: RentalCarModel)
}