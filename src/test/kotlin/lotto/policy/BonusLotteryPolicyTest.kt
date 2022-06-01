package lotto.policy

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lotto.domain.Lottery
import lotto.domain.toLotteryNumberSet
import lotto.vo.LotteryNumber
import lotto.vo.LotteryNumberSet
import lotto.vo.LotteryRank

internal class BonusLotteryPolicyTest : BehaviorSpec({

    given("모든 번호가 같은 경우") {
        val lotteryNumberSet = LotteryNumberSet(listOf(1, 2, 3, 4, 5, 6).map(LotteryNumber::of))
        val lottery = Lottery(lotteryNumberSet)
        val bonusLotteryPolicy = BonusLotteryPolicy(lotteryNumberSet, LotteryNumber.of(10))

        `when`("비교하면") {
            val result = bonusLotteryPolicy.match(lottery)

            then("1등이 반환된다.") {
                result shouldBe LotteryRank.ONE_PLACE
            }
        }
    }

    given("모든 번호 중 두 개 다르고 보너스 번호가 같은 경우") {
        val lotteryNumberSet = listOf(1, 2, 3, 4, 11, 10).toLotteryNumberSet()
        val lottery = Lottery(lotteryNumberSet)

        val winningLotteryNumbers = listOf(1, 2, 3, 4, 10, 11).toLotteryNumberSet()
        val bonusLotteryPolicy = BonusLotteryPolicy(winningLotteryNumbers, LotteryNumber.of(10))

        `when`("비교하면") {
            val result = bonusLotteryPolicy.match(lottery)

            then("보너스 당첨이 반환된다.") {
                result shouldBe LotteryRank.BONUS_TWO_PLACE
            }
        }
    }

    given("모든 번호가 한개 다른 경우") {
        val lotteryNumberSet = listOf(1, 2, 3, 4, 11, 10).toLotteryNumberSet()
        val lottery = Lottery(lotteryNumberSet)

        val winningLotteryNumbers = listOf(1, 2, 3, 4, 11, 45).toLotteryNumberSet()
        val bonusLotteryPolicy = BonusLotteryPolicy(winningLotteryNumbers, LotteryNumber.of(44))

        `when`("비교하면") {
            val result = bonusLotteryPolicy.match(lottery)

            then("2등이 반환된다.") {
                result shouldBe LotteryRank.TWO_PLACE
            }
        }
    }
})
