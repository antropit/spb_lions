package ru.antropit.spblions.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import ru.antropit.spblions.R
import ru.antropit.spblions.ui.details.DetailsFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    val adapter = MainAdapter(listener = {
                viewModel.onClickItem(it)
                activity?.supportFragmentManager?.beginTransaction()!!
                .replace(R.id.container, DetailsFragment.newInstance())
                .addToBackStack(null)
                .commit()
         })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTitle(R.string.app_name)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvLions.layoutManager = LinearLayoutManager(context)
        rvLions.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getMedia().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                MainAdapter.fullList = it.toMutableList()
            }
        })

        lifecycle.addObserver(viewModel)
    }
}
