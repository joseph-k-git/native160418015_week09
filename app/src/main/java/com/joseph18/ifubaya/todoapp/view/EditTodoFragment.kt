package com.joseph18.ifubaya.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.joseph18.ifubaya.todoapp.R
import com.joseph18.ifubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditTodoFragment : Fragment() {

    private lateinit var viewModel :DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtJudulTodo.text = "Edit ToDo"
        btnCreateTodo.text = "Save Changes"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid

        btnCreateTodo.setOnClickListener() {
            val selectedRadioButton = view.findViewById<RadioButton>(radioGroupPirority.checkedRadioButtonId)
            val priorityLevel = Integer.parseInt(selectedRadioButton.tag.toString())

            viewModel.updateTodo(txtTodoTitle.text.toString(), txtTodoNotes.text.toString(), priorityLevel, uuid = uuid)

            Toast.makeText(it.context, "ToDo Edited", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        viewModel.fetchTodo(uuid)

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            txtTodoTitle.setText(it.title)
            txtTodoNotes.setText(it.notes)
            checkRadioButtonByPriority(it.priorityLevel)
        })
    }

    fun checkRadioButtonByPriority(priorityLevel :Int) {
        (radioGroupPirority.children).forEach {
            val radioButton = it as RadioButton
            with (radioButton) {
                this.isChecked = radioButton.tag.toString() == priorityLevel.toString()
            }
        }
    }
}