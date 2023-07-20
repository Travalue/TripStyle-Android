package com.tripstyle.tripstyle.presentation.ui.traveller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripstyle.tripstyle.data.model.dto.Schedule
import com.tripstyle.tripstyle.data.model.dto.TravellerWriteResult

class TravellerWriteViewModel: ViewModel() {
    val bodyItem = ArrayList<TravellerWriteResult>()
    val bodyItemListData = MutableLiveData<ArrayList<TravellerWriteResult>>()
    private val editTextContents = mutableMapOf<Int, String>()

    private var title = ""
    private var subtitle = ""

    val scheduleItem = ArrayList<Schedule>()
    val scheduleItemListData = MutableLiveData<ArrayList<Schedule>>()

    private var mainBackgroundImage = ""
    var mainBackgroundImageLiveData = MutableLiveData<String>()

    private var categorySubject = ""
    var categorySubjectLiveData = MutableLiveData<String>()

    private var categoryCoverImage = ""
    var categoryCoverImageLiveData = MutableLiveData<String>()


    // 어떤 카테고리가 체크됐는지 확인하는 용도
    val categoryCheckBox = MutableLiveData<Int>()

    val isBodyContentsExist = MutableLiveData<Boolean>()


    /* 제목, 부제목 관련 */
    fun updateTitleAndSubtitle(currentTitle: String, currentSubtitle: String){
        title = currentTitle
        subtitle = currentSubtitle
    }



    /* 본문(사진-글, 사진-글 ...) 관련 */

    init {
        addBodyItem() // 최초에 본문 1개 생성
        isBodyContentsExist.value = false
    }

    fun addBodyItem() {
        // EditText 받아오기
        updateAllBodyText()

        // 빈 아이템 추가
        bodyItem.add(TravellerWriteResult("", ""))
        bodyItemListData.value = bodyItem
    }

    fun updateBodyItem(pos: Int, item: TravellerWriteResult) {
        // EditText 받아오기
        updateAllBodyText()

        // 이미지 update
        val updatedItem = TravellerWriteResult(item.image, bodyItem[pos].text)
        bodyItem[pos] = updatedItem
        bodyItemListData.value = bodyItem

        updateBodyContentsStatus()
//        printBodyItem()
    }

    fun updateBodyTextItem(id: Int, item: String) {
        editTextContents[id] = item

        updateBodyContentsStatus()
    }

    // 본문 EditText 전체 저장
    fun updateAllBodyText(){
        for ((id, content) in editTextContents) {
            if (id < bodyItem.size) {
                bodyItem[id].text = content
            } else {
                // 에러
            }
        }
    }

    private fun updateBodyContentsStatus(){
        editTextContents.forEach{
            if(it.value.isNotBlank()) { // 본문 텍스트 하나라도 있으면
                isBodyContentsExist.value = true
                return
            }
        }
        bodyItem.forEach {
            if(it.image.isNotBlank()){ // 본문 이미지 하나라도 있으면
                isBodyContentsExist.value = true
                return
            }
        }
        isBodyContentsExist.value = false
    }


    fun deleteBodyItem(){
        bodyItem.clear()
        bodyItemListData.value = bodyItem
    }

//    // 테스트용
//    fun printBodyItem(){
//        var count = 0
//        bodyItem.forEach {
//            println("current index: ${count++}")
//            println("current image count: ${it.images.size}")
//            println("current text: ${it.text}")
//        }
//    }


    /* 배경 사진(메인) 관련 */

    fun updateMainBackgroundImage(imageUri: String){
        mainBackgroundImage = imageUri
        mainBackgroundImageLiveData.value = mainBackgroundImage
    }

    fun isMainBackgroundImageUploaded():Boolean {
        return mainBackgroundImage.isNotBlank()
    }



    // 카테고리 주제 관련
    fun updateCategorySubject(text: String){
        categorySubject = text
        categorySubjectLiveData.value = categorySubject
    }

    // 카테고리 커버 사진 관련
    fun updateCategoryCoverImage(imageUri: String){
        categoryCoverImage = imageUri
        categoryCoverImageLiveData.value = categoryCoverImage
    }

    fun isCategoryCoverImageUploaded():Boolean {
        return categoryCoverImage.isNotBlank()
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