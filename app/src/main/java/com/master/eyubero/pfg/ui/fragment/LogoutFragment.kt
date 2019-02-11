package com.master.eyubero.pfg.ui.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.master.eyubero.pfg.R
import com.master.eyubero.pfg.databinding.FragmentLogoutBinding
import com.master.eyubero.pfg.ui.activities.MainActivity


/**
 * Created by Edu Yube ┌(▀Ĺ̯ ▀-͠ )┐
 * on 06/02/2019(ノಠ益ಠ)ノ
 */
class LogoutFragment : Fragment() {

    lateinit var mBinding: FragmentLogoutBinding
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_logout, container, false)

        activity!!.title = this.javaClass.simpleName.substringBefore("Fragment")

        mBinding.tvUserEmail.text = mAuth.currentUser!!.email
        mBinding.btLogout.setOnClickListener {
            mAuth.signOut()
            returnToMain()
        }

        return mBinding.root
    }

    private fun returnToMain() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogoutFragment()
    }
}