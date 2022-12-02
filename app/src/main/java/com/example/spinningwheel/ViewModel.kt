package com.example.spinningwheel

import android.provider.UserDictionary.Words
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel(){


    fun randomWord(category : String): String {
        var words = listOf(
            GameData.GameData.colorNamesList,
            GameData.GameData.mcDonaldsBurgerList,
            GameData.GameData.sportsList
        )

        var word = ""
        for (i in words.indices){
            if(words[i][0] == category)
                word = words[i][(Math.random() * (words[i].size-1)+1).toInt()]
        }

        return word
    }


    fun randomCategory(): String {

        var category = listOf("Colours", "McDonald's Burgers", "Sports")
        return category[(Math.random() * 3).toInt()]

    }

    fun setupWord(word: String): String {
        var cur = ""
        var wordLength = 0

        wordLength = word.toCharArray().size

        for(i in 0 until wordLength) {
            cur += "_"
        }

        return cur
    }


    fun checkWord(word: String, text : Char, cur : String): String{

        var tempCur = StringBuilder(cur)

        for (i in 0 until word.toCharArray().size){
            if (text == word.toCharArray()[i]){
                tempCur.setCharAt(i,text)
            }
        }
        return tempCur.toString()
    }

    fun pointSystem() : Int {
        var points = GameData.Points.Points
        return points[(Math.random() * 6).toInt()]
    }

    fun updatePoint(word: String, text : Char, points : Int): Int{
        var counter = 0
        for (i in 0 until word.toCharArray().size){
            if (text == word.toCharArray()[i]){
                counter++
            }
        }
        return counter * points
    }
}
