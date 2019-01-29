package com.master.eyubero.pfg.ui.viewModel
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.os.Handler


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 29/01/2019(ノಠ益ಠ)ノ
 */
class ResultsViewModel: ViewModel() {

    var isLoading: ObservableField<Boolean> = ObservableField()

    fun showProgressBar() {

        isLoading.set(true)
        isLoading.notifyChange()
        Handler().postDelayed( {

            isLoading.set(false)
            isLoading.notifyChange()

        },3000)
    }
}