package pa.chan.main_page.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pa.chan.databinding.TrainItemBinding
import pa.chan.main_page.domain.model.TrainModel

class MainAdapter(private val trainModelList: List<TrainModel?>) :
    RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemBinding =
            TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding, parent.context)
    }

    override fun getItemCount(): Int = trainModelList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val trainItem = trainModelList[position]
        holder.bind(trainItem)
    }
}