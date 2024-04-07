package com.example.ejercicio1cm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class PaymentResultActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_result)

        val paymentResultMessage = findViewById<TextView>(R.id.paymentResultMessage)
        val paymentInformation = findViewById<TextView>(R.id.paymentInformation)
        val tryAgainButton = findViewById<Button>(R.id.tryAgainButton)

        // Obtener los datos pasados desde PaymentActivity2
        val cardHolderName = intent.getStringExtra("cardHolderName")
        val cardType = intent.getStringExtra("cardType")
        val email = intent.getStringExtra("email")

        // Generar un resultado aleatorio de la operación
        val isSuccess = (Math.random() < 0.75)

        // Mostrar el resultado de la operación y la información del pago
        if (isSuccess) {
            paymentResultMessage.text = getString(R.string.payment_successful_message)
            paymentInformation.text = getString(R.string.payment_information, cardHolderName, cardType, email)
            paymentInformation.visibility = View.VISIBLE
        } else {
            paymentResultMessage.text = getString(R.string.payment_unsuccessful_message)
        }

        // Configurar el OnClickListener para el botón "Pago nuevo"
        tryAgainButton.setOnClickListener {
            // Crear un Intent para volver a PaymentActivity2
            val intent = Intent(this, PaymentActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}

