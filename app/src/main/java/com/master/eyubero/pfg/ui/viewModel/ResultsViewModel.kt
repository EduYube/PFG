package com.master.eyubero.pfg.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.*
import com.master.eyubero.pfg.model.MatchModel
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.repository.Repository


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 29/01/2019(ノಠ益ಠ)ノ
 */
class ResultsViewModel : ViewModel() {

    private val rankingLD = MutableLiveData<ArrayList<RankingModel>>()
    private val ranking = ArrayList<RankingModel>()
    private val matchesLD = MutableLiveData<ArrayList<MatchModel>>()
    private val matches = ArrayList<MatchModel>()
    private val mDtaBase = Repository().mSportRef
    var exists = MutableLiveData<Boolean>()

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


    fun saveResult(local: String, score: String, away: String, mMatchesDB: DatabaseReference, mRankingDB: DatabaseReference) {

        for (i in 0 until matches.size) {

            if (matches[i].away == away.toUpperCase() && matches[i].local == local.toUpperCase()) {
                matches[i].score = score
                mMatchesDB.setValue(matches)
                for (id in 0 until ranking.size) {
                    if (local.toUpperCase() == ranking[id].team!!.name)
                        ranking[id].points = Repository().setPoints(ranking[id].team!!,matches)
                    if (away.toUpperCase() == ranking[id].team!!.name)
                        ranking[id].points = Repository().setPoints(ranking[id].team!!,matches)

                }
                exists.postValue(true)
                mRankingDB.setValue(ranking)
            }
        }
    }
}