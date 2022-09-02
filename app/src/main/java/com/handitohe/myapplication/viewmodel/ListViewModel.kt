package com.handitohe.myapplication.viewmodel

import com.handitohe.myapplication.model.DataItemWithDate
import com.handitohe.myapplication.repository.BebasBayarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed class ListState(open val list: List<DataItemWithDate>) {
    object InitialLoad : ListState(emptyList())
    data class Loaded(override val list: List<DataItemWithDate>) : ListState(list)
    data class LoadFailed(override val list: List<DataItemWithDate>, val message: String) : ListState(list)
    data class Refreshing(override val list: List<DataItemWithDate>) : ListState(list)
    data class RefreshFailed(override val list: List<DataItemWithDate>, val message: String) : ListState(list)
}

sealed class ListEvent {
    object Refresh : ListEvent()
}

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: BebasBayarRepository,
) : BaseViewModel<ListState, ListEvent>(initialState = ListState.InitialLoad) {

    init {
        loadDataFlow().onStart { emit(ListState.InitialLoad) }
            .catch { emit(ListState.LoadFailed(emptyList(), it.message ?: "Unknown Error")) }
    }

    @FlowPreview
    override suspend fun mapEventToState(event: ListEvent): Flow<ListState> {
        val prevList = state.value.list
        return when (event) {
            ListEvent.Refresh -> loadDataFlow()
                .onStart { emit(ListState.Refreshing(prevList)) }
                .catch { emit(ListState.RefreshFailed(prevList, it.message ?: "Unknown Error")) }
        }
    }

    private fun loadDataFlow(): Flow<ListState> = repository.getData()
        .map<List<DataItemWithDate>, ListState> { ListState.Loaded(it) }
}
