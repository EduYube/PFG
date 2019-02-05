package com.master.eyubero.pfg.ui.viewHolder

import android.support.v7.widget.RecyclerView
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.LayoutItemBinding
import com.master.eyubero.pfg.listeners.onSportItemClickListener
import com.master.eyubero.pfg.model.SportModel


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 03/02/2019(ノಠ益ಠ)ノ
 */


class SportItemViewHolder(val mBinding: LayoutItemBinding) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(sport: SportModel, listener: onSportItemClickListener){

        itemView.setOnClickListener {
            listener.onSportItemClick(sport)
        }

        mBinding.tvSportName.text = sport.name
        when(sport.id){
            0 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_basketball)
            1 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_handball)
            2 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_football)
            3 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_rugby)
            4 -> mBinding.ivSportIcon.setImageResource(R.drawable.ic_volleyball)
        }

    }

}