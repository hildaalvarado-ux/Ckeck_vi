package com.example.check_vi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class StepFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE = "image"

        fun newInstance(title: String, description: String, imageResId: Int): StepFragment {
            val fragment = StepFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_IMAGE, imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stepfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE)
        val description = arguments?.getString(ARG_DESCRIPTION)
        val imageResId = arguments?.getInt(ARG_IMAGE) ?: 0

        view.findViewById<TextView>(R.id.title_step).text = title
        view.findViewById<TextView>(R.id.description_step).text = description
        view.findViewById<ImageView>(R.id.image_step).setImageResource(imageResId)

        // Botón Omitir
        view.findViewById<Button>(R.id.btn_skip).setOnClickListener {
            startActivity(Intent(requireContext(), inicioActivity2::class.java))
            requireActivity().finish()
        }
    }
}
