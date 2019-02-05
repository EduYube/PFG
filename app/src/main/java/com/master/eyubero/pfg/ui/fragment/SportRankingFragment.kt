package com.master.eyubero.pfg.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.model.RankingModel


class SportRankingFragment : Fragment() {
    private var rankingList: List<RankingModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rankingList = it.get("ranking") as List<RankingModel>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sport_ranking, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SportRankingFragment()
    }
}
