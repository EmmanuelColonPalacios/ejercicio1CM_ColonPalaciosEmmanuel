// PaymentActivity.kt
package com.example.ejercicio1cm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio1cm.R.id.expiryDate
import android.widget.Toast


class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val cardHolderName = findViewById<EditText>(R.id.cardHolderName)
        val cardNumber = findViewById<EditText>(R.id.cardNumber)
        val cardType = findViewById<Spinner>(R.id.cardType)
        val expiryDate = findViewById<EditText>(expiryDate)
        val cvv = findViewById<EditText>(R.id.cvv)
        val email = findViewById<EditText>(R.id.email)
        val payButton = findViewById<Button>(R.id.payButton)

        payButton.setOnClickListener {
            // Validar los datos ingresados
            if (cardHolderName.text.toString().isEmpty() ||
                cardNumber.text.toString().length != 16 ||
                expiryDate.text.toString().isEmpty() ||
                cvv.text.toString().length !in 3..4 ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
            ) {
                // Mostrar un mensaje de error si los datos no son válidos
                Toast.makeText(this, "Por favor, ingresa datos válidos", Toast.LENGTH_SHORT).show()
            } else {
                // Proceder con el pago
                val intent = Intent(this, PaymentResultActivity::class.java)
                // Pasar algunos datos al PaymentResultActivity2
                intent.putExtra("cardHolderName", cardHolderName.text.toString())
                intent.putExtra("cardType", cardType.selectedItem.toString())
                intent.putExtra("email", email.text.toString())
                // Iniciar PaymentResultActivity2
                startActivity(intent)
            }
        }
    }
}
