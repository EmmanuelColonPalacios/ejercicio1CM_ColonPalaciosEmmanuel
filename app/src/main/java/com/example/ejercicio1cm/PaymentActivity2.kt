package com.example.ejercicio1cm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ejercicio1cm.ui.theme.Ejercicio1CMTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.widget.TextView
import java.text.DecimalFormat

class PaymentActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val cardHolderName = findViewById<EditText>(R.id.cardHolderName)
        val cardNumber = findViewById<EditText>(R.id.cardNumber)
        val cardType = findViewById<Spinner>(R.id.cardType)
        val expiryDate = findViewById<EditText>(R.id.expiryDate)
        val cvv = findViewById<EditText>(R.id.cvv)
        val email = findViewById<EditText>(R.id.email)
        val payButton = findViewById<Button>(R.id.payButton)
        val amountToPayText = findViewById<TextView>(R.id.amountToPayText)

        // Generar un monto aleatorio entre $100.00 y $5,000.00
        val randomAmount = Random().nextInt(490000) + 10000
        val amountToPay = randomAmount / 100.0 // Convertir a decimal

        // Formatear el monto para mostrar dos decimales
        val decimalFormat = DecimalFormat("#,##0.00")
        val formattedAmount = decimalFormat.format(amountToPay)

        // Mostrar el monto en la etiqueta de texto
        amountToPayText.text = getString(R.string.amount_to_pay_label, formattedAmount)

        // Configurar TextWatcher para cada EditText
        val textWatchers = listOf(cardHolderName, cardNumber, expiryDate, cvv, email)
        for (editText in textWatchers) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // Remover saltos de línea si existen
                    s?.let {
                        val index = it.indexOf("\n")
                        if (index != -1) {
                            s.replace(index, index + 1, "")
                        }
                    }
                }
            })
        }

        // Ocultar el teclado cuando se presiona Enter
        val editTexts = arrayOf(cardHolderName, cardNumber, expiryDate, cvv, email)
        for (editText in editTexts) {
            editText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Ocultar el teclado
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                false
            }
        }

        payButton.setOnClickListener {
            // Validar los datos ingresados
            val cardHolderNameText = cardHolderName.text.toString().trim()
            val cardHolderNames = cardHolderNameText.split(" ")

            val isCardHolderNameValid = cardHolderNames.size >= 2 && cardHolderNames.size <= 4
            val isCardNumberValid = cardNumber.text.toString().length == 16
            val isExpiryDateValid = validateExpiryDate(expiryDate.text.toString())
            val isCvvValid = cvv.text.toString().length in 3..4
            val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()

            if (!isCardHolderNameValid) {
                cardHolderName.error = getString(R.string.error_card_holder_name)
            }
            if (!isCardNumberValid) {
                cardNumber.error = getString(R.string.error_card_number)
            }
            if (!isExpiryDateValid) {
                expiryDate.error = getString(R.string.error_expiry_date)
            }
            if (!isCvvValid) {
                cvv.error = getString(R.string.error_cvv)
            }
            if (!isEmailValid) {
                email.error = getString(R.string.error_email)
            }

            if (isCardHolderNameValid && isCardNumberValid && isExpiryDateValid && isCvvValid && isEmailValid) {
                // Proceder con el "pago"
                val intent = Intent(this, PaymentResultActivity2::class.java)
                // Pasar algunos datos al PaymentResultActivity
                intent.putExtra("cardHolderName", cardHolderNameText)
                intent.putExtra("cardType", cardType.selectedItem.toString())
                intent.putExtra("email", email.text.toString())
                // Iniciar PaymentResultActivity
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_invalid_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateExpiryDate(date: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("MM/yy", Locale.getDefault())
            dateFormat.isLenient = false
            val parsedDate = dateFormat.parse(date)
            parsedDate != null && parsedDate.after(Date())
        } catch (e: ParseException) {
            false
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}