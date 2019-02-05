package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentSportRankingBinding
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel


class SportRankingFragment : Fragment() {
    private var rankingList: List<RankingModel>? = null
    private lateinit var mViewModel: ResultsViewModel
    private lateinit var mBinding: FragmentSportRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        mViewModel.getData().observe(this, Observer {
            for (i in 0 until it!!.size){
                rankingList = it[i].ranking
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sport_ranking,container,false)
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SportRankingFragment()
    }
}
