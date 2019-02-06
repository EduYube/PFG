package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentSportRankingBinding
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel
import android.widget.*
import android.widget.TextView
import android.graphics.drawable.GradientDrawable


class SportRankingFragment : Fragment() {
    private var rankingList: ArrayList<RankingModel>? = null
    private lateinit var mViewModel: ResultsViewModel
    private lateinit var mBinding: FragmentSportRankingBinding
    var sport: String? = null
    val mTextViewBorderWidth = 4
    val mTableBorderWidth = mTextViewBorderWidth * 2
    var tableLayout: TableLayout? = null
    var positions: List<RankingModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sport = arguments!!.getString("sport")

        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sport_ranking, container, false)
        getRanking()

        mBinding.swipeRefresh.setOnRefreshListener {
            getRanking()
            mBinding.swipeRefresh.isRefreshing = false
        }

        tableLayout = mBinding.tlRanking

        return mBinding.root
    }

    fun getRanking() {

        mBinding.ivSport.setImageResource(setIcon())
        mBinding.tvSport.text = sport!!.toUpperCase()
        mViewModel.getRanking(sport!!).observe(this, Observer {
            rankingList = it
            setPositions()
            initTable()
        })
    }

    private fun setPositions() {
        positions = rankingList!!.sortedByDescending { it.points }
    }

    private fun initTable() {
        tableLayout!!.removeAllViews()

        tableLayout!!.isStretchAllColumns = true
        tableLayout!!.background = borderDrawable(mTableBorderWidth)
        tableLayout!!.setPadding(mTableBorderWidth, mTableBorderWidth, mTableBorderWidth, mTableBorderWidth)

        for (currentRow in 0 until positions!!.size) {
            val tableRow = TableRow(activity)

            val position = TextView(activity)
            val team = TextView(activity)
            val points = TextView(activity)

            position.background = borderDrawable(mTextViewBorderWidth)
            team.background = borderDrawable(mTextViewBorderWidth)
            points.background = borderDrawable(mTextViewBorderWidth)

            position.setPadding(15, 15, 15, 15)
            team.setPadding(15, 15, 15, 15)
            points.setPadding(15, 15, 15, 15)

            position.gravity = Gravity.CENTER
            team.gravity = Gravity.CENTER
            points.gravity = Gravity.CENTER

            position.text = (currentRow + 1).toString()
            team.text = positions!![currentRow].team!!.name
            points.text = positions!![currentRow].points.toString()

            tableRow.addView(position)
            tableRow.addView(team)
            tableRow.addView(points)

            tableRow.setBackgroundColor(activity!!.resources.getColor(R.color.colorAccent))

            tableLayout!!.addView(tableRow)
        }
    }

    fun setIcon(): Int {
        var icon: Int? = null
        when (sport) {
            "baloncesto" -> {
                icon = R.drawable.ic_basketball
            }
            "balonmano" -> {
                icon = R.drawable.ic_handball
            }
            "futbol" -> {
                icon = R.drawable.ic_football
            }
            "rugby" -> {
                icon = R.drawable.ic_rugby
            }
            "voleibol" -> {
                icon = R.drawable.ic_volleyball
            }
        }
        return icon!!
    }

    private fun borderDrawable(borderWidth: Int): GradientDrawable {
        val shapeDrawable = GradientDrawable()
        shapeDrawable.setStroke(borderWidth, activity!!.resources.getColor(R.color.black))
        return shapeDrawable
    }

    companion object {
        @JvmStatic
        fun newInstance() = SportRankingFragment()
    }
}
