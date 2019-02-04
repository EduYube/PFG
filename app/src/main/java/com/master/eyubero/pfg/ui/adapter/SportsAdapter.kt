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
class SportsAdapter (val listener: onSportItemClickListener): RecyclerView.Adapter<SportItemViewHolder>()  {

    lateinit var mBinding: LayoutItemBinding
    val sportsList = ArrayList<SportModel>()

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
        mBinding.tvSportName.text = sport.name
        when(sport.id){
            0 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_basketball)
            1 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_handball)
            2 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_football)
            3 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_rugby)
            4 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_volleyball)
        }

    }

    fun addToSportList(sport: SportModel){
        sportsList.add(sport)
    }
}