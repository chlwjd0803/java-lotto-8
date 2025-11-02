package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.*;

public class Application {

    // 로또 구입금액 입력
    private Integer retNumOfLotto() {
        while (true) {
            System.out.println("구입금액을 입력해 주세요.");
            Integer money = Validator.getMoneyAndcheckMoneyFormat(Console.readLine());
            if (money == null) continue;
            return money;
        }
    }

    // 당첨 번호 입력
    private List<Integer> retWinningLotto(){
        while(true){
            System.out.println("\n당첨 번호를 입력해 주세요.");
            String input = Console.readLine();
            List<Integer> winningNumbers = new ArrayList<>();
            if(!Validator.checkLottoInputFormat(input)) continue; // 정규식을 따르는지 검사
            if(!Validator.insertAndCheckWinningNumbers(input, winningNumbers)) continue; // List에 원소를 집어넣으며 정수형태 검증
            if(!Validator.checkWinningNumbersIsSix(winningNumbers)) continue; // 여섯개의 번호만 입력했는지 검사
            if(!Validator.checkWinningNumbersInRange(winningNumbers)) continue; // 1~45 범위의 번호들만 입력했는지 검사
            if(!Validator.checkWinningNumbersIsUnique(winningNumbers)) continue; // 번호들이 중복됨이 있는지 검사

            return winningNumbers;
        }
    }

    // 보너스 번호 입력
    private Integer retBonusNumber(){
        while(true){
            System.out.println("\n보너스 번호를 입력해 주세요.");
            Integer bonusNumber = Validator.insertAndCheckBonusNumber(Console.readLine());
            if(bonusNumber == null) continue;
            return bonusNumber;
        }
    }

    // 총합 구하기
    private Integer totalRewardMoney(HashMap<Reward, Integer> rewardMap){
        Integer total = 0;
        for(Reward reward : Reward.values()){
            // 총합에 보상금 * 당첨횟수를 더함
            total += reward.getRewardMoney() * rewardMap.get(reward);
        }
        return total;
    }

    // 실행
    public void run(){
        Integer numOfLotto = retNumOfLotto(); // 구입한 로또의 개수
        List<Lotto> lottos = Lotto.makeLottos(numOfLotto); // 로또 생성
        Lotto.printLottos(numOfLotto, lottos); // 로또들 출력
        List<Integer> winningLotto = retWinningLotto(); // 당첨번호 입력
        Integer bonus = retBonusNumber(); // 보너스 번호 입력
        HashMap<Reward, Integer> rewardMap = Reward.status(lottos, new TreeSet<>(winningLotto), bonus); // 각 당첨 통계 맵
        Reward.printStatus(rewardMap);
        Integer total = totalRewardMoney(rewardMap);// 상금 총합
        double profitRate = (double) total / (numOfLotto * 1000) * 100;
        System.out.println("총 수익률은 " + String.format("%.1f", profitRate) + "%입니다.");
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
