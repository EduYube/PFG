package com.master.eyubero.pfg.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.master.eyubero.pfg.BR
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentResultsBinding
import com.master.eyubero.pfg.ui.adapter.ResultsRecyclerViewAdapter

import com.master.eyubero.pfg.ui.fragment.dummy.DummyContent
import com.master.eyubero.pfg.ui.fragment.dummy.DummyContent.DummyItem
import com.master.eyubero.pfg.ui.viewModel.ResultsViewModel

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class ResultsFragment : Fragment() {

    private var columnCount = 1

    private lateinit var mBinding: FragmentResultsBinding

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var mViewModel: ResultsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")
        mViewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)

        // Set the adapter
//       if (view is RecyclerView) {
//           with(view) {
//               mBinding.layoutManager = when {
//                   columnCount <= 1 -> LinearLayoutManager(context)
//                   else -> GridLayoutManager(context, columnCount)
//               }
//               adapter = ResultsRecyclerViewAdapter(DummyContent.ITEMS, listener)
//           }
//       }
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance() = ResultsFragment()
    }
}
