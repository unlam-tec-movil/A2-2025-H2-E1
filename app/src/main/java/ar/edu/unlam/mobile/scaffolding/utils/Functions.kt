package ar.edu.unlam.mobile.scaffolding.utils

import ar.edu.unlam.mobile.scaffolding.ui.screens.ValidationResult

fun validateForm(
    email: String,
    password: String
): ValidationResult {
    if (!email.contains("@")) {
        return ValidationResult(
            isValid = false,
            message = "Email must be valid.",
        )
    }
    if (password.isEmpty()) {
        return ValidationResult(
            isValid = false,
            message = "Password cannot be empty.",
        )
    }
    if (password.length < 6) {
        return ValidationResult(
            isValid = false,
            message = "Password must be at least 6 characters long.",
        )
    }

    return ValidationResult(
        isValid = true,
        message = "Valid Form 😎",
    )
}
