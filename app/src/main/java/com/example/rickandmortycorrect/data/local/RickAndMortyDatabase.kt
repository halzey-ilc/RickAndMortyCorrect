package com.example.rickandmortycorrect.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortycorrect.domain.model.CharactersDomain

@Database(entities = [CharactersDomain:: class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase(){
    abstract val rickAndMortyDao: RickAndMortyDao
}