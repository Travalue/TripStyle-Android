package com.tripstyle.tripstyle.presentation.ui.traveller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val cities: String, val specificCities: String)

class TravellerSearchViewModel: ViewModel() {
    val domesticCities = ArrayList<Item>()
    val domesticCitiesListData = MutableLiveData<ArrayList<Item>>()

    val overseasCities = ArrayList<Item>()
    val overseasCitiesListData = MutableLiveData<ArrayList<Item>>()

    fun addDomesticCities(item: Item){
        domesticCities.add(item)
        domesticCitiesListData.value = domesticCities
    }

    fun deleteDomesticCities(){
        domesticCities.clear()
        domesticCitiesListData.value = domesticCities
    }

    fun addOverseasCities(item: Item){
        overseasCities.add(item)
        overseasCitiesListData.value = overseasCities
    }

    fun deleteOverseasCities(){
        overseasCities.clear()
        overseasCitiesListData.value = overseasCities
    }



}