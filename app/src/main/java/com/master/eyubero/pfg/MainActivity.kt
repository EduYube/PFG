package com.master.eyubero.pfg

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        context = applicationContext
        val myRef = database.getReference("teams")
//       for (i in 1..10) {
//           val teamId = i.toString()

//           val team = Team(teamId, "equipo $i", "basket")
//            if (!myRef.child("teams").orderByChild(teamId).equals("$i"))
//                myRef.child(teamId).setValue(team).addOnCompleteListener{
//                    Toast.makeText(this, "se ha registrado el equipo ${team.name} para el deporte ${team.sport}", Toast.LENGTH_LONG).show()
//                }


        for (i in 1..10) {
            val teamId = i.toString()

            val team = Team(teamId, "team $i", "basket")
            myRef.child("$i").addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(context, "Ha habido algún error", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists())
                        myRef.child(teamId).setValue(team).addOnCompleteListener {
                            Toast.makeText(context, "Se ha añadido el equipo ${team.name} al deporte ${team.sport}", Toast.LENGTH_LONG).show()
                        }
                    else
                        Toast.makeText(context, "Ya existe el equipo ${team.name} al deporte ${team.sport}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

}
