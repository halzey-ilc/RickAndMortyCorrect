package com.example.rickandmortycorrect.presentation.character.viewmodel.states

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.rickandmortycorrect.domain.model.CharactersDomain
import com.example.rickandmortycorrect.presentation.favorite.FavoriteState
import com.example.rickandmortycorrect.utils.GenderState
import com.example.rickandmortycorrect.utils.StatusState

data class CharacterActivityState(

    val characterWithDataBinding: PagingData<FavoriteState>? = PagingData.empty(),
    val Filter: Boolean = false,

    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val characterIdFromCharacterListFragment: Int = 1,
    val statusState: StatusState = StatusState.NONE,
    val genderState: GenderState = GenderState.NONE,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val isFilter: Boolean = false,
    val favoriteCharacter: List<CharactersDomain> = emptyList(),
    val showToastMessageEvent: Boolean = false,
    val toastMessage: String = "",
    val listType: ListType = ListType.GridLayout

)

enum class ListType() {
    LinearLayout(),
    GridLayout()
}

