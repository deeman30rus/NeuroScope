package com.theroom101.core.mvp

import com.theroom101.core.assertions.Asserts

interface MvpView

abstract class MvpPresenter<TView: MvpView> {

    private var _view: TView? = null
    val view: TView
        get() = _view ?: error("Attempt to access to detached view")

    fun attachView(view: TView) {
        Asserts.assertNull(_view) { "View is already attached"}
        _view = view
    }

    fun detachView() {
        _view = null
    }
}

class MvpFeature<TView: MvpView>(
    private val presenter: MvpPresenter<TView>,
    private val view: TView
) {

    fun combine() {
        presenter.attachView(view)
    }

    fun release() {
        presenter.detachView()
    }
}