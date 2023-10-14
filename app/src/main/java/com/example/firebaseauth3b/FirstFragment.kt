package com.example.firebaseauth3b

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebaseauth3b.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null) {
            // automatic login
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            // TODO if empty ...
            val password = binding.edittextPassword.text.trim().toString()
            // TODO if empty ...

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(binding.layoutOuter, "Welcome $email", Snackbar.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        binding.textviewMessage.text = task.exception?.message
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}