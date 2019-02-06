package com.master.eyubero.pfg.model


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 06/02/2019(ノಠ益ಠ)ノ
 */
class MatchModel {


    var local: String? = null
    var away: String? = null
    var score: String? = null

    constructor(local: String, away: String, score: String){
        this.local = local
        this.away = away
        this.score = score
    }

    constructor(){}
}