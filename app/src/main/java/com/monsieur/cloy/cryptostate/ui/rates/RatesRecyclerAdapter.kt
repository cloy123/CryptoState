package com.monsieur.cloy.cryptostate.ui.rates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Rates.Rates

class RatesRecyclerAdapter(context: Context) : RecyclerView.Adapter<RatesRecyclerAdapter.ViewHolder>() {

    private var items: Rates? = null


        fun getSize() : Int {
            return if(items != null){
                items!!.items.size
            } else{
                0
            }
    }
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }

    fun setItems(items : Rates){
        this.items = items
        notifyDataSetChanged()
    }


    private val context: Context = context


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var symbol: TextView = itemView.findViewById(R.id.symbol)
        var mainRate: TextView = itemView.findViewById(R.id.mainRate)
        var rubRate: TextView = itemView.findViewById(R.id.rate_rub)
        var usdRate: TextView = itemView.findViewById(R.id.rate_usd)
        var eurRate: TextView = itemView.findViewById(R.id.rate_eur)
        var uahRate: TextView = itemView.findViewById(R.id.rate_uah)
        var card: CardView = itemView.findViewById(R.id.card)
        var delete: TextView = itemView.findViewById(R.id.delete)
        var hiddenLayout: LinearLayout = itemView.findViewById(R.id.hidden_layout)
        var isOpen = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rates_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items != null && items!!.items.size > 0){
            val item = items!!.items[position]
            holder.symbol.text = item.Symbol
            holder.eurRate.text = item.EURRate.toString()
            holder.rubRate.text = item.RUBRate.toString()
            holder.uahRate.text = item.UAHRate.toString()
            holder.usdRate.text = item.USDRate.toString()
            holder.mainRate.text = item.getMainRate().toString()
            holder.card.setOnClickListener {
                if(holder.isOpen){
                    holder.hiddenLayout.visibility = View.GONE
                    holder.isOpen = false
                } else{
                    holder.hiddenLayout.visibility = View.VISIBLE
                    holder.isOpen = true
                }
            }
            holder.delete.setOnClickListener {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
                //TODO("сделать удаление курса")
            }
        }
    }

    override fun getItemCount(): Int {
        return if(items != null){
            items!!.items.size
        } else{
            0
        }
    }
}