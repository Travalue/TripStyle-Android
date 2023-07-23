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

    // 본문 아이템(사진 또는 글) 존재 여부
    val isBodyContentsExist = MutableLiveData<Boolean>()




    init {
        addBodyItem() // 최초에 본문 1개 생성
        isBodyContentsExist.value = false
    }



    // 데이터 전체 삭제(초기화)
    fun deleteAllItem(){
        deleteMainBackgroundImage()
        deleteTitleAndSubtitle()
        deleteScheduleItem()
        deleteBodyItem()
        deleteTempBodyTextItem()
        deleteCategorySubject()
        deleteCategoryCoverImage()
        isBodyContentsExist.value = false
    }



    /* 제목, 부제목 관련 */


    // 제목, 부제목 저장
    fun updateTitleAndSubtitle(currentTitle: String, currentSubtitle: String){
        title = currentTitle
        subtitle = currentSubtitle
    }

    // 제목, 부제목 삭제(초기화)
    private fun deleteTitleAndSubtitle(){
        title = ""
        subtitle = ""
    }




    /* 본문(사진-글, 사진-글 ...) 관련 */


    // 본문 세트 추가
    fun addBodyItem() {
        // EditText 받아오기
        updateAllBodyText()

        // 빈 아이템 추가
        bodyItem.add(TravellerWriteResult("", ""))
        bodyItemListData.value = bodyItem
    }

    // 본문 상태 저장
    fun updateBodyItem(pos: Int, item: TravellerWriteResult) {
        // EditText 받아오기
        updateAllBodyText()

        // 이미지 update
        val updatedItem = TravellerWriteResult(item.image, bodyItem[pos].text)
        bodyItem[pos] = updatedItem
        bodyItemListData.value = bodyItem

        updateBodyContentsStatus()
    }

    // 특정 위치의 본문 텍스트 상태 저장
    fun updateBodyTextItem(id: Int, item: String) {
        editTextContents[id] = item

        updateBodyContentsStatus()
    }

    // 본문 텍스트 전체 저장
    fun updateAllBodyText(){
        for ((id, content) in editTextContents) {
            if (id < bodyItem.size) {
                bodyItem[id].text = content
            } else {
                // 에러
            }
        }
    }

    // 본문 아이템 존재 상태 업데이트
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

    // 본문 임시저장 텍스트 삭제
    private fun deleteTempBodyTextItem(){
        editTextContents.clear()
    }

    // 본문 아이템 삭제
    private fun deleteBodyItem(){
        bodyItem.clear()
        bodyItemListData.value = bodyItem
    }


    /* 배경 사진(메인) 관련 */


    // 배경사진 저장
    fun updateMainBackgroundImage(imageUri: String){
        mainBackgroundImage = imageUri
        mainBackgroundImageLiveData.value = mainBackgroundImage
    }

    // 배경사진 삭제(초기화)
    private fun deleteMainBackgroundImage(){
        mainBackgroundImage = ""
        mainBackgroundImageLiveData.value = mainBackgroundImage
    }

    // 배경사진 존재 여부 확인
    fun isMainBackgroundImageUploaded():Boolean {
        return mainBackgroundImage.isNotBlank()
    }



    /* 카테고리 주제 관련 */


    // 카테고리 주제 저장
    fun updateCategorySubject(text: String){
        categorySubject = text
        categorySubjectLiveData.value = categorySubject
    }

    // 카테고리 주제 삭제(초기화)
    private fun deleteCategorySubject(){
        categorySubject = ""
        categorySubjectLiveData.value = categorySubject
    }



    /* 카테고리 커버 사진 관련 */


    // 카테고리 커버 이미지 저장
    fun updateCategoryCoverImage(imageUri: String){
        categoryCoverImage = imageUri
        categoryCoverImageLiveData.value = categoryCoverImage
    }

    // 카테고리 커버 이미지 삭제(초기화)
    private fun deleteCategoryCoverImage(){
        categoryCoverImage = ""
        categoryCoverImageLiveData.value = categoryCoverImage
    }

    // 카테고리 커버 이미지 존재 여부 확인
    fun isCategoryCoverImageUploaded():Boolean {
        return categoryCoverImage.isNotBlank()
    }



    /* 지도 및 일정 관련 */


    // 일정 저장
    fun addScheduleItem(item: Schedule){
        scheduleItem.add(item)
        scheduleItemListData.value = scheduleItem
    }

    // 일정 삭제
    fun deleteScheduleItem(){
        scheduleItem.clear()
        scheduleItemListData.value = scheduleItem
    }
}