package com.master.eyubero.pfg.model

/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 01/02/2019(ノಠ益ಠ)ノ
 */
class RankingModel {

    var position: Int? = null
    var team: TeamModel? = null
    var points: Int? = null

    constructor(position: Int, team: TeamModel, points: Int){
        this.position = position
        this.team = team
        this.points = points
    }

    constructor(){}
}
