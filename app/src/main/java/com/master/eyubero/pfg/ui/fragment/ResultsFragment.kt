package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentResultsBinding
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.ui.adapter.SportsAdapter
import com.master.eyubero.pfg.ui.viewModel.SportsViewModel


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class ResultsFragment : Fragment() {

    private lateinit var mBinding: FragmentResultsBinding
    private lateinit var mViewModel: SportsViewModel
    private lateinit var adapter: SportsAdapter
    var transaction: FragmentTransaction? = null
    private var mAuth: FirebaseAuth? = null
    var currentUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mViewModel = ViewModelProviders.of(this).get(SportsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")

        transaction = fragmentManager!!.beginTransaction()

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mBinding.resultRecyclerview.layoutManager = linearLayoutManager
        mBinding.resultRecyclerview.setHasFixedSize(true)

        getData()
        mBinding.swipeRefresh.setOnRefreshListener {
            getData()
            mBinding.swipeRefresh.isRefreshing = false
        }

        return mBinding.root
    }

    fun getData() {
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.progressBar.isIndeterminate = true

        if (isConnected(context!!)) {
            mViewModel.getData().observe(this, Observer {
                initRecyclerView(it)
            })

        }else {
            mViewModel.getDataWOInternet().observe(this, Observer {
                initRecyclerView(it)
            })
        }
    }

    fun isConnected(context: Context): Boolean {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connection = manager.activeNetworkInfo
        return connection != null && connection.isConnectedOrConnecting
    }

    override fun onResume() {
        super.onResume()
        currentUser = mAuth!!.currentUser
        getData()
    }

    fun initRecyclerView(sports: ArrayList<SportModel>?) {
        adapter = SportsAdapter(object : onSportItemClickListener {
            override fun onSportItemClick(sport: SportModel) {

                val fragment = SportRankingFragment.newInstance()
                val args = Bundle()
                args.putString("sport", sport.name)
                fragment.arguments = args

                transaction!!.replace(R.id.main_activity, fragment, SportRankingFragment::class.java.simpleName.toString())
                transaction!!.addToBackStack(null)
                transaction!!.commit()
            }
        }, sports!!)

        mBinding.resultRecyclerview.adapter = adapter
        mBinding.progressBar.visibility = View.GONE
        mBinding.progressBar.isIndeterminate = false
    }

    companion object {

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}
