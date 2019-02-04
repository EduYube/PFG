package com.master.eyubero.pfg.ui.viewHolder

import android.support.v7.widget.RecyclerView
import com.master.eyubero.pfg.databinding.LayoutItemBinding
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 03/02/2019(ノಠ益ಠ)ノ
 */


class SportItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val mBinding = binding

    fun bind(sport: SportModel, listener: onSportItemClickListener){

        itemView.setOnClickListener {
            listener.onSportItemClick(sport)
        }

    }

}