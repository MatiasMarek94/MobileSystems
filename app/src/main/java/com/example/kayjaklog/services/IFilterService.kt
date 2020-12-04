package com.example.kayjaklog.services

interface IFilterService {
    fun applyMeanFilter(): Array<DoubleArray?>?
}