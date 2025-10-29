package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Application {

    // 로또 구입금액 입력
    private Integer retNumOfLotto(){
        while(true){
            System.out.println("구입금액을 입력해 주세요.");
            try{
                Integer money = Integer.parseInt(Console.readLine());
                return money / 1000;
            } catch (NumberFormatException e){
                System.out.println("[ERROR] 구입금액이 올바른 정수형태가 아닙니다.");
            }
        }
    }

    // 당첨 번호 입력
    private List<Integer> retWinningLotto(){
        System.out.println("당첨 번호를 입력해 주세요.");
        String input = Console.readLine();

        String[] inputSplit = input.split(",");
        List<Integer> winningNumbers = new ArrayList<>();

        for(String number : inputSplit){
            winningNumbers.add(Integer.parseInt(number));
        }

        return winningNumbers;
    }

    // 보너스 번호 입력
    private Integer retBonusNumber(){
        System.out.println("보너스 번호를 입력해 주세요.");
        return Integer.parseInt(Console.readLine());
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
        // 구입한 로또의 개수
        Integer numOfLotto = retNumOfLotto();
        // 로또 생성
        List<Lotto> lottos = Lotto.makeLottos(numOfLotto);
        // 로또들 출력
        Lotto.printLottos(numOfLotto, lottos);
        // 당첨번호 입력
        List<Integer> winningLotto = retWinningLotto();
        // 보너스 번호 입력
        Integer bonus = retBonusNumber();
        // 각 당첨 통계 맵
        HashMap<Reward, Integer> rewardMap = Reward.status(lottos, new TreeSet<>(winningLotto), bonus);
        Reward.printStatus(rewardMap);
        // 상금 총합
        Integer total = totalRewardMoney(rewardMap);
        System.out.println("총 수익률은 " + (total / (numOfLotto * 1000) * 100) + "%입니다.");
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
