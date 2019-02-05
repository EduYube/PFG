package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentResultsBinding
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel
import android.support.v7.widget.LinearLayoutManager
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.ui.adapter.SportsAdapter
import java.io.Serializable


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class ResultsFragment : Fragment() {

    private lateinit var mBinding: FragmentResultsBinding
    private lateinit var mViewModel: ResultsViewModel
    private lateinit var adapter: SportsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")

        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)



        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.resultRecyclerview.layoutManager = linearLayoutManager
        mBinding.resultRecyclerview.setHasFixedSize(true)

        val transaction = fragmentManager!!.beginTransaction()

        mViewModel.getData().observe(this, Observer {
            initRecyclerView(transaction,it)
        })

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        val transaction = fragmentManager!!.beginTransaction()

        mViewModel.getData().observe(this, Observer {
            initRecyclerView(transaction,it)
        })
    }

    fun initRecyclerView(transaction: FragmentTransaction, sports: ArrayList<SportModel>?) {
        adapter = SportsAdapter(object : onSportItemClickListener {
            override fun onSportItemClick(sport: SportModel) {
                val arg = Bundle()
                arg.putSerializable("ranking", sport.ranking as Serializable)

                transaction.replace(R.id.main_activity, SportRankingFragment.newInstance(), SportRankingFragment::class.java.simpleName.toString())
                transaction.commit()
            }
        }, sports!!)

        mBinding.resultRecyclerview.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}
