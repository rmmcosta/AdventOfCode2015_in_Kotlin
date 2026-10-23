package org.example

/*
Santa's previous password expired, and he needs help choosing a new one.

To help him remember his new password after the old one expires, 
Santa has devised a method of coming up with a password based on the previous one.
Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons),
so he finds his new password by incrementing his old password string repeatedly until it is valid.

Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; 
if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around.

Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:

Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. 
They cannot skip letters; abd doesn't count.
Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
For example:

hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
abbcegjk fails the third requirement, because it only has one double letter (bb).
The next password after abcdefgh is abcdffaa.
The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.
Given Santa's current password (your puzzle input), what should his next password be?

Your puzzle input is cqjxjnds.
 */

//passwords must be exactly eight lowercase letters
//new password by incrementing his old password string repeatedly until it is valid
//xx, xy, xz, ya, yb, and so on
//if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around
//Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. 
//They cannot skip letters; abd doesn't count.
//Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
//Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
fun ex11GenerateNewPassword(previousPassword: String): String {
    var newPassword = previousPassword
   
    do {
        val currentLetter = previousPassword[7]
        newPassword = nextPassword(newPassword, currentLetter)
    } while (!ex11IsPasswordValid(newPassword))
    
    println("The new password is: $newPassword for the old $previousPassword")

    return newPassword
}

private fun getNextValidLetter(currentLetter: Char): Char {
    var newLetter = if (currentLetter == 'z')
        'a'
    else
        Char(currentLetter.code + 1)
    while (newLetter in charArrayOf('i', 'o', 'l'))
        newLetter = Char(newLetter.code + 1)
    return newLetter
}

private fun nextPassword(password: String, nextChar: Char): String {
    val newPasswordArray = password.toCharArray()
    var iterIndex = 7

    while (iterIndex >= 0 && newPasswordArray[iterIndex] == 'z') {
        newPasswordArray[iterIndex] = getNextValidLetter(newPasswordArray[iterIndex])
        iterIndex--
    }
    newPasswordArray[iterIndex] = getNextValidLetter(newPasswordArray[iterIndex])

    return newPasswordArray.joinToString("")
}

fun ex11IsPasswordValid(password: String): Boolean = validPasswordLength(password)
        && validLetters(password)
        && validPairs(password)
        && validIncreasingStraight(password)

private fun validPairs(password: String): Boolean {
    val pairs: MutableSet<Char> = mutableSetOf()
    var currentCount = 0
    var previousChar: Char? = null
    password.forEachIndexed { index, char ->
        if (pairs.size > 1) return true
        if (char != previousChar) {
            if (previousChar != null && currentCount == 2)
                pairs.add(previousChar)
            previousChar = char
            currentCount = 0
        }
        currentCount++
        if (index == password.length - 1 && currentCount == 2)
            pairs.add(char)
    }
    return pairs.size > 1
}

private fun validIncreasingStraight(password: String): Boolean {
    var previousChar: Char? = null
    var straightCount = 0
    password.forEach { char ->
        if (previousChar == null || char.code == previousChar.code + 1) {
            straightCount++
            if (straightCount == 3) return true
        } else {
            straightCount = 1
        }
        previousChar = char
    }
    return false
}

private fun validLetters(password: String): Boolean = !password.contains(Regex("[iol]"))

private fun validPasswordLength(password: String): Boolean = password.length == 8