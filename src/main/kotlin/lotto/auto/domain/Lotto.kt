package lotto.auto.domain

import lotto.auto.port.NumberGenerator
import lotto.auto.vo.LottoScore

class Lotto(private val numbers: List<Int>) {

    init {
        require(numbers.distinct().count() == LOTTO_NUMBER_LENGTH)
        require(numbers.all(::isValidNumber))
    }

    fun match(other: Lotto): LottoScore = LottoScore.matchCountOf(countingMatchNumber(other))

    private fun countingMatchNumber(other: Lotto): Int = numbers.count(other.numbers::contains)

    private fun isValidNumber(number: Int) = number in MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER

    companion object {

        const val LOTTO_NUMBER_LENGTH = 6
        const val MIN_LOTTO_NUMBER = 1
        const val MAX_LOTTO_NUMBER = 45

        fun createRandomNumbers(numberGenerator: NumberGenerator): Lotto {
            val numbers = mutableListOf<Int>()
            while (numbers.size != LOTTO_NUMBER_LENGTH) {
                val newNumber = numberGenerator.getNumber()
                if (numbers.contains(newNumber)) continue
                numbers.add(newNumber)
            }
            return Lotto(numbers)
        }
    }
}
