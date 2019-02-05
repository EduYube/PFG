package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.master.eyubero.pfg.repository.Repository
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentResultsBinding
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.ui.adapter.SportsAdapter
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel
import java.io.Serializable
import android.support.v7.widget.LinearLayoutManager



/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class ResultsFragment : Fragment() {

    private lateinit var mBinding: FragmentResultsBinding
    private lateinit var mViewModel: ResultsViewModel
    private lateinit var adapter: SportsAdapter
    private val sports = ArrayList<SportModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")

        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)

        val mDtaBase = Repository().mSportRef
        mDtaBase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "No se han podido obtener los datos", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(data: DataSnapshot) {
                for(sport in data.children) {
                    sports.add(sport.getValue(SportModel::class.java)!!)
                }
                initRecyclerView()
                mBinding.resultRecyclerview.adapter = adapter
            }

        })

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.resultRecyclerview.layoutManager = linearLayoutManager
        mBinding.resultRecyclerview.setHasFixedSize(true)

        return mBinding.root
    }

    private fun initRecyclerView() {
        adapter = SportsAdapter(object : onSportItemClickListener {
            override fun onSportItemClick(sport: SportModel) {
                val arg = Bundle()
                arg.putSerializable("ranking", sport.ranking as Serializable)

                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.main_activity, SportRankingFragment.newInstance(), SportRankingFragment::class.java.simpleName.toString())
                transaction.commit()
            }
        }, sports)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}
