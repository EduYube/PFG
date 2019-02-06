package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
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
import com.master.eyubero.pfg.model.MatchModel
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager




class SportRankingFragment : Fragment() {
    private var rankingList: ArrayList<RankingModel>? = null
    private var matchesList: ArrayList<MatchModel>? = null
    private lateinit var mViewModel: ResultsViewModel
    private lateinit var mBinding: FragmentSportRankingBinding
    var sport: String? = null
    val mTextViewBorderWidth = 4
    var rankingTableLayout: TableLayout? = null
    var matchesTableLayout: TableLayout? = null
    var positions: List<RankingModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sport = arguments!!.getString("sport")

        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sport_ranking, container, false)
        activity!!.title = sport!!.capitalize()
        if(isConnected(context!!))
            getRanking()
        else
            getRankingWOInternet()

        mBinding.swipeRefresh.setOnRefreshListener {
            if(isConnected(context!!))
                getRanking()
            else
                getRankingWOInternet()
            mBinding.swipeRefresh.isRefreshing = false
        }

        rankingTableLayout = mBinding.tlRanking
        matchesTableLayout = mBinding.tlMatches

        return mBinding.root
    }

    fun isConnected(context: Context): Boolean {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connection = manager.activeNetworkInfo
        return connection != null && connection.isConnectedOrConnecting
    }

    private fun getRankingWOInternet() {

        mBinding.ivSport.setImageResource(setIcon())
        mBinding.tvSport.text = sport!!.toUpperCase()
        mViewModel.getRankingWOInternet(sport!!).observe(this, Observer {
            rankingList = it
            setPositions()
            initRanking()
        })

        mViewModel.getMatchesWOInternet(sport!!).observe(this, Observer{
            matchesList = it
            mViewModel.getRanking(sport!!)
            initMatches()
        })
    }

    fun getRanking() {

        mBinding.ivSport.setImageResource(setIcon())
        mBinding.tvSport.text = sport!!.toUpperCase()
        mViewModel.getRanking(sport!!).observe(this, Observer {
            rankingList = it
            setPositions()
            initRanking()
        })

        mViewModel.getMatches(sport!!).observe(this, Observer{
            matchesList = it
            mViewModel.getRanking(sport!!)
            initMatches()
        })
    }

    private fun setPositions() {
        positions = rankingList!!.sortedByDescending { it.points }
    }

    private fun initMatches() {
        matchesTableLayout!!.removeAllViews()

        matchesTableLayout!!.isStretchAllColumns = true
        matchesTableLayout!!.setPadding(mTextViewBorderWidth, mTextViewBorderWidth, mTextViewBorderWidth, mTextViewBorderWidth)

        for (currentRow in 0 until matchesList!!.size) {
            val tableRow = TableRow(activity)

            val localTeam = TextView(activity)
            val score = TextView(activity)
            val awayTeam = TextView(activity)

            localTeam.background = mBinding.root.context.getDrawable(R.drawable.bg_match)
            score.background = mBinding.root.context.getDrawable(R.drawable.bg_match)
            awayTeam.background = mBinding.root.context.getDrawable(R.drawable.bg_match)

            localTeam.setPadding(10, 10, 10, 10)
            score.setPadding(10, 10, 10, 10)
            awayTeam.setPadding(10, 10, 10, 10)

            localTeam.gravity = Gravity.CENTER
            score.gravity = Gravity.CENTER
            awayTeam.gravity = Gravity.CENTER

            localTeam.text = matchesList!![currentRow].local
            score.text = matchesList!![currentRow].score
            awayTeam.text = matchesList!![currentRow].away

            tableRow.addView(localTeam)
            tableRow.addView(score)
            tableRow.addView(awayTeam)

            tableRow.setBackgroundColor(activity!!.resources.getColor(R.color.colorAccent))

            matchesTableLayout!!.addView(tableRow)
        }
    }

    private fun initRanking() {
        rankingTableLayout!!.removeAllViews()

        rankingTableLayout!!.isStretchAllColumns = true
        rankingTableLayout!!.background = borderDrawable(mTextViewBorderWidth)
        rankingTableLayout!!.setPadding(mTextViewBorderWidth, mTextViewBorderWidth, mTextViewBorderWidth, mTextViewBorderWidth)

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

            rankingTableLayout!!.addView(tableRow)
        }
    }

    fun setIcon(): Int {
        mBinding.llHeader.gravity = Gravity.CENTER
        var icon: Int? = null
        when (sport) {
            "baloncesto" -> {
                mBinding.llHeader.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_basket)
                icon = R.drawable.ic_basketball
            }
            "balonmano" -> {
                mBinding.llHeader.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_handball)
                icon = R.drawable.ic_handball
            }
            "futbol" -> {
                mBinding.llHeader.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_football)
                icon = R.drawable.ic_football
            }
            "rugby" -> {
                mBinding.llHeader.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_rugby)
                icon = R.drawable.ic_rugby
            }
            "voleibol" -> {
                mBinding.llHeader.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_voley)
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
