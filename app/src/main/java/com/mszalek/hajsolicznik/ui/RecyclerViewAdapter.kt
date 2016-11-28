package com.mszalek.hajsolicznik.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.mszalek.hajsolicznik.R
import com.mszalek.hajsolicznik.data.models.Player
import kotlinx.android.synthetic.main.recycler_item.view.*
import kotlinx.android.synthetic.main.row_player_balance.view.*

import java.util.ArrayList

class RecyclerViewAdapter
//protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

(private val mContext: Context, private val mDataset: List<Player>,
 var funBuyInClick: (View, Player) -> Unit, var funPlayerClick: (View, Player) -> Unit) : RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder>() {

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPlayer(player: Player, funBuyInClick: (View, Player) -> Unit, funPlayerClick: (View, Player) -> Unit) {
            with(player) {
                itemView.tvBalance.text = getBalance().toString()
                itemView.tvName.text = name
                itemView.row_player_balance.setOnClickListener { funPlayerClick(itemView, player) }
                itemView.buyInIcon.setOnClickListener { funBuyInClick(itemView, player)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        view.swipe.showMode = SwipeLayout.ShowMode.LayDown
        view.swipe.isRightSwipeEnabled = false
        view.swipe.addDrag(SwipeLayout.DragEdge.Left, view.swipe_swiped)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SimpleViewHolder, position: Int) {
        viewHolder.bindPlayer(mDataset[position], funBuyInClick, funPlayerClick)
        mItemManger.bindView(viewHolder.itemView, position)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }
}
