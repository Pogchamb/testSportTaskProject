package pa.chan.main_page.presentation

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import pa.chan.databinding.TrainItemBinding
import pa.chan.main_page.domain.model.TrainModel


class MainViewHolder(
    private val trainItemBinding: TrainItemBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(trainItemBinding.root) {


    fun bind(trainModel: TrainModel?) {
        with(trainItemBinding) {
            this.warmUpText.text = trainModel?.warmUp?.let { context.getString(it) }
            this.efficiencyText.text = trainModel?.efficiency?.let { context.getString(it) }
            this.technicText.text = trainModel?.technic?.let { context.getString(it) }
            this.hitchText.text = trainModel?.hitch?.let { context.getString(it) }
            this.trainName.text = trainModel?.name?.let { context.getString(it) }
        }
    }


}