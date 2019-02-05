package com.master.eyubero.pfg.ui.viewModel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentResultsBinding
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.repository.Repository
import com.master.eyubero.pfg.ui.adapter.SportsAdapter
import com.master.eyubero.pfg.ui.fragment.SportRankingFragment
import java.io.Serializable


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 29/01/2019(ノಠ益ಠ)ノ
 */
class ResultsViewModel : ViewModel() {

    private lateinit var adapter: SportsAdapter
    private val sports = ArrayList<SportModel>()

    fun getData(mBinding: FragmentResultsBinding, transaction: FragmentTransaction) {
        val mDtaBase = Repository().mSportRef
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
                initRecyclerView(transaction)
                mBinding.resultRecyclerview.adapter = adapter
            }

        })
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

    fun initRecyclerView(transaction: FragmentTransaction) {
        adapter = SportsAdapter(object : onSportItemClickListener {
            override fun onSportItemClick(sport: SportModel) {
                val arg = Bundle()
                arg.putSerializable("ranking", sport.ranking as Serializable)

                transaction.replace(R.id.main_activity, SportRankingFragment.newInstance(), SportRankingFragment::class.java.simpleName.toString())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }, sports)
    }
}