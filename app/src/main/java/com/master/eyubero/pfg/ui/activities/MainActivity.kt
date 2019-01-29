package com.master.eyubero.pfg.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.ui.fragment.ResultsFragment
import com.master.eyubero.pfg.ui.fragment.dummy.DummyContent

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class MainActivity : AppCompatActivity(), ResultsFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        val runnable = Runnable {

            transaction.add(ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        Handler().postDelayed(runnable,3000)
    }
}
