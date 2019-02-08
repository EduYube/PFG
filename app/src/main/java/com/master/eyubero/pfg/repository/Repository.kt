package com.master.eyubero.pfg.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.master.eyubero.pfg.model.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 01/02/2019(ノಠ益ಠ)ノ
 */
class Repository {

    val database = FirebaseDatabase.getInstance()
    val mSportRef = database.getReference("sports")
    val sports = ArrayList<String>()
    val teams = ArrayList<String>()

    val teamList = ArrayList<TeamModel>()
    val rankingList = ArrayList<RankingModel>()
    var dateList = ArrayList<MatchModel>()

    fun initDB() {

        mSportRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("error <3", p0.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                if (!data.exists()) {
                    mSportRef.keepSynced(true)

                    sports.add(0, "baloncesto")
                    sports.add(1, "balonmano")
                    sports.add(2, "futbol")
                    sports.add(3, "rugby")
                    sports.add(4, "voleibol")

                    teams.add(0, "ETSISI")
                    teams.add(1, "ETSIST")
                    teams.add(2, "ETSIDI")
                    teams.add(3, "ETSII")
                    teams.add(4, "INEF")
                    teams.add(5, "ETSIAE")

                    initDataBases()
                }
            }

        })
    }


    private fun initDataBases() {

        initSportsDB()
    }

    private fun initSportsDB() {

        for (sportId in 0 until sports.size) {

            val sport = SportModel(sportId, sports[sportId], initTeamsDB(), initMatchesDB(), initRankingDB())
            mSportRef.child("${sport.name}").setValue(sport)
        }
    }

    private fun initMatchesDB(): ArrayList<MatchModel> {

        dateList.clear()
        for (date in 1 until teamList.size) {
            when (date) {
                1 -> {
                    val match1 = MatchModel(teams[0], teams[1], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[3], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[5], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                2 -> {
                    val match1 = MatchModel(teams[0], teams[2], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[1], teams[5], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[3], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                3 -> {
                    val match1 = MatchModel(teams[0], teams[3], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[5], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[1], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                4 -> {
                    val match1 = MatchModel(teams[0], teams[4], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[1], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[5], teams[3], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                5 -> {
                    val match1 = MatchModel(teams[0], teams[5], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[4], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[3], teams[1], "${Random().nextInt(5 - 0)}-${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
            }
        }
        return dateList
    }

    private fun initTeamsDB(): ArrayList<TeamModel> {

        teamList.clear()
        for (teamId in 0 until teams.size) {

            val team = TeamModel(teamId, teams[teamId])
            teamList.add(team)
        }
        return teamList
    }

    private fun initRankingDB(): ArrayList<RankingModel> {

        rankingList.clear()
        for (team in 0 until teamList.size) {
            val ranking = RankingModel(teamList[team], setPoints(teamList[team], dateList))
            rankingList.add(ranking)
        }
        return rankingList
    }

    fun setPoints(team: TeamModel, matches: ArrayList<MatchModel>): Int {
        var points = 0
        if(dateList.size == 0)
            dateList = matches
        for (i in 0 until dateList.size) {
            var local = false
            var away = false
            if (dateList[i].away == team.name) {
                away = true
                local = false
            }
            if (dateList[i].local == team.name) {
                away = false
                local = true
            }

            if (local || away) {
                val score = dateList[i].score
                val localScore = score!!.substringBefore("-")
                val awayScore = score.substringAfter("-")
                if ((localScore.toInt() > awayScore.toInt() && local) || (localScore.toInt() < awayScore.toInt() && away)) {
                    points += 3
                } else if (localScore.toInt() == awayScore.toInt())
                    points += 1
            }


        }
        return points
    }
}