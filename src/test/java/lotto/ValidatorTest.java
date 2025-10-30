package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @DisplayName("구입금액이 1000으로 나누어 떨어지지 않으면 예외가 발생한다.")
    @Test
    void 구입금액이_1000으로_나누어_떨어지지_않으면_예외가_발생한다() {
        String input = "1234";
        assertNull(Validator.getMoneyAndcheckMoneyFormat(input));
    }

    @DisplayName("구입금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void 구입금액이_숫자가_아니면_예외가_발생한다() {
        String input = "abcd";
        assertNull(Validator.getMoneyAndcheckMoneyFormat(input));
    }

    @DisplayName("로또 번호 입력이 쉼표로 구분되지 않으면 예외가 발생한다.")
    @Test
    void 로또_번호_입력이_쉼표로_구분되지_않으면_예외가_발생한다() {
        String input = "1 2 3 4 5 6";
        assertFalse(Validator.checkLottoInputFormat(input));
    }

    @DisplayName("로또 번호 입력에 숫자가 아닌 문자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호_입력에_숫자가_아닌_문자가_있으면_예외가_발생한다() {
        String input = "1,2,3,a,5,6";
        List<Integer> winningNumbers = new ArrayList<>();
        assertFalse(Validator.insertAndCheckWinningNumbers(input, winningNumbers));
    }

    @DisplayName("로또 번호가 6개가 아니면 예외가 발생한다.")
    @Test
    void 로또_번호가_6개가_아니면_예외가_발생한다() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5);
        assertFalse(Validator.checkWinningNumbersIsSix(winningNumbers));
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 5);
        assertFalse(Validator.checkWinningNumbersIsUnique(winningNumbers));
    }

    @DisplayName("로또 번호가 1-45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 로또_번호가_1_45_범위를_벗어나면_예외가_발생한다() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 46);
        assertFalse(Validator.checkWinningNumbersInRange(winningNumbers));
    }

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void 보너스_번호가_숫자가_아니면_예외가_발생한다() {
        String input = "a";
        assertNull(Validator.insertAndCheckBonusNumber(input));
    }

    @DisplayName("정상적인 구입금액 입력")
    @Test
    void 정상적인_구입금액_입력() {
        String input = "3000";
        assertThat(Validator.getMoneyAndcheckMoneyFormat(input)).isEqualTo(3);
    }

    @DisplayName("정상적인 로또 번호 입력")
    @Test
    void 정상적인_로또_번호_입력() {
        String input = "1,2,3,4,5,6";
        assertTrue(Validator.checkLottoInputFormat(input));
        List<Integer> winningNumbers = new ArrayList<>();
        assertTrue(Validator.insertAndCheckWinningNumbers(input, winningNumbers));
        assertTrue(Validator.checkWinningNumbersIsSix(winningNumbers));
        assertTrue(Validator.checkWinningNumbersIsUnique(winningNumbers));
        assertTrue(Validator.checkWinningNumbersInRange(winningNumbers));
    }

    @DisplayName("정상적인 보너스 번호 입력")
    @Test
    void 정상적인_보너스_번호_입력() {
        String input = "7";
        assertThat(Validator.insertAndCheckBonusNumber(input)).isEqualTo(7);
    }
}
