package com.monsieur.cloy.cryptostate.ui.prices

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.monsieur.cloy.cryptostate.R
import com.monsieur.cloy.cryptostate.model.Prices.Price
import com.monsieur.cloy.cryptostate.utilits.APP_ACTIVITY
import com.monsieur.cloy.cryptostate.viewModels.MainViewModel

class PricesRecyclerAdapter() : RecyclerView.Adapter<PricesRecyclerAdapter.ViewHolder>() {

    private var prices: List<Price>? = null
    val viewModel = ViewModelProvider(APP_ACTIVITY).get(MainViewModel::class.java)

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(prices : List<Price>){
        this.prices = prices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prices_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(prices != null && prices!!.isNotEmpty()){
            val price = prices!![position]
            holder.symbol.text = price.symbol
            holder.eurPrice.text = price.priceEUR.toString()
            holder.rubPrice.text = price.priceRUB.toString()
            holder.uahPrice.text = price.priceUAH.toString()
            holder.usdPrice.text = price.priceUSD.toString()
            holder.url.text = price.url
            holder.symbolName.text = price.symbolName
            holder.mainPrice.text = price.getMainPrice().toString()
            if(price.isDefaultFiatPrice){
                holder.delete.visibility = View.GONE
                holder.url.visibility = View.GONE
            }else{
                holder.delete.visibility = View.VISIBLE
                holder.url.visibility = View.VISIBLE
            }
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
                viewModel.removePrice(prices!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if(prices != null){
            prices!!.size
        } else{
            0
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var symbol: TextView = itemView.findViewById(R.id.symbol)
        var symbolName: TextView = itemView.findViewById(R.id.symbolName)
        var url: TextView = itemView.findViewById(R.id.url)
        var mainPrice: TextView = itemView.findViewById(R.id.mainPrice)
        var rubPrice: TextView = itemView.findViewById(R.id.price_rub)
        var usdPrice: TextView = itemView.findViewById(R.id.price_usd)
        var eurPrice: TextView = itemView.findViewById(R.id.price_eur)
        var uahPrice: TextView = itemView.findViewById(R.id.price_uah)
        var card: CardView = itemView.findViewById(R.id.card)
        var delete: Button = itemView.findViewById(R.id.delete)
        var hiddenLayout: LinearLayout = itemView.findViewById(R.id.hidden_layout)
        var isOpen = false
    }
}