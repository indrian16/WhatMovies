package io.indrian.moviecatalogue.ui.base

import androidx.fragment.app.Fragment
import io.indrian.moviecatalogue.MovieApp
import io.indrian.moviecatalogue.di.AppComponent

abstract class BaseFragment : Fragment() {

    protected fun appComponent(): AppComponent {

        return (activity?.application as MovieApp).appComponent
    }
}