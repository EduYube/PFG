package com.master.eyubero.pfg.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.ui.fragment.ResultsFragment
import android.content.Intent
import android.widget.Toast


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class MainActivity : AppCompatActivity() {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_activity, ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1 ){
            supportFragmentManager.popBackStack()
            count = 0
        } else {
            if(count < 1){
                count++
                Toast.makeText(this,"Si pulsa una segunda vez, saldrá de la appliación",Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

}
