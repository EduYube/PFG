package com.master.eyubero.pfg.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.master.eyubero.pfg.model.MatchModel
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.repository.Repository


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 29/01/2019(ノಠ益ಠ)ノ
 */
class ResultsViewModel : ViewModel() {

    private val sportsLD = MutableLiveData<ArrayList<SportModel>>()
    private val sports = ArrayList<SportModel>()
    private val rankingLD = MutableLiveData<ArrayList<RankingModel>>()
    private val ranking = ArrayList<RankingModel>()
    private val matchesLD = MutableLiveData<ArrayList<MatchModel>>()
    private val matches = ArrayList<MatchModel>()
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

    fun getDataWOInternet(): MutableLiveData<ArrayList<SportModel>> {
        sports.clear()
        mDtaBase.limitToLast(10).addValueEventListener(object : ValueEventListener {
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

    fun getMatchesWOInternet(sport: String): MutableLiveData<ArrayList<MatchModel>> {
        mDtaBase.child(sport).child("matches").limitToLast(10).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("error <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                matches.clear()
                for (rank in data.children) {
                    val model = rank.getValue(MatchModel::class.java)
                    matches.add(model!!)
                }
                matchesLD.postValue(matches)
            }

        })
        return matchesLD
    }

    fun getMatches(sport: String): MutableLiveData<ArrayList<MatchModel>> {
        mDtaBase.child(sport).child("matches").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("error <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                matches.clear()
                for (rank in data.children) {
                    val model = rank.getValue(MatchModel::class.java)
                    matches.add(model!!)
                }
                matchesLD.postValue(matches)
            }

        })
        return matchesLD
    }

    fun getRankingWOInternet(sport: String): MutableLiveData<ArrayList<RankingModel>> {
        mDtaBase.child(sport).child("ranking").limitToLast(10).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("error <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                ranking.clear()
                for (rank in data.children) {
                    val model = rank.getValue(RankingModel::class.java)
                    ranking.add(model!!)
                }
                rankingLD.postValue(ranking)
            }

        })
        return rankingLD
    }

    fun getRanking(sport: String): MutableLiveData<ArrayList<RankingModel>> {
        mDtaBase.child(sport).child("ranking").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("error <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                ranking.clear()
                for (rank in data.children) {
                    val model = rank.getValue(RankingModel::class.java)
                    ranking.add(model!!)
                }
                rankingLD.postValue(ranking)
            }

        })
        return rankingLD
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