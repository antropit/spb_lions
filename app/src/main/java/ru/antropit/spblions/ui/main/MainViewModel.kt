package ru.antropit.spblions.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import ru.antropit.spblions.api.ApiRepo
import ru.antropit.spblions.api.model.Entity
import ru.antropit.spblions.ui.details.DetailsViewModel

class MainViewModel : ViewModel(), LifecycleObserver {

    fun getMedia() = LiveDataReactiveStreams.fromPublisher(
        ApiRepo.createAdapter().loadPoisQuery("antropit")
            .observeOn(Schedulers.io())
    )

    fun onClickItem(data: Entity) {
        DetailsViewModel.name = data.name
        DetailsViewModel.description = data.description
        DetailsViewModel.address = data.address
        DetailsViewModel.photo = data.photo
        DetailsViewModel.location = LatLng(data.location.lat.toDouble(), data.location.lng.toDouble())
    }


}
