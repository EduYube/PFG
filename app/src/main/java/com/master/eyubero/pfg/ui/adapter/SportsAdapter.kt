package com.master.eyubero.pfg.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.LayoutItemBinding
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.ui.viewHolder.SportItemViewHolder
import java.text.FieldPosition


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 03/02/2019(ノಠ益ಠ)ノ
 */
class SportsAdapter (val listener: onSportItemClickListener, val sportsList: ArrayList<SportModel>): RecyclerView.Adapter<SportItemViewHolder>()  {

    lateinit var mBinding: LayoutItemBinding

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): SportItemViewHolder {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.layout_item, container, false)
        return SportItemViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return sportsList.size
    }

    override fun onBindViewHolder(itemVH: SportItemViewHolder, position: Int) {

        val sport = sportsList[position]
        itemVH.bind(sport,listener)
    }
}