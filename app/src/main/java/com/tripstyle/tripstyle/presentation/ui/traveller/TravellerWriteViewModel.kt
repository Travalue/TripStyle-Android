package com.tripstyle.tripstyle.presentation.ui.traveller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.data.model.dto.Schedule
import com.tripstyle.tripstyle.data.model.dto.TravellerWriteResult

class TravellerWriteViewModel: ViewModel() {
    val bodyItem = ArrayList<TravellerWriteResult>()
    val bodyItemListData = MutableLiveData<ArrayList<TravellerWriteResult>>()
    private val editTextContents = mutableMapOf<Int, String>()

    val scheduleItem = ArrayList<Schedule>()
    val scheduleItemListData = MutableLiveData<ArrayList<Schedule>>()

    private var categorySubject = ""
    var categorySubjectLiveData = MutableLiveData<String>()

    init {
        addBodyItem()
    }

    fun addBodyItem() {
        // EditText 받아오기
        for ((id, content) in editTextContents) {
            if (id < bodyItem.size) {
                bodyItem[id].text = content
            } else {
                // 에러
            }
        }

        // 빈 아이템 추가
        bodyItem.add(TravellerWriteResult(ArrayList(), ""))
        bodyItemListData.value = bodyItem
    }

    fun updateBodyItem(pos: Int, item: TravellerWriteResult) {
        // EditText 받아오기
        for ((id, content) in editTextContents) {
            if (id < bodyItem.size) {
                bodyItem[id].text = content
            } else {
                // 에러
            }
        }

        // 이미지 update
        val updatedItem = TravellerWriteResult(ArrayList(item.images), bodyItem[pos].text)
        bodyItem[pos] = updatedItem
        bodyItemListData.value = bodyItem

//        printBodyItem()
    }

    fun updateBodyTextItem(id: Int, item: String) {
        editTextContents[id] = item
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



    // 카테고리 주제 저장하는
    fun updateCategorySubject(text: String){
        categorySubject = text
        categorySubjectLiveData.value = categorySubject
    }


    // 지도 및 일정 관련
    fun addScheduleItem(item: Schedule){
        scheduleItem.add(item)
        scheduleItemListData.value = scheduleItem
    }

    fun deleteScheduleItem(){
        scheduleItem.clear()
        scheduleItemListData.value = scheduleItem
    }
}