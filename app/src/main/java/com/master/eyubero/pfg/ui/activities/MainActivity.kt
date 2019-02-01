package com.master.eyubero.pfg.ui.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.model.TeamModel
import com.master.eyubero.pfg.model.UserModel
import com.master.eyubero.pfg.ui.fragment.ResultsFragment

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 08/01/2019(ノಠ益ಠ)ノ
 */

class MainActivity : AppCompatActivity() {
    var context: Context? = null
    lateinit var mTeamRef: DatabaseReference
    lateinit var mSportRef: DatabaseReference
    lateinit var mUserRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    val sports = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()

        context = applicationContext
        sports.add(0, "baloncesto")
        sports.add(1, "balonmano")
        sports.add(2, "futbol")
        sports.add(3, "futbolSala")
        sports.add(4, "rugby")
        sports.add(5, "voleibol")

        initDataBases()

        transaction.replace(R.id.main_activity, ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
        transaction.commit()

    }

    private fun initDataBases() {
        database = FirebaseDatabase.getInstance()
        initSportsDB()
    }

    private fun initSportsDB() {

        mSportRef = database.getReference("sports")
        for (sportId in 0 until sports.size) {

            val sport = SportModel(sportId, sports[sportId], initTeamsDB())
            mSportRef.child("$sportId-${sport.name}").addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(context, "Ha habido algún error", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists())
                        mSportRef.child(sport.name).setValue(sport)
                    else
                        Toast.makeText(context, "Ya existe el deporte ${sport.name}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun initTeamsDB(): ArrayList<TeamModel> {
        val teamList = ArrayList<TeamModel>()
        for (teamId in 1..5) {

            val team = TeamModel(teamId, "team $teamId")
            teamList.add(team)
        }
        return teamList
    }
}
