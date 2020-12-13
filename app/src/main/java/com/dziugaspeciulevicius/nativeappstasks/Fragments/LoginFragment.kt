package com.dziugaspeciulevicius.nativeappstasks.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dziugaspeciulevicius.nativeappstasks.Models.Item
import com.dziugaspeciulevicius.nativeappstasks.ViewModels.ItemViewModel
import com.dziugaspeciulevicius.nativeappstasks.ViewModels.ShoppingCartViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.*

const val RC_SIGN_IN = 123
class LoginFragment : Fragment() {

    lateinit var shoppingCartViewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Choose authentication providers

        //
        shoppingCartViewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
// Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(context, "Success!!!", Toast.LENGTH_LONG).show()

//                shoppingCartViewModel.addToCart(Item(Random().nextLong(),"Women Jeans", 29.99, "https://image.shutterstock.com/image-photo/3-shot-woman-blue-jeans-260nw-690960433.jpg"))


                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

}