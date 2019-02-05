package com.master.eyubero.pfg.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.repository.Repository
import com.master.eyubero.pfg.ui.fragment.ResultsFragment
import android.content.Intent



/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.main_activity, ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
        transaction.commit()

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1 ){
            supportFragmentManager.popBackStack()
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
