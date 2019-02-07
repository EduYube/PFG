package com.master.eyubero.pfg.ui.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentLoginBinding
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.master.eyubero.pfg.ui.activities.MainActivity


class LoginFragment : Fragment() {

    lateinit var mBinding: FragmentLoginBinding
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")
        mBinding.btLogin.setOnClickListener { it ->
            mAuth.signInWithEmailAndPassword(mBinding.tietUser.text.toString(), mBinding.tietPass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("succed <3", "signInWithEmail:success")
                            val user = mAuth.currentUser

                            Toast.makeText(context, "${user!!.email} logado correctamente", Toast.LENGTH_SHORT).show()
                            returnToMain()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("error <3", "signInWithEmail:failure", it.exception)
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
        }

        return mBinding.root
    }

    private fun returnToMain() {
        val intent = Intent(context,MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
