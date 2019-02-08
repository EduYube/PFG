package com.master.eyubero.pfg.ui.fragment

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment

import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentSportRankingBinding
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel
import android.widget.*
import android.widget.TextView
import android.graphics.drawable.GradientDrawable
import com.master.eyubero.pfg.model.MatchModel
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.master.eyubero.pfg.databinding.CustomLayoutBinding
import com.master.eyubero.pfg.repository.Repository


class SportRankingFragment : Fragment() {
    private var rankingList: ArrayList<RankingModel>? = null
    private var matchesList: ArrayList<MatchModel>? = null

    private lateinit var mViewModel: ResultsViewModel
    private lateinit var mBinding: FragmentSportRankingBinding
    lateinit var mDialogBinding: CustomLayoutBinding

    var sport: String? = null

    val mTextViewBorderWidth = 4
    var rankingTableLayout: TableLayout? = null
    var matchesTableLayout: TableLayout? = null
    var positions: List<RankingModel>? = null

    private var mAuth: FirebaseAuth? = null
    var currentUser: FirebaseUser? = null

    lateinit var dialogs: Dialog

    lateinit var mDataBase: DatabaseReference
    lateinit var mMatchesDB: DatabaseReference
    lateinit var mRankingDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sport = arguments!!.getString("sport")
        mAuth = FirebaseAuth.getInstance()
        mDataBase = Repository().mSportRef.child(sport!!)
        mMatchesDB = mDataBase.child("matches")
        mRankingDB = mDataBase.child("ranking")
        currentUser = mAuth!!.currentUser
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        currentUser = mAuth!!.currentUser
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sport_ranking, container, false)
        mDialogBinding = DataBindingUtil.inflate(inflater, R.layout.custom_layout, container, false)

        dialogs = Dialog(activity)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(mDialogBinding.root)
        activity!!.title = sport!!.capitalize()

        showLoading()
        if (isConnected(context!!))
            getRanking()
        else
            getRankingWOInternet()
        if (currentUser != null) {
            mBinding.addResult.visibility = View.VISIBLE
            mBinding.fabResult.setOnClickListener {
                showDialog()
            }
            mBinding.fabResult.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mBinding.root.context, setFABBackgroundColor()))
        } else {
            mBinding.addResult.visibility = View.GONE
        }

        mBinding.swipeRefresh.setOnRefreshListener {
            if (isConnected(context!!))
                getRanking()
            else
                getRankingWOInternet()
            mBinding.swipeRefresh.isRefreshing = false
        }

        rankingTableLayout = mBinding.tlRanking
        matchesTableLayout = mBinding.tlMatches

        return mBinding.root
    }

    fun showLoading() {

        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.progressBar.isIndeterminate = true
    }

    fun hideLoading() {

        mBinding.progressBar.visibility = View.GONE
        mBinding.progressBar.isIndeterminate = false
    }

    private fun showDialog() {

        mDialogBinding.tietLocal.setText("")
        mDialogBinding.tietScore.setText("")
        mDialogBinding.tietAway.setText("")
        mDialogBinding.tietLocal.requestFocus()
        mDialogBinding.btYes.setOnClickListener {
            showLoading()
            val local = mDialogBinding.tietLocal.text.toString()
            val score = mDialogBinding.tietScore.text.toString()
            val away = mDialogBinding.tietAway.text.toString()
            mViewModel.saveResult(local, score, away, mMatchesDB, mRankingDB)
            dialogs.dismiss()
            hideLoading()
        }
        mDialogBinding.btNo.setOnClickListener {
            dialogs.dismiss()
        }
        dialogs.show()

    }

    fun isConnected(context: Context): Boolean {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connection = manager.activeNetworkInfo
        return connection != null && connection.isConnectedOrConnecting
    }

    private fun getRankingWOInternet() {
        showLoading()
        mBinding.ivSport.setImageResource(setIcon())
        mBinding.tvSport.text = sport!!.toUpperCase()
        mViewModel.getRankingWOInternet(sport!!).observe(this, Observer {
            rankingList = it
            setPositions()
            initRanking()
        })

        mViewModel.getMatchesWOInternet(sport!!).observe(this, Observer {
            matchesList = it
            mViewModel.getRanking(sport!!)
            initMatches()
        })
    }

    fun getRanking() {
        showLoading()
        mBinding.ivSport.setImageResource(setIcon())
        mBinding.tvSport.text = sport!!.toUpperCase()
        mViewModel.getRanking(sport!!).observe(this, Observer {
            rankingList = it
            setPositions()
            initRanking()
        })

        mViewModel.getMatches(sport!!).observe(this, Observer {
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

            tableRow.setBackgroundColor(activity!!.resources.getColor(R.color.white))

            mViewModel.saveResult(matchesList!![currentRow].local!!,
                    matchesList!![currentRow].score!!,
                    matchesList!![currentRow].away!!,
                    mMatchesDB,
                    mRankingDB)
            matchesTableLayout!!.addView(tableRow)
        }
        hideLoading()
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

            tableRow.setBackgroundColor(activity!!.resources.getColor(R.color.white))

            rankingTableLayout!!.addView(tableRow)
        }
        hideLoading()
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

    private fun setFABBackgroundColor(): Int {
        var color: Int? = null
        when (sport) {
            "baloncesto" -> {
                color = R.color.orange
            }
            "balonmano" -> {
                color = R.color.red
            }
            "futbol" -> {
                color = R.color.pink
            }
            "rugby" -> {
                color = R.color.brown
            }
            "voleibol" -> {
                color = R.color.blue
            }
        }
        return color!!
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
