package fusion.neo.androidkotlinneo.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.model.DataHome
import kotlinx.android.synthetic.main.adapter_item_main.view.*


/**
 * Created by farhan on 6/30/17.
 */

class MainAdapter(private val context: Context, private val arrayData: ArrayList<DataHome>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val dataList: DataHome? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_item_main, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MainAdapter.ViewHolder, i: Int) {
        viewHolder.itemView.textViewAdapter!!.text = arrayData[i].summary
        Picasso.with(context).load(arrayData[i].image).into(viewHolder.itemView.imageViewAdapter)
        viewHolder.itemView.linearAdapter!!.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return arrayData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.textViewAdapter
            itemView.imageViewAdapter
            itemView.linearAdapter
        }
    }

}

