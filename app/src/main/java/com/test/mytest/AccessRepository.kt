package com.test.mytest

import com.test.mytest.data.DummyDataProvider
import com.test.mytest.model.UserAccessData

class AccessRepository {

    fun getUserAccessData(): UserAccessData {
        return DummyDataProvider.getDummyUserAccessData()
    }

}