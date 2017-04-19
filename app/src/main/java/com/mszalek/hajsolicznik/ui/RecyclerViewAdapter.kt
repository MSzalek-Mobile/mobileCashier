package com.mszalek.hajsolicznik.ui

import android.content.Context
import android.support.transition.TransitionManager
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.row_player_main.view.*

class RecyclerViewAdapter
(private val mContext: Context,
 private val mDataset: List<Player>,
 var funBuyInClick: (View, Player) -> Unit,
 var funCheckoutClick: (View, Player) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.SimpleViewHolder>() {

    var expandedPosition = -1

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPlayer(player: Player) {
            val isExpanded = expandedPosition == adapterPosition
            setDetailVisibility(isExpanded)

            itemView.setOnClickListener {
                collapseExpandedView()
                expandedPosition = if (isExpanded) -1 else adapterPosition
                TransitionManager.beginDelayedTransition(itemView as CardView)
                notifyItemChanged(adapterPosition)
            }

            initActionButtons(player)
            populatePlayerData(player)

        }

        private fun initActionButtons(player: Player) {
            val hasCheckedOut = player.checkout != null
            itemView.btnBuyIn.isEnabled = !hasCheckedOut
            itemView.btnCheckout.isEnabled = !hasCheckedOut
            if (!hasCheckedOut) {
                itemView.btnBuyIn.setOnClickListener { funBuyInClick(it, player) }
                itemView.btnCheckout.setOnClickListener { funCheckoutClick(it, player) }
            }
        }

        private fun setDetailVisibility(isExpanded: Boolean) {
            itemView.detail_view.visibility = if (isExpanded) View.VISIBLE else View.GONE
            itemView.isActivated = isExpanded
            itemView.item_description_expand_img.isActivated = isExpanded
        }

        private fun populatePlayerData(player: Player) {
            with(player) {
                itemView.tvBalance.text = mContext.getString(R.string.row_player_balance, getBalance())
                itemView.tvName.text = name
                itemView.tvCashBuyIns.text = mContext.getString(R.string.row_player_cash_buy_ins, getSumOfCashBuyIns())
                itemView.tvDebtBuyIns.text = mContext.getString(R.string.row_player_debt_buy_ins, getSumOfDebtBuyIns())
                if (checkout == null) {
                    itemView.tvCheckout.visibility = GONE
                } else {
                    itemView.tvCheckout.visibility = VISIBLE
                    itemView.tvCheckout.text = mContext.getString(R.string.row_player_checkout, checkout)
                }
            }
        }
    }

    fun collapseExpandedView() {
        if (expandedPosition != -1) {
            val oldExpandedPosition = expandedPosition
            expandedPosition = -1
            notifyItemChanged(oldExpandedPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_player_main, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SimpleViewHolder, position: Int) {
        viewHolder.bindPlayer(mDataset[position])
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}
