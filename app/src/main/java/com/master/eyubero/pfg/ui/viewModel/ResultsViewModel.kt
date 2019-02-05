package com.master.eyubero.pfg.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.repository.Repository


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 29/01/2019(ノಠ益ಠ)ノ
 */
class ResultsViewModel : ViewModel() {

    private val sportsLD = MutableLiveData<ArrayList<SportModel>>()
    private val sports = ArrayList<SportModel>()
    private val mDtaBase = Repository().mSportRef

    fun getData(): MutableLiveData<ArrayList<SportModel>> {
        sports.clear()
        mDtaBase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("DataBaseError <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                for (sport in data.children) {
                    val model = sport.getValue(SportModel::class.java)
                    if (!checkIfExist(model!!))
                        sports.add(model)
                }
                sportsLD.postValue(sports)
            }

        })
        return sportsLD
    }

    private fun checkIfExist(model: SportModel): Boolean {

        var exists = false
        for (i in 0 until sports.size) {

            if (sports[i].id == model.id || sports[i].name == model.name) {
                exists = true
                break
            }
        }
        return exists
    }
}