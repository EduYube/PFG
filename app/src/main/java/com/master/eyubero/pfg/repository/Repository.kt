package com.master.eyubero.pfg.repository

import com.google.firebase.database.FirebaseDatabase
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
    val dateList = ArrayList<MatchModel>()

    fun initDB() {

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


    private fun initDataBases() {

        initSportsDB()
        initUserDB()
    }

    private fun initUserDB() {

        val mUserRef = database.getReference("users")

        val admin = UserModel(0, "Yubero", true)
        mUserRef.child(admin.user).setValue(admin)
    }

    private fun initSportsDB() {

        for (sportId in 0 until sports.size) {

            val sport = SportModel(sportId, sports[sportId], initTeamsDB(), initRankingDB(), initMatchesDB())
            mSportRef.child("${sport.name}").setValue(sport)
        }
    }

    private fun initMatchesDB(): ArrayList<MatchModel> {

        dateList.clear()
        for (date in 1 until teamList.size) {
            when (date) {
                1 -> {
                    val match1 = MatchModel(teams[0], teams[1], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[3], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[5], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                2 -> {
                    val match1 = MatchModel(teams[0], teams[2], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[5], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[3], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                3 -> {
                    val match1 = MatchModel(teams[0], teams[3], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[4], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[1], teams[5], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                4 -> {
                    val match1 = MatchModel(teams[0], teams[4], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[1], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[3], teams[5], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    dateList.add(match1)
                    dateList.add(match2)
                    dateList.add(match3)
                }
                5 -> {
                    val match1 = MatchModel(teams[0], teams[5], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match2 = MatchModel(teams[2], teams[3], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
                    val match3 = MatchModel(teams[4], teams[1], "${Random().nextInt(5 - 0)} - ${Random().nextInt(5 - 0)}")
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
            val ranking = RankingModel(teamList[team], Random().nextInt(21 - 0))
            rankingList.add(ranking)
        }
        return rankingList
    }
}