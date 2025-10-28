package ar.edu.unlam.mobile.scaffolding.utils

import ar.edu.unlam.mobile.scaffolding.ui.screens.ValidationResult

fun validateForm(
    name: String,
    email: String,
): ValidationResult {
    if (name.isEmpty()) {
        return ValidationResult(
            isValid = false,
            message = "El nombre no puede estar vacío",
        )
    }

    if (!email.contains("@")) {
        return ValidationResult(
            isValid = false,
            message = "El email debe ser válido",
        )
    }
    return ValidationResult(
        isValid = true,
        message = "Formulario válido 😎",
    )

}

