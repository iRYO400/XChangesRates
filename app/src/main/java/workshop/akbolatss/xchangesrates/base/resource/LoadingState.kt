package workshop.akbolatss.xchangesrates.base.resource

sealed class LoadingState {
    object Loading : LoadingState()
    object Success : LoadingState()
}
