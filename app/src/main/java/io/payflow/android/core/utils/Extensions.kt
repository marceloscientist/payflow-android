fun String.isValidEmail(): Boolean {

    return contains("@")
            && substringBefore("@").length >= 2
            && substringAfter("@").isNotBlank()
}