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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()

        context = applicationContext

        initDataBases()

        transaction.replace(R.id.main_activity, ResultsFragment.newInstance(), ResultsFragment::class.java.simpleName.toString())
        transaction.commit()

    }

    private fun initDataBases() {
        database = FirebaseDatabase.getInstance()
//        initTeamsDB()
//        initSportsDB()
//        initUserDB()
    }

    private fun initUserDB() {

        mUserRef = database.getReference("user")

        val admin = UserModel(0,"Yube",true)
        val user = UserModel(1,"Edu",false)

        mUserRef.child("admin").setValue(admin)
        mUserRef.child("user").setValue(user)
    }

    private fun initSportsDB() {

        mSportRef = database.getReference("sport")
        for (i in 1..3) {
            val teamId = i.toString()

            val sport = SportModel(teamId.toInt(), "sport $i")
            mSportRef.child("$i").addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(context, "Ha habido algún error", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists())
                        mSportRef.child(teamId).setValue(sport).addOnCompleteListener {
                            Toast.makeText(context, "Se ha añadido el deporte ${sport.name}", Toast.LENGTH_LONG).show()
                        }
                    else
                        Toast.makeText(context, "Ya existe el deporte ${sport.name}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun initTeamsDB() {
        mTeamRef = database.getReference("teams")
        for (i in 1..5) {
            val teamId = i.toString()

            val team = TeamModel(teamId.toInt(), "team $i", "balonmano")
            mTeamRef.child("$i").addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(context, "Ha habido algún error", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists())
                        mTeamRef.child(teamId).setValue(team).addOnCompleteListener {
                            Toast.makeText(context, "Se ha añadido el equipo ${team.name} al deporte ${team.sport}", Toast.LENGTH_LONG).show()
                        }
                    else
                        Toast.makeText(context, "Ya existe el equipo ${team.name} al deporte ${team.sport}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
