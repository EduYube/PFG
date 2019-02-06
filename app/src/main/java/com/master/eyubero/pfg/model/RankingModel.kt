package com.master.eyubero.pfg.model

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 01/02/2019(ノಠ益ಠ)ノ
 */
class RankingModel {

    var team: TeamModel? = null
    var points: Int? = null

    constructor(team: TeamModel, points: Int){
        this.team = team
        this.points = points
    }

    constructor(){}
}
