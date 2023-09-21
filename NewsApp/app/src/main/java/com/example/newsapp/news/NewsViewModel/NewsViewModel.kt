package com.example.newsapp.news.NewsViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.news.models.News
import com.example.newsapp.news.sealed.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.*

class NewsViewModel : ViewModel() {

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchDataFromFirebase()

    }

    val templist2 = mutableListOf<News>()
    private fun fetchDataFromFirebase() {
        val tempList = mutableListOf<News>()
        response.value=DataState.Loading
        FirebaseDatabase.getInstance().getReference("Category")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for(DataSnap in snapshot.children){
                        val newsItem = DataSnap.getValue(News::class.java)
                        if(newsItem!=null){
                            tempList.add(newsItem)
                            templist2.add(newsItem)
                            Log.d("",newsItem.Name.toString())
                        }
                    }
                    response.value = DataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }
            }
            )
    }
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(templist2)
    //private val _persons = MutableStateFlow( allPersons)
    val persons = searchText.combine(_persons){ text, persons ->
        if(text.isBlank()){
            persons
        } else {
            persons.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(  5000 ),
        _persons.value
    )


    fun onSearchTextChange(text: String){
        _searchText.value = text
    }
}
