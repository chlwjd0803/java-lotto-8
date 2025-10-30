package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        this.numbers = sortedNumbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        for (Integer number : numbers) {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
        }
        HashSet<Integer> numberSet = new HashSet<>(numbers);
        if(numberSet.size() != 6){
            throw new IllegalArgumentException("[ERROR] 중복된 번호를 입력할 수 없습니다.");
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public static Lotto makeLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        return new Lotto(numbers);
    }

    // 로또번호 랜덤생성 전체
    public static List<Lotto> makeLottos(Integer numOfLotto) {
        // 로또들을 담는 자료형
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < numOfLotto; i++) {
            lottos.add(makeLotto());
        }
        return lottos;
    }

    // 만든 로또들 출력
    public static void printLottos(Integer numOfLotto, List<Lotto> lottos) {
        System.out.println("\n" + numOfLotto + "개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers().toString());
        }
    }
}