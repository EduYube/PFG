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

    fun bind(sport: SportModel, listener: onSportItemClickListener) {

        itemView.setOnClickListener {
            listener.onSportItemClick(sport)
        }

        mBinding.tvSportName.text = sport.name!!.capitalize()
        when (sport.id) {
            0 -> {
                mBinding.ivSportIcon.setImageResource(R.drawable.ic_basketball)
                mBinding.clItem.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_basket)
            }
            1 -> {
                mBinding.ivSportIcon.setImageResource(R.drawable.ic_handball)
                mBinding.clItem.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_handball)
            }
            2 -> {
                mBinding.ivSportIcon.setImageResource(R.drawable.ic_football)
                mBinding.clItem.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_football)
            }
            3 -> {
                mBinding.ivSportIcon.setImageResource(R.drawable.ic_rugby)
                mBinding.clItem.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_rugby)
            }
            4 -> {
                mBinding.ivSportIcon.setImageResource(R.drawable.ic_volleyball)
                mBinding.clItem.background = mBinding.root.context.resources.getDrawable(R.drawable.bg_voley)
            }
        }

    }

}