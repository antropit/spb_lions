package ru.antropit.spblions.ui.details

import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import ru.antropit.spblions.R

class DetailsViewModel : ViewModel(), Observable {
    companion object {
        private val registry = PropertyChangeRegistry()
        @Bindable lateinit var name: String
        @Bindable lateinit var description: String
        @Bindable lateinit var photo: String
        @Bindable lateinit var address: String
        @Bindable lateinit var location: LatLng
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }
}
