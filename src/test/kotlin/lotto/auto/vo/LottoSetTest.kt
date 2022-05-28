package lotto.auto.vo

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lotto.auto.domain.Lotto

internal class LottoSetTest : BehaviorSpec({

    given("로또 번호들 중") {
        val lottoSet = LottoSet(
            listOf(
                Lotto(listOf(1, 2, 3, 4, 5, 6)),

                Lotto(listOf(1, 2, 3, 4, 5, 7)),
                Lotto(listOf(1, 2, 3, 4, 5, 7)),

                Lotto(listOf(1, 2, 3, 4, 9, 8)),
                Lotto(listOf(1, 2, 3, 4, 9, 8)),
                Lotto(listOf(1, 2, 3, 4, 9, 8)),

                Lotto(listOf(1, 2, 3, 12, 11, 10)),
                Lotto(listOf(1, 2, 3, 12, 11, 10)),
                Lotto(listOf(1, 2, 3, 12, 11, 10)),
                Lotto(listOf(1, 2, 3, 12, 11, 10)),

                Lotto(listOf(1, 2, 16, 15, 14, 13)),
                Lotto(listOf(1, 2, 16, 15, 14, 13)),
                Lotto(listOf(1, 2, 16, 15, 14, 13)),
                Lotto(listOf(1, 2, 16, 15, 14, 13)),
                Lotto(listOf(1, 2, 16, 15, 14, 13)),
            )
        )
        val lastWeekLotto = Lotto(listOf(1, 2, 3, 4, 5, 6))

        `when`("1등 당첨자 조회시") {
            val result = lottoSet.countPlace(lastWeekLotto, LottoScore.ONE_PLACE)

            then("1등 당첨자 수를 반환한다.") {
                result shouldBe 1
            }
        }

        `when`("2등 당첨자 조회시") {
            val result = lottoSet.countPlace(lastWeekLotto, LottoScore.TWO_PLACE)

            then("2등 당첨자 수를 반환한다.") {
                result shouldBe 2
            }
        }

        `when`("3등 당첨자 조회시") {
            val result = lottoSet.countPlace(lastWeekLotto, LottoScore.THIRD_PLACE)

            then("3등 당첨자 수를 반환한다.") {
                result shouldBe 3
            }
        }

        `when`("4등 당첨자 조회시") {
            val result = lottoSet.countPlace(lastWeekLotto, LottoScore.FOUR_PLACE)

            then("4등 당첨자 수를 반환한다.") {
                result shouldBe 4
            }
        }

        `when`("꽝 조회시") {
            val result = lottoSet.countPlace(lastWeekLotto, LottoScore.BANG)

            then("꽝 수를 반환한다.") {
                result shouldBe 5
            }
        }
    }
})
