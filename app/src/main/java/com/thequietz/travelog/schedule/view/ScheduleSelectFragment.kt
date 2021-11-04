package com.thequietz.travelog.schedule.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thequietz.travelog.R
import com.thequietz.travelog.databinding.FragmentScheduleSelectBinding
import com.thequietz.travelog.schedule.viewmodel.ScheduleSelectViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class ScheduleSelectFragment : Fragment() {
    private lateinit var binding: FragmentScheduleSelectBinding
    private val scheduleSelectViewModel by viewModels<ScheduleSelectViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_select, container, false)
        initEditText()
        initDatePicker()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = scheduleSelectViewModel
    }

    private fun initEditText() {
        binding.etTravelName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                scheduleSelectViewModel.setTravelName(p0.toString())
            }
        })
    }

    private fun initDatePicker() {
        val start = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        val end = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        end.add(Calendar.MONTH, 12)
        binding.calendar.apply {
            setRangeDate(start.time, end.time)
        }
        binding.calendar.setOnRangeSelectedListener { startDate, endDate, _, _ ->
            scheduleSelectViewModel.setScheduleRange(startDate, endDate)
        }
        binding.calendar.setOnStartSelectedListener { startDate, _ ->
            scheduleSelectViewModel.setScheduleRange(startDate, startDate)
        }
    }
}
