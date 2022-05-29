package lotto.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lotto.infra.port.NumberGenerator
import lotto.vo.LottoScore

internal class LottoTest : BehaviorSpec({

    given("로또의 번호가") {
        val lottery = Lottery(listOf(1, 2, 3, 4, 5, 6))

        var lastWeekLottery = Lottery(listOf(1, 7, 8, 9, 10, 11))
        `when`("1개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("꽝") {
                result shouldBe LottoScore.NONE
            }
        }

        lastWeekLottery = Lottery(listOf(1, 2, 8, 9, 10, 11))
        `when`("2개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("꽝") {
                result shouldBe LottoScore.NONE
            }
        }

        lastWeekLottery = Lottery(listOf(1, 2, 3, 9, 10, 11))
        `when`("3개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("4등") {
                result shouldBe LottoScore.FOUR_PLACE
            }
        }

        lastWeekLottery = Lottery(listOf(1, 2, 3, 4, 10, 11))
        `when`("4개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("3등") {
                result shouldBe LottoScore.THIRD_PLACE
            }
        }

        lastWeekLottery = Lottery(listOf(1, 2, 3, 4, 5, 11))
        `when`("5개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("2등") {
                result shouldBe LottoScore.TWO_PLACE
            }
        }

        lastWeekLottery = Lottery(listOf(1, 2, 3, 4, 5, 6))
        `when`("6개 맞을 경우") {
            val result = lottery.match(lastWeekLottery)
            then("1등") {
                result shouldBe LottoScore.ONE_PLACE
            }
        }
    }

    given("로또 생성시") {
        `when`("중복된 숫자가 포함된 경우") {
            val throwableAction = {
                Lottery(listOf(1, 1, 2, 3, 4, 5))
            }
            then("IllegalArgumentException 예외를 던진다.") {
                shouldThrow<IllegalArgumentException>(throwableAction)
            }
        }

        `when`("6개의 번호가 아닌 경우") {
            val throwableAction = {
                Lottery(listOf(1, 2, 3, 4, 5))
            }
            then("IllegalArgumentException 예외를 던진다.") {
                shouldThrow<IllegalArgumentException>(throwableAction)
            }
        }

        `when`("1~45 범위 외의 숫자가 포함된 경우") {
            val throwableAction = {
                Lottery(listOf(1, 2, 3, 46, 5, 6))
            }
            then("IllegalArgumentException 예외를 던진다.") {
                shouldThrow<IllegalArgumentException>(throwableAction)
            }
        }

        `when`("자동 발급한 경우") {
            val randomNumber = listOf(6, 5, 4, 3, 2, 1)
            val result = Lottery.createRandomNumbers(StubNumberGenerator(randomNumber))
            then("숫자 생성기에 의해 생성된 숫자를 가진 로또를 발행한다.") {
                result.match(Lottery(randomNumber)) shouldBe LottoScore.ONE_PLACE
            }
        }
    }
})

class StubNumberGenerator(private val actualNumbers: List<Int>) : NumberGenerator<List<Int>> {

    override fun generate(): List<Int> = actualNumbers
}
