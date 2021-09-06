package com.example.dontgifup.ui.view_models
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dontgifup.App
import com.example.dontgifup.data.models.Post
import com.example.dontgifup.domain.repositories.GifRepository
import com.example.dontgifup.errors.DataException
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PostState {
    LOADED, LOADING, ERROR
}

class MainViewModel() : ViewModel(), LifecycleObserver {

    @Inject lateinit var gifRepository: GifRepository

    val liveDataPost = MutableLiveData<Post>()
    val liveDataState = MutableLiveData<PostState>(PostState.LOADING)
    val liveDataErrorMessage = MutableLiveData<String>()
    val liveDataForwardBtnState = MutableLiveData<Boolean>(false)
    val liveDataBackBtnState = MutableLiveData<Boolean>(false)

    init {
        App.appComponent.inject(this)
        getRandomGif(gifRepository::getNextRandomGif)
    }

    fun backOnClick() {
        getRandomGif(gifRepository::getPrevGif)
    }

    fun forwardOnClick() {
        getRandomGif(gifRepository::getNextRandomGif)
    }

    private fun getRandomGif(method: suspend (currentPrimaryId: Int) -> Post) {
        viewModelScope.launch {
            liveDataState.postValue(PostState.LOADING)
            try {
                val res = method(liveDataPost.value?.primaryId ?: 0)
                println(liveDataPost.value?.primaryId)
                println(res.primaryId)
                liveDataPost.postValue(res)
                liveDataState.postValue(PostState.LOADED)
            } catch (e: DataException) {
                liveDataErrorMessage.postValue(e.message)
                liveDataState.postValue(PostState.ERROR)
            }
        }
    }
}