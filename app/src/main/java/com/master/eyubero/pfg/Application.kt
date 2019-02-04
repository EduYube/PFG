package com.master.eyubero.pfg

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.master.eyubero.pfg.model.RankingModel
import com.master.eyubero.pfg.model.SportModel
import com.master.eyubero.pfg.model.TeamModel
import com.master.eyubero.pfg.model.UserModel
import java.lang.Math.random
import java.util.*
import java.util.EnumSet.range
import kotlin.math.nextUp


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 01/02/2019(ノಠ益ಠ)ノ
 */
class Application : Application() {

    lateinit var mSportRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    val sports = ArrayList<String>()

    val teamList = ArrayList<TeamModel>()
    val rankingList = ArrayList<RankingModel>()

    override fun onCreate() {

        super.onCreate()
        sports.add(0, "baloncesto")
        sports.add(1, "balonmano")
        sports.add(2, "futbol")
        sports.add(3, "rugby")
        sports.add(4, "voleibol")

        initDataBases()
    }


    private fun initDataBases() {

        database = FirebaseDatabase.getInstance()
        initSportsDB()
        initUserDB()
    }

    private fun initUserDB() {

        val mUserRef = database.getReference("users")

        val admin = UserModel(0, "Yubero", true)
        mUserRef.child(admin.user).setValue(admin)
    }

    private fun initSportsDB() {

        mSportRef = database.getReference("sports")
        for (sportId in 0 until sports.size) {

            val sport = SportModel(sportId, sports[sportId], initTeamsDB(), initRankingDB())
            mSportRef.child("$sportId-${sport.name}").setValue(sport)
        }
    }

    private fun initTeamsDB(): ArrayList<TeamModel> {

        for (teamId in 1..5) {

            val team = TeamModel(teamId, "team $teamId")
            teamList.add(team)
        }
        return teamList
    }

    private fun initRankingDB(): ArrayList<RankingModel> {

        for (team in 0 until teamList.size) {
            val ranking = RankingModel(team+1,teamList[team],Random().nextInt(21-0))
            rankingList.add(ranking)
        }
        return rankingList
    }
}