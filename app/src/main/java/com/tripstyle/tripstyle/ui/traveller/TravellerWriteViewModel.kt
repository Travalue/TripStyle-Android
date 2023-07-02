package com.tripstyle.tripstyle.ui.traveller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.model.TravellerWriteResult

class TravellerWriteViewModel: ViewModel() {
    val bodyItem = ArrayList<TravellerWriteResult>()
    val bodyItemListData = MutableLiveData<ArrayList<TravellerWriteResult>>()

    init {
        addBodyItem()
    }

    fun addBodyItem(){
        bodyItem.add(TravellerWriteResult(ArrayList(),""))
        bodyItemListData.value = bodyItem
    }

    fun updateBodyItem(pos: Int, item: TravellerWriteResult){
        val updatedItem = TravellerWriteResult(ArrayList(item.images), item.text)
        bodyItem[pos] = updatedItem
        bodyItemListData.value = bodyItem
    }

    fun updateImageItem(pos: Int, item: ArrayList<String>){
        val updatedItem = TravellerWriteResult(ArrayList(item),bodyItem[pos].text)
        bodyItem[pos] = updatedItem
        bodyItemListData.value = bodyItem
    }

    fun updateTextItem(item: MutableList<String>){
        for(i in 0 until bodyItem.size){
            val updatedItem = TravellerWriteResult(ArrayList(bodyItem[i].images),item[i])
            bodyItem[i] = updatedItem
            bodyItemListData.value = bodyItem
        }

        printBodyItem()
    }

    fun deleteBodyItem(){
        bodyItem.clear()
        bodyItemListData.value = bodyItem
    }

    // 테스트용
    fun printBodyItem(){
        var count = 0
        bodyItem.forEach {
            println("current index: ${count++}")
            println("current image count: ${it.images.size}")
            println("current text: ${it.text}")
        }
    }
}