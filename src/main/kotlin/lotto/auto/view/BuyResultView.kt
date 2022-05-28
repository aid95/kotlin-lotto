package lotto.auto.view

import lotto.auto.domain.Lotto
import lotto.auto.infra.port.OutputSystem

class BuyResultView(private val outputSystem: OutputSystem, private val lottos: List<Lotto>) {

    fun printLottos() {
        printLottoCount()
        printAllLotto()
    }

    private fun printLottoCount() {
        outputSystem.write("${lottos.size}개를 구매했습니다.\n")
    }

    private fun printAllLotto() {
        lottos.forEach(::printLotto)
    }

    private fun printLotto(lotto: Lotto) = outputSystem.write("${lotto}\n")
}
