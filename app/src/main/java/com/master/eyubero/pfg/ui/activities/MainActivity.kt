package com.master.eyubero.pfg.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.ui.fragment.ResultsFragment
import android.content.Intent
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import com.master.eyubero.pfg.ui.fragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.master.eyubero.pfg.ui.fragment.LogoutFragment


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class MainActivity : AppCompatActivity() {

    var count = 0
    var view: View? = null
    private var mAuth: FirebaseAuth? = null
    var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        view = findViewById(R.id.main_activity)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_activity, ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        currentUser = mAuth!!.currentUser
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            count = 0
        } else {
            if (count < 1) {
                count++
                Toast.makeText(this, "Si pulsa una segunda vez, saldrá de la appliación", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.login_menu, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_login ->{
                showMenu()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private fun showMenu() {
        val pm = PopupMenu(this, view)
        if(currentUser == null) {
            pm.menuInflater.inflate(R.menu.login_menu, pm.menu)
            pm.setOnMenuItemClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_activity, LoginFragment.newInstance(), LoginFragment::class.java.simpleName.toString())
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
        } else {
            pm.menuInflater.inflate(R.menu.logout_menu, pm.menu)
            pm.setOnMenuItemClickListener {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_activity, LogoutFragment.newInstance(), LogoutFragment::class.java.simpleName.toString())
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
        }
        pm.show()
    }

}
