package com.master.eyubero.pfg.model

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 31/01/2019(ノಠ益ಠ)ノ
 */
class SportModel {
    var id : Int? = null
    var name: String? = null
    var teams: List<TeamModel>? = null
    var ranking: List<RankingModel>? = null

    constructor(id: Int, name: String, teams: List<TeamModel>,ranking: List<RankingModel>){
        this.id = id
        this.name = name
        this.teams = teams
        this.ranking = ranking
    }
    constructor(){}
}