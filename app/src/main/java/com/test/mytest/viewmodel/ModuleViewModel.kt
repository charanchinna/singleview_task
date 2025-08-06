package com.test.mytest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.test.mytest.AccessRepository
import com.test.mytest.model.Module
import com.test.mytest.utils.AccessManager


class ModuleViewModel : ViewModel() {

    private val repository= AccessRepository()


    private val accessManager = AccessManager(repository.getUserAccessData().user)

    private val _coolingMessage = MutableLiveData<String>()
    val coolingMessage: LiveData<String> = _coolingMessage

    val modules = MutableLiveData(repository.getUserAccessData().modules)


    fun checkModuleAccess(module: Module): String {
        return when {
            accessManager.isInCoolingPeriod() -> "Access denied: cooling period"
            !accessManager.hasAccess(module.id) -> "Access denied: no permission"
            else -> "Navigating to ${module.title}"
        }
    }


    init {
        startCoolingTimer()
    }

    private fun startCoolingTimer() {
        viewModelScope.launch {
            while (true) {
                if (accessManager.isInCoolingPeriod()) {
                    _coolingMessage.postValue(accessManager.getCoolingRemaining())
                } else {
                    _coolingMessage.postValue("")
                }
                delay(1000)
            }
        }
    }


}

