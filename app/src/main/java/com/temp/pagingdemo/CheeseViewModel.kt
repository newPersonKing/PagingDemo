package com.temp.pagingdemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import paging.android.example.com.pagingsample.Cheese

open class CheeseViewModel(app : Application) : AndroidViewModel(app) {


    val dao = CheeseDb.get(app).cheeseDao()

    val allCheeses = LivePagedListBuilder(dao.allCheesesByName(), PagedList.Config.Builder()
            .setPageSize(10)/*设置每次请求数量*/
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)/*设置初始化数量*/
            .build()).build()

    fun insert(text: CharSequence) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(cheese)
    }
}
