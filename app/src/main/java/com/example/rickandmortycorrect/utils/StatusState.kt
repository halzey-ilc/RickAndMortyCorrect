package com.example.rickandmortycorrect.utils

import android.icu.text.CaseMap.Title

enum class StatusState (val title: String) {
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown"),
    NONE("")
}
