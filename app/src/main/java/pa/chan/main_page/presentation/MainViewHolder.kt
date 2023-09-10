package pa.chan.main_page.presentation

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import pa.chan.databinding.TrainItemBinding
import pa.chan.main_page.domain.model.TrainModel


class MainViewHolder(
    private val trainItemBinding: TrainItemBinding,
    private val context: Context
): RecyclerView.ViewHolder(trainItemBinding.root) {


    fun bind(trainModel: TrainModel?) {
        with(trainItemBinding) {
            this.warmUpText.text = trainModel?.warmUp?.let { getString(context, it) }

        }
    }


}