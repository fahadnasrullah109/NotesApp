package com.example.journel.app.presentation.note.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journel.app.R
import com.example.journel.app.data.local.models.Mood
import com.example.journel.app.data.local.models.NotesListItemType
import com.example.journel.app.databinding.ItemDayHeaderBinding
import com.example.journel.app.databinding.ItemMonthHeaderBinding
import com.example.journel.app.databinding.ItemNoteBinding
import com.example.journel.app.base.BaseRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter : BaseRecyclerViewAdapter<RecyclerView.ViewHolder, NotesListItemType>() {

    private val ITEM_TYPE_MONTH = 0
    private val ITEM_TYPE_DAY = 1
    private val ITEM_TYPE_CONTENT = 2


    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is NotesListItemType.MonthHeader -> ITEM_TYPE_MONTH
        is NotesListItemType.DayHeader -> ITEM_TYPE_DAY
        is NotesListItemType.Content -> ITEM_TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_MONTH -> return MonthViewHolder(
                ItemMonthHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_TYPE_DAY -> return DayViewHolder(
                ItemDayHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> return ContentViewHolder(
                ItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_MONTH -> (holder as MonthViewHolder).bind(getItem(position) as NotesListItemType.MonthHeader)
            ITEM_TYPE_DAY -> (holder as DayViewHolder).bind(getItem(position) as NotesListItemType.DayHeader)
            else -> (holder as ContentViewHolder).bind(getItem(position) as NotesListItemType.Content)
        }
    }

    inner class ContentViewHolder(private val itemBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: NotesListItemType.Content) {
            itemBinding.apply {
                noteTv.text = item.data.text
                timeTv.text = getFormattedTime(item.data.date)
                moodView.setBackgroundResource(
                    getBackGroundColorForMood(
                        item.data.mood
                    )
                )
            }
        }

        private fun getBackGroundColorForMood(mood: Mood) = when (mood) {
            Mood.HAPPY -> R.color.mood_color_happy_selected
            Mood.NEUTRAL -> R.color.mood_color_neutral_selected
            Mood.SAD -> R.color.mood_color_sad_selected
            Mood.NOTHING -> R.color.mood_color_happy_selected
        }

        @SuppressLint("SimpleDateFormat")
        private fun getFormattedTime(time: Date): String {
            val calendar = Calendar.getInstance()
            calendar.time = time
            return SimpleDateFormat("HH:mm").format(calendar.time)
        }
    }

    inner class MonthViewHolder(private val itemBinding: ItemMonthHeaderBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: NotesListItemType.MonthHeader) {
            itemBinding.apply {
                titleTv.text = item.title
                val count = itemBinding.root.context.resources.getQuantityString(
                    R.plurals.numberOfEntries, item.count, item.count
                )
                countTv.text = count
                parentView.setBackgroundColor(item.bgColor)
            }
        }
    }

    inner class DayViewHolder(private val itemBinding: ItemDayHeaderBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: NotesListItemType.DayHeader) {
            itemBinding.apply {
                titleTv.text = item.title
                val count = itemBinding.root.context.resources.getQuantityString(
                    R.plurals.numberOfEntries, item.count, item.count
                )
                countTv.text = count
            }
        }
    }
}