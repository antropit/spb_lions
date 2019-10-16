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
import ru.antropit.spblions.ui.details.DetailsFragment
import ru.antropit.spblions.api.model.Entity
import java.util.ArrayList


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    val adapter = MainAdapter(listener = {
                viewModel.onClickItem(it)
                activity?.supportFragmentManager?.beginTransaction()!!
                .replace(ru.antropit.spblions.R.id.container, DetailsFragment.newInstance())
                .addToBackStack(null)
                .commit()
         })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTitle(ru.antropit.spblions.R.string.app_name)
        return inflater.inflate(ru.antropit.spblions.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvLions.setHasFixedSize(true)
        rvLions.layoutManager = LinearLayoutManager(context)
        rvLions.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getMedia().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it as ArrayList<Entity>)
            }
        })

        lifecycle.addObserver(viewModel)
    }
}
